package net.zimmercarral.mealcalculator.meal

import android.R.attr.text
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import net.zimmercarral.mealcalculator.R
import net.zimmercarral.mealcalculator.SnackbarAction
import net.zimmercarral.mealcalculator.SnackbarController
import net.zimmercarral.mealcalculator.SnackbarEvent
import net.zimmercarral.mealcalculator.data.db.model.FoodItem
import net.zimmercarral.mealcalculator.ui.theme.AppTheme
import net.zimmercarral.mealcalculator.ui.theme.robotoFamily

@Composable
fun MealItem(
    index: Int,
    item: FoodItem,
    onEditItem: (Int) -> Unit,
    onAddItem: (FoodItem) -> Unit,
    onDeleteItem: (FoodItem) -> Unit
) {
    // Swipe-to-dismiss configuration
    val dismissState = rememberSwipeToDismissBoxState(
        positionalThreshold = { distance: Float -> distance * 0.5f },
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
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        println("*** onLongPress on $index")
                        onEditItem(index)
                    }
                )
            },
            shape = RoundedCornerShape(7.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
            colors = CardDefaults.cardColors(
                containerColor = colorScheme.primaryContainer
            )
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                var name =
                    item.name.substring(0, Math.min(item.name.length, 27))
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
                    color = colorScheme.onPrimaryContainer,
                    text = name
                )
                Text(modifier = Modifier
                    .padding(start = 5.dp, end = 10.dp, top = 17.dp),
                    maxLines = 1,
                    fontFamily = robotoFamily,
                    fontWeight = FontWeight.W600,
                    color = colorScheme.onPrimaryContainer,
                    text = " " + item.amount + " gr"
                )
            }
        }
    }
    if (dismissState.currentValue == SwipeToDismissBoxValue.EndToStart) {
        val itemDeleted = stringResource(R.string.item_was_deleted, item.name)
        val undo = stringResource(R.string.undo)
        // When swiped to start (left), trigger a callback to update state
        LaunchedEffect(dismissState.currentValue) {
            onDeleteItem(item)
            dismissState.snapTo(SwipeToDismissBoxValue.Settled)
            SnackbarController.sendEvent(
                event = SnackbarEvent(
                    message = itemDeleted,
                    action = SnackbarAction(
                        name = undo,
                        action = {
                            onAddItem(item)
                        },
                    ),
                    duration = SnackbarDuration.Short
                )
            )
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
        MealItem(
            index = 0,
            item = item,
            onEditItem = {},
            onAddItem = {},
            onDeleteItem = {})
    }
}
