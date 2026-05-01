package at.zimmercarral.mealcalc

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
import androidx.core.content.ContextCompat
import at.zimmercarral.mealcalc.meal.InsulinReminderManager
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
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import at.zimmercarral.mealcalc.data.auth.AuthManager
import at.zimmercarral.mealcalc.data.cloud.FirestoreFoodItemRepository
import at.zimmercarral.mealcalc.data.cloud.FirestoreSyncService
import at.zimmercarral.mealcalc.data.db.FoodItemDatabase
import at.zimmercarral.mealcalc.data.db.dao.FoodItemDao
import at.zimmercarral.mealcalc.data.repo.FoodItemRepository
import at.zimmercarral.mealcalc.edit.EditItemDialogViewModel
import at.zimmercarral.mealcalc.meal.MealViewModel
import at.zimmercarral.mealcalc.search.SearchViewModel
import at.zimmercarral.mealcalc.ui.theme.AppTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class MainActivity : ComponentActivity() {
    private val prefName = "prefs"
    private val stateKeyMealItems = "key_meal_items"
    private var mealViewModel = MealViewModel(listOf())

    @SuppressLint("StateFlowValueCalledInComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = Room.databaseBuilder(
            this.applicationContext,
            FoodItemDatabase::class.java,
            "food-items.db"
        ).build()

        val webClientId = getString(R.string.default_web_client_id)
        val authManager = AuthManager(FirebaseAuth.getInstance(), webClientId)

        val insulinReminderManager = InsulinReminderManager(this.applicationContext)
        mealViewModel.setInsulinReminderManager(insulinReminderManager)

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

        val dao = db.dao()
        val repository = FoodItemRepository(dao)
        val searchViewModel = SearchViewModel(repository)
        val editItemDialogViewModel = EditItemDialogViewModel(repository)

        lifecycleScope.launch { backfillMissingTimestamps(dao) }

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

                var user by remember { mutableStateOf<FirebaseUser?>(authManager.currentUser) }
                DisposableEffect(authManager) {
                    val listener = authManager.addAuthStateListener { user = it }
                    onDispose { authManager.removeAuthStateListener(listener) }
                }

                val cloud = remember(user?.uid) {
                    user?.uid?.let {
                        FirestoreFoodItemRepository(FirebaseFirestore.getInstance(), it)
                    }
                }
                LaunchedEffect(cloud) { repository.setCloud(cloud) }

                DisposableEffect(cloud) {
                    val current = cloud
                    if (current == null) {
                        onDispose { }
                    } else {
                        val service = FirestoreSyncService(dao, current)
                        service.start()
                        onDispose { service.stop() }
                    }
                }

                val onSignIn: () -> Unit = {
                    scope.launch {
                        try {
                            authManager.signInWithGoogle(this@MainActivity)
                        } catch (e: Exception) {
                            val msg = e.localizedMessage ?: e.javaClass.simpleName
                            snackbarHostState.showSnackbar(
                                getString(R.string.sign_in_failed, msg)
                            )
                        }
                    }
                }
                val onSignOut: () -> Unit = { authManager.signOut() }

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

    private suspend fun backfillMissingTimestamps(dao: FoodItemDao) {
        val now = LocalDateTime.now()
        dao.getAllItems()
            .filter { it.lastModifiedAt == null }
            .forEach { dao.updateOrInsert(it.copy(lastModifiedAt = now)) }
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
