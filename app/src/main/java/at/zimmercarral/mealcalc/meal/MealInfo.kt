package at.zimmercarral.mealcalc.meal

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import at.zimmercarral.mealcalc.R
import at.zimmercarral.mealcalc.SnackbarAction
import at.zimmercarral.mealcalc.SnackbarController
import at.zimmercarral.mealcalc.SnackbarEvent
import at.zimmercarral.mealcalc.data.db.model.FoodItem
import at.zimmercarral.mealcalc.item.CalculatedValues
import at.zimmercarral.mealcalc.item.itemCalculator
import at.zimmercarral.mealcalc.ui.CarbsIcon
import at.zimmercarral.mealcalc.ui.FatIcon
import at.zimmercarral.mealcalc.ui.ProteinIcon
import at.zimmercarral.mealcalc.ui.theme.AppTheme
import at.zimmercarral.mealcalc.ui.theme.robotoFamily
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun MealInfo(
    mealViewModel: MealViewModel,
    onEditItem: (FoodItem) -> Unit,
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
        verticalArrangement = Arrangement.SpaceBetween) {
        MealTitle()
        MealList(mealViewModel.mealItems, onEditItem,
            mealViewModel::addItem,
            mealViewModel::removeItem)
        ActionButtons(mealViewModel, scope, mealDeleted, undo, onAddNewItem)
        MealValues(calculatedValues)
    }
}

@Composable
private fun DeleteButton(
    mealViewModel: MealViewModel,
    scope: CoroutineScope,
    mealDeleted: String,
    undo: String
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth(0.7f)
            .height(60.dp)
            .padding(bottom = 5.dp, end = 20.dp)
    ) {
        Button(
            modifier = Modifier.fillMaxSize(),
            enabled = mealViewModel.mealItems.isNotEmpty(),
            shape = RoundedCornerShape(15.dp),
            onClick = {
                val currentItems = mealViewModel.mealItems.toList()
                if (currentItems.isNotEmpty()) {
                    mealViewModel.removeAllItems()
                    scope.launch {
                        SnackbarController.sendEvent(
                            event = SnackbarEvent(
                                message = mealDeleted,
                                action = SnackbarAction(
                                    name = undo,
                                    action = { mealViewModel.addItems(currentItems) }
                                )
                            )
                        )
                    }
                }
            }
        ) {
            Text(
                text = stringResource(id = R.string.clear_menu),
                fontFamily = robotoFamily,
                fontWeight = FontWeight.Medium,
                fontSize = TextUnit(18f, TextUnitType.Sp),
            )
        }
    }
}

@Composable
private fun ActionButtons(
    mealViewModel: MealViewModel,
    scope: CoroutineScope = rememberCoroutineScope(),
    mealDeleted: String = stringResource(R.string.meal_deleted),
    undo: String = stringResource(R.string.undo),
    onAddNewItem: () -> Unit = {}
) {
    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        DeleteButton(mealViewModel, scope, mealDeleted, undo)

        FloatingActionButton(
            onClick = onAddNewItem,
            modifier = Modifier
                .padding(end = 25.dp),
            containerColor = colorScheme.primary,
            contentColor = colorScheme.onPrimary,

            ) {
            Icon(modifier = Modifier.size(30.dp),
                imageVector = Icons.Rounded.Add,
                contentDescription = "Add new item"
            )
        }
    }
}

@Composable
fun MealTitle() {
    Row(horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .padding(start = 10.dp, top = 20.dp)
    ) {
        Text(fontFamily = robotoFamily,
            fontSize = TextUnit(25f, TextUnitType.Sp),
            fontWeight = FontWeight.Medium,
            maxLines = 1,
            text = stringResource(R.string.meal_title))
    }
}

@Composable
private fun MealValues(calculatedValues: CalculatedValues) {
    Card(
        modifier = Modifier
            .fillMaxWidth(0.95f)
            .padding(start = 18.dp, bottom = 40.dp)
            .border(width = 1.dp, color = colorScheme.primary,
                shape = CardDefaults.outlinedShape),
    ) {
        Row(
            modifier = Modifier
                .background(color = colorScheme.surfaceContainer)
                .fillMaxWidth()
                .height(60.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                modifier = Modifier
                    .padding(start = 20.dp, end = 10.dp, top = 10.dp, bottom = 10.dp),
            ) {
                Icon(
                    imageVector = CarbsIcon,
                    contentDescription = stringResource(R.string.label_carbs),
                    modifier = Modifier.size(34.dp),
                    tint = colorScheme.primary,
                )
                Text(modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, top = 10.dp),
                    color = colorScheme.onBackground,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold,
                    fontSize = TextUnit(20f, TextUnitType.Sp),
                    maxLines = 1,
                    text = "%.0f ".format(calculatedValues.calculatedBEGrams) + stringResource(R.string.gram)
                )
            }
            Row(
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 10.dp),
            ) {
                Icon(
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .size(27.dp),
                    imageVector = FatIcon,
                    contentDescription = stringResource(R.string.label_fat),
                    tint = colorScheme.primary,
                )
                Icon(
                    modifier = Modifier
                        .padding(start = 5.dp, top = 8.dp)
                        .size(25.dp),
                    imageVector = Icons.Rounded.Add,
                    contentDescription = stringResource(R.string.plus),
                    tint = colorScheme.primary,
                )
                Icon(
                    modifier = Modifier
                        .size(38.dp),
                    imageVector = ProteinIcon,
                    contentDescription = stringResource(R.string.label_protein),
                    tint = colorScheme.primary,
                )
                Text(modifier = Modifier
                    .padding(start = 10.dp, end = 5.dp, top = 10.dp),
                    color = colorScheme.onBackground,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold,
                    fontSize = TextUnit(20f, TextUnitType.Sp),
                    maxLines = 1,
                    text = "%.0f ".format(calculatedValues.calculatedFPEGrams) + stringResource(R.string.gram)
                )
            }
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

    AppTheme() {
        MealInfo(mealViewModel, onEditItem = {})
    }
}
