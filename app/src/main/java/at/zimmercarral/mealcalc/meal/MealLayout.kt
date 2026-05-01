package at.zimmercarral.mealcalc.meal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import at.zimmercarral.mealcalc.R
import at.zimmercarral.mealcalc.data.db.model.FoodItem
import at.zimmercarral.mealcalc.edit.CalculatedValues
import at.zimmercarral.mealcalc.edit.itemCalculator
import at.zimmercarral.mealcalc.ui.theme.AppTheme
import at.zimmercarral.mealcalc.ui.theme.robotoFamily

@Composable
fun MealLayout(
    mealViewModel: MealViewModel,
    onEditItem: (Int) -> Unit,
    modifier: Modifier = Modifier,
    onAddNewItem: () -> Unit = {},
) {
    val mealDeleted = stringResource(R.string.meal_deleted)
    val undo = stringResource(R.string.undo)
    val scope = rememberCoroutineScope()

    val calculatedValues = remember(mealViewModel.mealItems.toList()) {
        if (!mealViewModel.mealItems.isEmpty()) {
            mealViewModel.mealItems
                .map {
                    itemCalculator.calculateValues(it.fatPer100g, it.carbsPer100g,
                        it.proteinPer100g, it.amount)
                }
                .reduce { acc, calculatedValues ->
                    acc.add(calculatedValues)
                }
        } else {
            CalculatedValues(0f, 0f, 0f, 0f)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        MealTitle()
        MealList(foodItems = mealViewModel.mealItems,
            onEditItem = onEditItem,
            onAddItem = mealViewModel::addOrUpdateItem,
            mealViewModel::removeItem)
        MealValues(calculatedValues)
        MealActionButtons(mealViewModel, scope, mealDeleted, undo, onAddNewItem)
    }
}

@Composable
fun MealTitle() {
    Column() {
        Row(horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp, bottom = 10.dp)
        ) {
            Text(fontFamily = robotoFamily,
                fontSize = TextUnit(19f, TextUnitType.Sp),
                fontWeight = FontWeight.Normal,
                maxLines = 1,
                text = stringResource(R.string.meal_title))
        }
        Row(horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            HorizontalDivider(modifier = Modifier
                .fillMaxWidth(0.45f)
                .padding(bottom = 2.dp)
            )
        }
    }
}

@Preview
@PreviewLightDark
@Composable
private fun MealInfoPreview() {
    val item = FoodItem(
        uuid = "123",
        name = "Apfelpfannkuchen mit Schlagobers",
        fatPer100g = 0f,
        carbsPer100g = 12f,
        proteinPer100g = 0f,
        amount = 100,
        lastModifiedAt = null,
        isDeleted = false,
    )
    val mealViewModel = MealViewModel().apply { addItems(listOf(item)) }

    AppTheme() {
        MealLayout(
            mealViewModel,
            onEditItem = {},
        )
    }
}
