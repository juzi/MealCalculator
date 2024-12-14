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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import at.zimmercarral.mealcalc.data.db.model.FoodItem
import at.zimmercarral.mealcalc.item.EditItemDialogViewModel
import at.zimmercarral.mealcalc.meal.MealViewModel
import at.zimmercarral.mealcalc.ui.theme.AppTheme
import java.util.UUID

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

    val availableItems: List<FoodItem> = listOf(
        FoodItem(
            UUID.randomUUID().toString(),
            "Apfelpfannkuchen mit Schlagobers",
            10f,
            10f,
            10f,
            10,
            java.util.Date(),
            false
        ),
        FoodItem(
            UUID.randomUUID().toString(),
            "Pasta",
            10f,
            10f,
            10f,
            10,
            java.util.Date(),
            false
        ),
        FoodItem(
            UUID.randomUUID().toString(),
            "Apfelpfannkuchen mit Schlagobers",
            10f,
            10f,
            10f,
            10,
            java.util.Date(),
            false
        ),
        FoodItem(
            UUID.randomUUID().toString(),
            "Pasta",
            10f,
            10f,
            10f,
            10,
            java.util.Date(),
            false
        ),
        FoodItem(
            UUID.randomUUID().toString(),
            "Apfelpfannkuchen mit Schlagobers",
            10f,
            10f,
            10f,
            10,
            java.util.Date(),
            false
        ),
        FoodItem(
            UUID.randomUUID().toString(),
            "Pasta",
            10f,
            10f,
            10f,
            10,
            java.util.Date(),
            false
        ))


    private val mealViewModel = MealViewModel(availableItems)
    private val editItemDialogViewModel = EditItemDialogViewModel()

    @SuppressLint("StateFlowValueCalledInComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        lifecycleScope.launch {
//            for (foodItem in availableItems) {
//                db.dao().insert(foodItem)
//            }
//        }

        enableEdgeToEdge()
        setContent {
            AppTheme {
//                val state by foodItemViewModel.state.collectAsState()
                val hostState = remember { SnackbarHostState() }

                Scaffold(modifier = Modifier.fillMaxSize(),
                    snackbarHost = { SnackbarHost(hostState) }
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

