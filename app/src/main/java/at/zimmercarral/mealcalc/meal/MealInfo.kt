package at.zimmercarral.mealcalc.meal

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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

    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween) {
        MealTitle()
        MealList(mealViewModel.mealItems, onEditItem,
            mealViewModel::addItem,
            mealViewModel::removeItem)
        AddItemButton(onAddNewItem)
        MealValues(mealViewModel)
        DeleteButton(mealViewModel, scope, mealDeleted, undo)
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
            .fillMaxWidth()
            .height(60.dp)
            .padding(bottom = 15.dp)
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .fillMaxHeight(),
            enabled = mealViewModel.mealItems.isNotEmpty(),
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
private fun AddItemButton(onAddNewItem: () -> Unit = {}) {
    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End) {
        FloatingActionButton(
            onClick = onAddNewItem,
            modifier = Modifier
                .padding(end = 25.dp, bottom = 5.dp),
            containerColor = colorScheme.primary,
            contentColor = colorScheme.onPrimary
        ) {
            Icon(modifier = Modifier.size(30.dp),
                imageVector = Icons.Rounded.Add,
                contentDescription = "Add new item"
            )
        }
//        Button(
//            modifier = Modifier
//                .padding(end = 25.dp)
//                .background(color = colorScheme.primary)
//                .size(60.dp),
//            onClick = {}
//        ) {
//            Icon(modifier = Modifier.size(60.dp),
//                imageVector = Icons.Rounded.Add,
//                contentDescription = "Add new item"
//            )
//        }
    }
}

@Composable
fun MealTitle() {
    Row(horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp, bottom = 25.dp)
    ) {
        Text(fontFamily = robotoFamily,
            fontSize = TextUnit(25f, TextUnitType.Sp),
            fontWeight = FontWeight.Medium,
            maxLines = 1,
            text = stringResource(R.string.meal_title))
    }
}

@Composable
private fun MealValues(mealViewModel: MealViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth(0.95f)
            .padding(start = 18.dp)
            .border(width = 1.dp, color = colorScheme.primary,
                shape = CardDefaults.outlinedShape),
        //elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Row(
            modifier = Modifier
                .background(color = colorScheme.surfaceContainerLowest)
                .fillMaxWidth()
                .height(45.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Row {
                Text(modifier = Modifier
                    .padding(end = 10.dp, top = 10.dp),
                    color = colorScheme.primary,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold,
                    fontSize = TextUnit(15f, TextUnitType.Sp),
                    maxLines = 1,
                    text = stringResource(R.string.label_carbs)
                )
                Text(modifier = Modifier
                    .padding(end = 10.dp, top = 10.dp),
                    color = colorScheme.error,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold,
                    fontSize = TextUnit(20f, TextUnitType.Sp),
                    maxLines = 1,
                    text = mealViewModel.calculateCarbs()
                )
            }
            Row {
                Text(modifier = Modifier
                    .padding(end = 10.dp, top = 10.dp),
                    color = colorScheme.primary,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold,
                    fontSize = TextUnit(15f, TextUnitType.Sp),
                    maxLines = 1,
                    text = stringResource(R.string.label_calculated_fpe)
                )
                Text(modifier = Modifier
                    .padding(end = 5.dp, top = 10.dp),
                    color = colorScheme.error,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold,
                    fontSize = TextUnit(20f, TextUnitType.Sp),
                    maxLines = 1,
                    text = mealViewModel.calculateFPE())
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
