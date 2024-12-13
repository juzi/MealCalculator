package at.zimmercarral.mealcalc.item

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircleOutline
import androidx.compose.material.icons.rounded.ArrowCircleDown
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import at.zimmercarral.mealcalc.R
import at.zimmercarral.mealcalc.common.ActionButton
import at.zimmercarral.mealcalc.meal.MealViewModel

@Composable
fun CurrentItemView(currentItemViewModel: CurrentItemViewModel, mealViewModel: MealViewModel) {
    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier
            .background(color = colorScheme.surface)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            },
        verticalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        ItemName(currentItemViewModel.name.value, currentItemViewModel::setName)
        NutrionalValuesDialog(currentItemViewModel, mealViewModel)
        CalculatedValues(currentItemViewModel)
    }
}


@Composable
private fun NutrionalValuesDialog(
    currentItemViewModel: CurrentItemViewModel,
    mealViewModel: MealViewModel
) {
    Row() {
        NutritionalValues(currentItemViewModel)
        Column(modifier = Modifier
            .padding(top = 80.dp, start = 55.dp),
            verticalArrangement = Arrangement.Center
        ) {
            var buttonImage = Icons.Rounded.AddCircleOutline
            if (currentItemViewModel.inEditMode.value) {
                buttonImage = Icons.Rounded.ArrowCircleDown
            }
            ActionButton(stringResource(id = R.string.add_item_to_meal),
                buttonImage,
                color = Color(80, 127, 80, 255),
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 5.dp)
                    .size(60.dp),
                onClick = {
                    mealViewModel.addOrUpdateItem(currentItemViewModel.getFoodItem())
                })
        }
    }
}



