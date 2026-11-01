package net.zimmercarral.mealcalculator.data.auth

import android.content.Context
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await

class AuthManager(
    private val auth: FirebaseAuth,
    private val webClientId: String,
) {
    val currentUser: FirebaseUser?
        get() = auth.currentUser

    fun addAuthStateListener(listener: (FirebaseUser?) -> Unit): FirebaseAuth.AuthStateListener {
        val l = FirebaseAuth.AuthStateListener { listener(it.currentUser) }
        auth.addAuthStateListener(l)
        return l
    }

    fun removeAuthStateListener(listener: FirebaseAuth.AuthStateListener) {
        auth.removeAuthStateListener(listener)
    }

    suspend fun signInWithGoogle(activityContext: Context): FirebaseUser {
        val firebaseCred = requestGoogleCredential(activityContext)
        return auth.signInWithCredential(firebaseCred).await().user
            ?: error("Firebase signIn returned null user")
    }

    fun signOut() = auth.signOut()

    /**
     * Permanently deletes the signed-in user's account.
     *
     * The user re-authenticates with Google first, because Firebase only deletes
     * accounts with a recent login. Doing it up front also means the cloud data
     * is only removed once we know the account deletion itself can succeed.
     * [deleteUserData] removes everything stored for the user's uid and must
     * finish before the account is deleted, since afterwards the security rules
     * no longer allow access to that data.
     */
    suspend fun deleteAccount(
        activityContext: Context,
        deleteUserData: suspend (uid: String) -> Unit,
    ) {
        val user = auth.currentUser ?: error("not signed in")
        user.reauthenticate(requestGoogleCredential(activityContext)).await()
        deleteUserData(user.uid)
        user.delete().await()
        CredentialManager.create(activityContext)
            .clearCredentialState(ClearCredentialStateRequest())
    }

    private suspend fun requestGoogleCredential(activityContext: Context): AuthCredential {
        val credentialManager = CredentialManager.create(activityContext)
        val option = GetSignInWithGoogleOption.Builder(webClientId).build()
        val request = GetCredentialRequest.Builder().addCredentialOption(option).build()

        val response = credentialManager.getCredential(activityContext, request)
        val cred = response.credential
        check(
            cred is CustomCredential &&
                cred.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
        ) { "unexpected credential type: ${cred::class.java.simpleName}" }
        val googleCred = GoogleIdTokenCredential.createFrom(cred.data)
        return GoogleAuthProvider.getCredential(googleCred.idToken, null)
    }
}
