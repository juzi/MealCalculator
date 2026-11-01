package net.zimmercarral.mealcalculator

import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory

/**
 * Debug builds use the App Check debug provider. On first launch it logs a
 * debug token (logcat tag "DebugAppCheckProvider"); register it in the Firebase
 * console under App Check -> Apps -> Manage debug tokens, otherwise debug
 * builds are rejected once App Check is enforced for Firestore.
 */
internal fun installAppCheck() {
    FirebaseAppCheck.getInstance()
        .installAppCheckProviderFactory(DebugAppCheckProviderFactory.getInstance())
}
