package at.zimmercarral.mealcalc.meal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import at.zimmercarral.mealcalc.data.db.model.FoodItem

@Composable
fun MealList(
    foodItems: List<FoodItem>,
    onEditItem: (Int) -> Unit,
    onAddItem: (FoodItem) -> Unit,
    onDeleteItem: (FoodItem) -> Unit
) {

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxHeight(0.7f)
            .padding(start = 15.dp)
    ) {
        itemsIndexed(items = foodItems) { index, foodItem ->
            MealItem(
                index = index,
                item = foodItem,
                onEditItem = onEditItem,
                onAddItem = onAddItem,
                onDeleteItem = onDeleteItem
            )
        }
    }
}



