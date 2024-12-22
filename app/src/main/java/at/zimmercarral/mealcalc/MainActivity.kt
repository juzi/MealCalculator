package at.zimmercarral.mealcalc

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.room.Room
import at.zimmercarral.mealcalc.data.db.FoodItemDatabase
import at.zimmercarral.mealcalc.data.sync.NetworkManager
import at.zimmercarral.mealcalc.data.sync.SynchronizationTask
import at.zimmercarral.mealcalc.item.EditItemDialogViewModel
import at.zimmercarral.mealcalc.item.Focus
import at.zimmercarral.mealcalc.meal.MealViewModel
import at.zimmercarral.mealcalc.search.SearchViewModel
import at.zimmercarral.mealcalc.search.SearchableItemList
import at.zimmercarral.mealcalc.ui.theme.AppTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val networkManager = NetworkManager(this)
    private val synchronizationTask = SynchronizationTask()

    //    private val requestQueue by lazy { Volley.newRequestQueue(this) }

//    @Suppress("UNCHECKED_CAST")
//    private val editItemDialogViewModel by viewModels<FoodItemViewModel>(
//        factoryProducer = {
//            object : ViewModelProvider.Factory {
//                override fun <T : ViewModel> create(modelClass: Class<T>): T {
//                    return FoodItemViewModel(db.dao()) as T
//                }
//            }
//        }
//    )


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

        if (networkManager.isInHomeNetwork()) {
            synchronizationTask.syncDatabases(mealViewModel)
        }
        val availableItems = db.dao().getAllItems()
        val searchViewModel = SearchViewModel(db.dao().getAllItems())
        // Use the items here
//            for (foodItem in availableItems) {
//                db.dao().insert(foodItem)
//            }
//        }

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
//                    Column(modifier = Modifier
//                        .padding(innerPadding)
//                        .background(color = Color.White)
//                    ) {
//                        state.items.forEach {
//                            Text(text = it.name)
//                        }
//                    }
                    Column(modifier = Modifier
                        .padding(innerPadding)
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            MainLayout(editItemDialogViewModel, mealViewModel)
                            SearchableItemList(searchViewModel,
                                onItemSelected = {
                                    editItemDialogViewModel.setItem(it)
                                    editItemDialogViewModel.setInUpdateMode(false)
                                    editItemDialogViewModel.setInitialFocus(Focus.AMOUNT)
                                    editItemDialogViewModel.setVisible(true)
                                })
                            // Floatinsg search button
                            FloatingActionButton(
                                onClick = {
                                    searchViewModel.setVisibility(true)
                                },
                                modifier = Modifier
                                    .padding(end = 25.dp, top = 15.dp)
                                    .align(Alignment.TopEnd),
                                containerColor = colorScheme.primary,
                                contentColor = colorScheme.onPrimary,
                            ) {
                                Icon(modifier = Modifier.size(30.dp),
                                    imageVector = Icons.Rounded.Search,
                                    contentDescription = "Search item"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

