package at.zimmercarral.mealcalc.meal

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import at.zimmercarral.mealcalc.R
import at.zimmercarral.mealcalc.SearchScreen
import at.zimmercarral.mealcalc.data.db.model.FoodItem
import at.zimmercarral.mealcalc.edit.CalculatedValues
import at.zimmercarral.mealcalc.edit.itemCalculator
import at.zimmercarral.mealcalc.ui.theme.AppTheme
import at.zimmercarral.mealcalc.ui.theme.robotoFamily

@Composable
fun MealLayout(
    navController: NavHostController,
    mealViewModel: MealViewModel,
    onEditItem: (Int) -> Unit,
    onAddNewItem: () -> Unit = {}
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
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        MealTitle(navController)
        MealList(foodItems = mealViewModel.mealItems,
            onEditItem = onEditItem,
            onAddItem = mealViewModel::addOrUpdateItem,
            mealViewModel::removeItem)
        MealActionButtons(mealViewModel, scope, mealDeleted, undo, onAddNewItem)
        MealValues(calculatedValues)
    }
}

@Composable
fun MealTitle(navController: NavHostController) {
    Column() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, top = 20.dp, bottom = 5.dp, end = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Spacer(Modifier
                .width(30.dp))
            Text(
                fontFamily = robotoFamily,
                fontSize = TextUnit(19f, TextUnitType.Sp),
                fontWeight = FontWeight.Normal,
                maxLines = 1,
                text = stringResource(R.string.meal_title))
            Icon(imageVector = Icons.Rounded.Search,
                modifier = Modifier
                    .size(30.dp)
                    .clickable { navController.navigate(SearchScreen) },
                contentDescription = "Search")
        }
        Row(horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        ) {
            HorizontalDivider(modifier = Modifier
                .fillMaxWidth(0.45f)
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
    val mealViewModel = MealViewModel(listOf(item))
    val navController = rememberNavController()
    AppTheme() {
        MealLayout(navController, mealViewModel, onEditItem = {})
    }
}
