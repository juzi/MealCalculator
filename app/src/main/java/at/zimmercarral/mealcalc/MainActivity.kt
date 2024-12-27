package at.zimmercarral.mealcalc

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
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
import at.zimmercarral.mealcalc.item.EditItemDialogViewModel
import at.zimmercarral.mealcalc.meal.MealViewModel
import at.zimmercarral.mealcalc.search.SearchViewModel
import at.zimmercarral.mealcalc.ui.theme.AppTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val networkManager = NetworkManager(this)
    private val synchronizationTask = SynchronizationTask()
    
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

        val editItemDialogViewModel = EditItemDialogViewModel(db.dao())
        val mealViewModel = MealViewModel(listOf())
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
                            duration = SnackbarDuration.Long
                        )
                        if (result == SnackbarResult.ActionPerformed) {
                            event.action?.action?.invoke()
                        }
                    }
                }

                Scaffold(modifier = Modifier.fillMaxSize(),
                    snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
                ) { innerPadding ->
                    Column(modifier = Modifier
                        .padding(innerPadding)
                    ) {
                        MainLayout(editItemDialogViewModel, mealViewModel, searchViewModel)
                    }
                }
            }
        }
    }
}


