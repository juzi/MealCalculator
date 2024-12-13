package at.zimmercarral.mealcalc

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import at.zimmercarral.mealcalc.item.CurrentItemView
import at.zimmercarral.mealcalc.item.CurrentItemViewModel
import at.zimmercarral.mealcalc.meal.MealInfo
import at.zimmercarral.mealcalc.meal.MealViewModel

@Composable
fun MainLayout(currentItemViewModel: CurrentItemViewModel, mealViewModel: MealViewModel) {
    Column(verticalArrangement = Arrangement.Top,
        modifier = Modifier.fillMaxSize()) {
        TopActionIcons(currentItemViewModel, mealViewModel)
        CurrentItemView(currentItemViewModel, mealViewModel)
        MealInfo(currentItemViewModel, mealViewModel)
    }
}
