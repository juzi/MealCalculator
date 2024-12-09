package at.zimmercarral.mealcalc

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import at.zimmercarral.mealcalc.data.db.model.FoodItem
import at.zimmercarral.mealcalc.domain.FoodItemViewModel
import at.zimmercarral.mealcalc.meal.MealViewModel
import com.example.compose.AppTheme
import java.util.UUID

class MainActivity : ComponentActivity() {
    private val foodItemViewModel: FoodItemViewModel by viewModels()
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        for (foodItem in availableItems) {
            foodItemViewModel.insertItem(foodItem)
        }
        enableEdgeToEdge()
        setContent {
            AppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column() {
                        foodItemViewModel.getAllItems().map {
                            Text(text = it.name)
                        }
                    }
                    Column(modifier = Modifier
                        .padding(innerPadding)
                        .background(color = Color.White)
                    ) {
                        MainLayout(currentItemViewModel, mealViewModel)
                    }
                }
            }
        }
    }
}

