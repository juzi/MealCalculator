package at.zimmercarral.mealcalc

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import at.zimmercarral.mealcalc.data.db.FoodItemDatabase
import at.zimmercarral.mealcalc.data.db.FoodItemEvent
import at.zimmercarral.mealcalc.data.db.model.FoodItem
import at.zimmercarral.mealcalc.domain.FoodItemViewModel
import at.zimmercarral.mealcalc.meal.MealViewModel
import com.example.compose.AppTheme
import kotlinx.coroutines.launch
import java.util.UUID

class MainActivity : ComponentActivity() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            FoodItemDatabase::class.java,
            "food-items.db"
        ).allowMainThreadQueries().build()
    }
    @Suppress("UNCHECKED_CAST")
    private val foodItemViewModel by viewModels<FoodItemViewModel>(

        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return FoodItemViewModel(db.dao()) as T
                }
            }
        }
    )

    val availableItems: List<FoodItem> = listOf(
        FoodItem(
            UUID.randomUUID().toString(),
            "Pizza",
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
    private val currentItemViewModel = CurrentItemViewModel()

    @SuppressLint("StateFlowValueCalledInComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            for (foodItem in availableItems) {
                db.dao().insert(foodItem)
            }
        }

        enableEdgeToEdge()
        setContent {
            AppTheme {
                val state by foodItemViewModel.state.collectAsState()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier
                        .padding(innerPadding)
                        .background(color = Color.White)
                    ) {
                        state.items.forEach {
                            Text(text = it.name)
                        }
                    }
//                    Column(modifier = Modifier
//                        .padding(innerPadding)
//                        .background(color = Color.White)
//                    ) {
//                        MainLayout(currentItemViewModel, mealViewModel)
//                    }
                }
            }
        }
    }
}

