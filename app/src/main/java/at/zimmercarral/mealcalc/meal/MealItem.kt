package at.zimmercarral.mealcalc.meal

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import at.zimmercarral.mealcalc.R
import at.zimmercarral.mealcalc.SnackbarAction
import at.zimmercarral.mealcalc.SnackbarController
import at.zimmercarral.mealcalc.SnackbarEvent
import at.zimmercarral.mealcalc.data.db.model.FoodItem
import at.zimmercarral.mealcalc.ui.theme.AppTheme
import at.zimmercarral.mealcalc.ui.theme.robotoFamily
import kotlinx.coroutines.launch

@Composable
fun MealItem(
    item: FoodItem,
    onEditItem: (FoodItem) -> Unit,
    onAddItem: (FoodItem) -> Unit,
    onDeleteItem: (FoodItem) -> Unit
) {
    var isDismissed by remember { mutableStateOf(false) }
    val itemDeleted = stringResource(R.string.item_was_deleted, item.name)
    val undo = stringResource(R.string.undo)
    val scope = rememberCoroutineScope()

    // Swipe-to-dismiss configuration
    val dismissState = rememberSwipeToDismissBoxState(
        positionalThreshold = { distance: Float -> distance * 0.5f },
        confirmValueChange = { dismissValue ->
            if (dismissValue == SwipeToDismissBoxValue.EndToStart) {
                // When swiped to start (left), trigger a callback to update state
                isDismissed = true
                val deletedItem = item
                onDeleteItem(item)
                scope.launch {
                    SnackbarController.sendEvent(
                        event = SnackbarEvent(
                            message = itemDeleted,
                            action = SnackbarAction(
                                name = undo,
                                action = {
                                    onAddItem(deletedItem)
                                    isDismissed = false
                                }
                            )
                        )
                    )
                }
                true
            } else {
                false
            }
        }
    )
    SwipeToDismissBox(
        state = dismissState,
        enableDismissFromStartToEnd = false,
        backgroundContent = {
            DeleteBackground(dismissState)
        },
    ) {
        Card(modifier = Modifier
            .fillMaxWidth(0.95f)
            .height(60.dp)
            .padding(start = 3.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = { onEditItem(item) }
                )
            },
            shape = RoundedCornerShape(7.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .background(color = colorScheme.primaryContainer)
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    var name = item.name.substring(0, Math.min(item.name.length, 27))
                    if (item.name.length > 27) {
                        name += "..."
                    }
                    Text(modifier = Modifier
                        .padding(start = 10.dp, top = 17.dp, bottom = 5.dp)
                        .fillMaxWidth(0.70f)
                        .horizontalScroll(rememberScrollState()),
                        maxLines = 1,
                        fontFamily = robotoFamily,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Normal,
                        color = colorScheme.primary,
                        text = name
                    )
                    Text(modifier = Modifier
                        .padding(start = 5.dp, end = 10.dp, top = 17.dp),
                        maxLines = 1,
                        fontFamily = robotoFamily,
                        fontWeight = FontWeight.W600,
                        color = colorScheme.primary,
                        text = " " + item.amount + " gr"
                    )
                }
            }
        }
    }
}

@Preview
@PreviewLightDark
@Composable
private fun MealItemPreview() {
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
    AppTheme() {
        MealItem(item = item,
            onEditItem = {},
            onAddItem = {},
            onDeleteItem = {})
    }
}
