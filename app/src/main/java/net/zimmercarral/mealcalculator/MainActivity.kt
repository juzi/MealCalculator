package net.zimmercarral.mealcalculator

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import net.zimmercarral.mealcalculator.data.cloud.FirestoreFoodItemRepository
import net.zimmercarral.mealcalculator.data.cloud.FirestoreSyncService
import net.zimmercarral.mealcalculator.edit.EditItemDialogViewModel
import net.zimmercarral.mealcalculator.meal.MealViewModel
import net.zimmercarral.mealcalculator.search.SearchViewModel
import net.zimmercarral.mealcalculator.ui.theme.AppTheme
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val prefName = "prefs"
    private val stateKeyMealItems = "key_meal_items"

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

                val notificationPermissionLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission(),
                ) { /* reminder silently no-ops if denied */ }
                LaunchedEffect(Unit) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                        ContextCompat.checkSelfPermission(
                            this@MainActivity,
                            Manifest.permission.POST_NOTIFICATIONS,
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    }
                }
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

                DisposableEffect(cloud) {
                    val current = cloud
                    if (current == null) {
                        onDispose { }
                    } else {
                        val service = FirestoreSyncService(app.foodItemDao, current)
                        service.start()
                        onDispose { service.stop() }
                    }
                }

                val onSignIn: () -> Unit = {
                    scope.launch {
                        try {
                            app.authManager.signInWithGoogle(this@MainActivity)
                        } catch (e: Exception) {
                            val msg = e.localizedMessage ?: e.javaClass.simpleName
                            snackbarHostState.showSnackbar(
                                getString(R.string.sign_in_failed, msg)
                            )
                        }
                    }
                }
                val onSignOut: () -> Unit = { app.authManager.signOut() }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
                ) { innerPadding ->
                    MainLayout(
                        innerPadding = innerPadding,
                        editItemDialogViewModel = editItemDialogViewModel,
                        mealViewModel = mealViewModel,
                        searchViewModel = searchViewModel,
                        isSignedIn = user != null,
                        onSignIn = onSignIn,
                        onSignOut = onSignOut,
                    )
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

        val sharedPrefs = getSharedPreferences(prefName, Context.MODE_PRIVATE)
        val editor = sharedPrefs?.edit()
        val json = mealViewModel.getItemsAsJson()
        editor?.putString(stateKeyMealItems, json)
        editor?.apply()
    }
}
