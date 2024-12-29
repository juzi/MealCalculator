package at.zimmercarral.mealcalc

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.room.Room
import at.zimmercarral.mealcalc.data.db.FoodItemDatabase
import at.zimmercarral.mealcalc.data.sync.NetworkManager
import at.zimmercarral.mealcalc.data.sync.SynchronizationTask
import at.zimmercarral.mealcalc.edit.EditItemDialogViewModel
import at.zimmercarral.mealcalc.meal.MealViewModel
import at.zimmercarral.mealcalc.search.SearchViewModel
import at.zimmercarral.mealcalc.ui.theme.AppTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val prefName = "prefs"
    private val stateKeyMealItems = "key_meal_items";
    private val networkManager = NetworkManager(this)
    private val synchronizationTask = SynchronizationTask()
    private var mealViewModel = MealViewModel(listOf())

    @SuppressLint("StateFlowValueCalledInComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db by lazy {
            Room.databaseBuilder(
                this.applicationContext,
                FoodItemDatabase::class.java,
                "food-items.db"
            ).allowMainThreadQueries().build()
        }

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

        val editItemDialogViewModel = EditItemDialogViewModel(db.dao())
        val searchViewModel = SearchViewModel(db.dao().getAllItems())

        if (networkManager.isInHomeNetwork()) {
            synchronizationTask.syncDatabases(db.dao(),
                onSyncCompleted = { searchViewModel.setItems(db.dao().getAllItems()) })
        }

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

                Scaffold(modifier = Modifier.fillMaxSize(),
                    snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
                ) { innerPadding ->
                    MainLayout(innerPadding,
                        editItemDialogViewModel,
                        mealViewModel,
                        searchViewModel)
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
        super.onPause();

        val sharedPrefs = getSharedPreferences(prefName, Context.MODE_PRIVATE)
        val editor = sharedPrefs?.edit()
        val json = mealViewModel.getItemsAsJson()
        editor?.putString(stateKeyMealItems, json)
        editor?.apply()
    }
}


