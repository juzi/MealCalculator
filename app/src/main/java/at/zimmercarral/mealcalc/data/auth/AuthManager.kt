package at.zimmercarral.mealcalc.data.auth

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
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
        val firebaseCred = GoogleAuthProvider.getCredential(googleCred.idToken, null)
        return auth.signInWithCredential(firebaseCred).await().user
            ?: error("Firebase signIn returned null user")
    }

    fun signOut() = auth.signOut()
}
