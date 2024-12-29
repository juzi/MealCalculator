package at.zimmercarral.mealcalc.meal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import at.zimmercarral.mealcalc.R
import at.zimmercarral.mealcalc.SnackbarAction
import at.zimmercarral.mealcalc.SnackbarController
import at.zimmercarral.mealcalc.SnackbarEvent
import at.zimmercarral.mealcalc.ui.theme.robotoFamily
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MealDeleteButton(
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
                                ),
                                duration = SnackbarDuration.Long
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
