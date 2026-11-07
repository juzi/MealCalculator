package net.zimmercarral.mealcalculator.meal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import net.zimmercarral.mealcalculator.R
import net.zimmercarral.mealcalculator.data.db.model.FoodItem

@Composable
fun MealList(
    foodItems: List<FoodItem>,
    onEditItem: (Int) -> Unit,
    onAddItem: (FoodItem) -> Unit,
    onDeleteItem: (FoodItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (foodItems.isEmpty()) {
        // Same height as the list, so the rest of the screen keeps its layout.
        Box(
            modifier = modifier
                .fillMaxHeight(0.7f)
                .fillMaxWidth()
                .padding(horizontal = 40.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = stringResource(R.string.empty_meal_hint),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
            )
        }
        return
    }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
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



