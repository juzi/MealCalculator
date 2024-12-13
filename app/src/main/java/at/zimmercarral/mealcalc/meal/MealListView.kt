package at.zimmercarral.mealcalc.meal

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import at.zimmercarral.mealcalc.item.CurrentItemViewModel
import at.zimmercarral.mealcalc.data.db.model.FoodItem

@Composable
fun MealListView(
    foods: SnapshotStateList<FoodItem>,
    currentMealViewModel: CurrentItemViewModel
) {

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .padding(start = 10.dp, end = 10.dp, top = 0.dp, bottom = 0.dp)
            .border(width = 1.dp, color = Color.LightGray,
                shape = RoundedCornerShape(10.dp))
    ) {
        items(foods, key = { it.uuid }) { foodItem ->
            MealItemView(item = foodItem, setCurrentItem = {
                currentMealViewModel.setItem(foodItem)
                currentMealViewModel.setInUpdateMode(true)
            })
        }
    }
}



