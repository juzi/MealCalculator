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
import at.zimmercarral.mealcalc.data.http.HttpFoodItemRepository
import at.zimmercarral.mealcalc.item.EditItemDialogViewModel
import at.zimmercarral.mealcalc.meal.MealViewModel
import at.zimmercarral.mealcalc.ui.theme.AppTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

//    private val db by lazy {
//        Room.databaseBuilder(
//            applicationContext,
//            FoodItemDatabase::class.java,
//            "food-items.db"
//        ).allowMainThreadQueries().build()
//    }
//    @Suppress("UNCHECKED_CAST")
//    private val foodItemViewModel by viewModels<FoodItemViewModel>(
//
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

        val httpFoodItemRepository = HttpFoodItemRepository()
        val mealViewModel = MealViewModel(listOf())

        httpFoodItemRepository.getEntries(
            onSuccess = { items ->
                items.forEach({ item ->
                    mealViewModel.addItem(item)

                })
            })
        // Use the items here
        val editItemDialogViewModel = EditItemDialogViewModel()

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
                        MainLayout(editItemDialogViewModel, mealViewModel)
                    }
                }
            }
        }
    }
}

