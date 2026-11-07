package net.zimmercarral.mealcalculator

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.core.content.edit
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.lifecycle.lifecycleScope
import net.zimmercarral.mealcalculator.data.auth.NoGoogleAccountException
import net.zimmercarral.mealcalculator.data.cloud.FirestoreFoodItemRepository
import net.zimmercarral.mealcalculator.data.cloud.FirestoreSyncService
import net.zimmercarral.mealcalculator.data.cloud.SyncState
import net.zimmercarral.mealcalculator.edit.EditItemDialogViewModel
import net.zimmercarral.mealcalculator.help.HelpScreen
import net.zimmercarral.mealcalculator.meal.MealViewModel
import net.zimmercarral.mealcalculator.search.SearchViewModel
import net.zimmercarral.mealcalculator.ui.theme.AppTheme
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val prefName = "prefs"
    private val stateKeyMealItems = "key_meal_items"
    private val prefKeyHelpSeen = "key_help_seen"

    private val app: MealCalcApplication
        get() = application as MealCalcApplication

    private val mealViewModel: MealViewModel by viewModels()

    private val searchViewModel: SearchViewModel by viewModels {
        SearchViewModel.factory(app.foodItemRepository)
    }

    private val editItemDialogViewModel: EditItemDialogViewModel by viewModels {
        EditItemDialogViewModel.factory(app.foodItemRepository)
    }

    @SuppressLint("StateFlowValueCalledInComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var savedMealItems = savedInstanceState?.getString(stateKeyMealItems)
        if (savedMealItems?.isNotBlank() == true) {
            mealViewModel.setItemsFromJson(savedMealItems)
        } else {
            val sharedPrefs = getSharedPreferences(prefName, Context.MODE_PRIVATE)
            savedMealItems = sharedPrefs.getString(stateKeyMealItems, null)
            if (savedMealItems?.isNotBlank() == true) {
                mealViewModel.setItemsFromJson(savedMealItems)
            }
        }

        lifecycleScope.launch { app.foodItemRepository.backfillMissingTimestamps() }

        enableEdgeToEdge()
        setContent {
            AppTheme {
                val snackbarHostState = remember { SnackbarHostState() }
                val scope = rememberCoroutineScope()

                ObserveAsEvents(flow = SnackbarController.events, snackbarHostState) { event ->
                    scope.launch {
                        snackbarHostState.currentSnackbarData?.dismiss()
                        val result = snackbarHostState.showSnackbar(
                            message = event.message,
                            actionLabel = event.action?.name,
                            duration = event.duration
                        )
                        if (result == SnackbarResult.ActionPerformed) {
                            event.action?.action?.invoke()
                        }
                    }
                }

                var user by remember { mutableStateOf<FirebaseUser?>(app.authManager.currentUser) }
                DisposableEffect(app.authManager) {
                    val listener = app.authManager.addAuthStateListener { user = it }
                    onDispose { app.authManager.removeAuthStateListener(listener) }
                }

                val cloud = remember(user?.uid) {
                    user?.uid?.let {
                        FirestoreFoodItemRepository(FirebaseFirestore.getInstance(), it)
                    }
                }
                LaunchedEffect(cloud) { app.foodItemRepository.setCloud(cloud) }

                val syncService = remember(cloud) {
                    cloud?.let { FirestoreSyncService(app.foodItemDao, it) }
                }
                DisposableEffect(syncService) {
                    syncService?.start()
                    onDispose { syncService?.stop() }
                }
                val syncState by remember(syncService) {
                    syncService?.state ?: MutableStateFlow(SyncState.SIGNED_OUT)
                }.collectAsState()

                val onSignIn: () -> Unit = {
                    scope.launch {
                        try {
                            app.authManager.signInWithGoogle(this@MainActivity)
                        } catch (_: GetCredentialCancellationException) {
                            // User closed the Google account picker.
                        } catch (_: NoGoogleAccountException) {
                            snackbarHostState.showSnackbar(getString(R.string.no_google_account))
                        } catch (e: Exception) {
                            val msg = e.localizedMessage ?: e.javaClass.simpleName
                            snackbarHostState.showSnackbar(
                                getString(R.string.sign_in_failed, msg)
                            )
                        }
                    }
                }
                // The walkthrough opens by itself until the user has closed it once.
                val prefs = remember { getSharedPreferences(prefName, Context.MODE_PRIVATE) }
                var showHelp by rememberSaveable {
                    mutableStateOf(!prefs.getBoolean(prefKeyHelpSeen, false))
                }

                val onSignOut: () -> Unit = { app.authManager.signOut() }
                val onDeleteAccount: () -> Unit = {
                    scope.launch {
                        try {
                            app.authManager.deleteAccount(this@MainActivity) { uid ->
                                FirestoreFoodItemRepository(FirebaseFirestore.getInstance(), uid)
                                    .deleteAll()
                            }
                            snackbarHostState.showSnackbar(getString(R.string.account_deleted))
                        } catch (_: GetCredentialCancellationException) {
                            // User closed the Google account picker: nothing was deleted.
                        } catch (_: NoGoogleAccountException) {
                            snackbarHostState.showSnackbar(getString(R.string.no_google_account))
                        } catch (e: Exception) {
                            val msg = e.localizedMessage ?: e.javaClass.simpleName
                            snackbarHostState.showSnackbar(
                                getString(R.string.delete_account_failed, msg)
                            )
                        }
                    }
                }

                Box(modifier = Modifier.fillMaxSize()) {
                    Scaffold(
                        // While the help covers the screen, hide the screen below from TalkBack.
                        modifier = Modifier
                            .fillMaxSize()
                            .then(if (showHelp) Modifier.clearAndSetSemantics { } else Modifier),
                        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
                    ) { innerPadding ->
                        MainLayout(
                            innerPadding = innerPadding,
                            editItemDialogViewModel = editItemDialogViewModel,
                            mealViewModel = mealViewModel,
                            searchViewModel = searchViewModel,
                            syncState = syncState,
                            onSignIn = onSignIn,
                            onSignOut = onSignOut,
                            onDeleteAccount = onDeleteAccount,
                            onShowHelp = { showHelp = true },
                        )
                    }
                    if (showHelp) {
                        HelpScreen(onDismiss = {
                            showHelp = false
                            prefs.edit { putBoolean(prefKeyHelpSeen, true) }
                        })
                    }
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        val json = mealViewModel.getItemsAsJson()
        outState.putString(stateKeyMealItems, json)
        super.onSaveInstanceState(outState)
    }

    override fun onPause() {
        super.onPause()

        getSharedPreferences(prefName, Context.MODE_PRIVATE).edit {
            putString(stateKeyMealItems, mealViewModel.getItemsAsJson())
        }
    }
}
