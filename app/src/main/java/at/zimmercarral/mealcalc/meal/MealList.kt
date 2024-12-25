package at.zimmercarral.mealcalc.meal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import at.zimmercarral.mealcalc.data.db.model.FoodItem

@Composable
fun MealList(
    foods: SnapshotStateList<FoodItem>,
    onEditItem: (FoodItem) -> Unit,
    onAddItem: (FoodItem) -> Unit,
    onDeleteItem: (FoodItem) -> Unit
) {

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
//        contentPadding = PaddingValues(start = 10.dp, end = 10.dp),
        modifier = Modifier
            .fillMaxHeight(0.6f)
            .padding(start = 15.dp)
    ) {
        items(foods, key = { it.uuid }) { foodItem ->
            MealItem(
                item = foodItem,
                onEditItem = onEditItem,
                onAddItem = onAddItem,
                onDeleteItem = onDeleteItem
            )
        }
    }
}



