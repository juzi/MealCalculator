package at.zimmercarral.mealcalc.meal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DeleteOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import at.zimmercarral.mealcalc.R
import at.zimmercarral.mealcalc.common.ActionButton
import at.zimmercarral.mealcalc.common.Label
import at.zimmercarral.mealcalc.item.EditItemDialogViewModel


@Composable
fun MealInfo(
    editItemDialogViewModel: EditItemDialogViewModel,
    mealViewModel: MealViewModel
) {
    Column {
        HorizontalDivider(modifier = Modifier.padding(top = 10.dp),
            thickness = 1.dp,
            color = Color.LightGray)
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            Text(modifier = Modifier
                .padding(start = 10.dp, end = 230.dp, top = 12.dp, bottom = 0.dp),
                fontFamily = FontFamily.SansSerif,
                fontSize = TextUnit(18f, TextUnitType.Sp),
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                text = stringResource(R.string.meal_title))

            ActionButton(description = stringResource(id = R.string.clear_menu),
                icon = Icons.Rounded.DeleteOutline,
                modifier = Modifier
                    .padding(start = 0.dp, end = 10.dp, top = 0.dp, bottom = 0.dp)
                    .size(50.dp),
                onClick = {
                    mealViewModel.removeAllItems()
                })
        }
        MealListView(mealViewModel.mealItems, editItemDialogViewModel)
        MealValues(mealViewModel)
    }
}

@Composable
private fun MealValues(mealViewModel: MealViewModel) {
    Card(
        modifier = Modifier
            .background(color = Color.Transparent)
            .fillMaxWidth()
            .padding(top = 5.dp, start = 10.dp, end = 10.dp, bottom = 2.dp),
        elevation = CardDefaults.cardElevation(5.dp),
    ) {
        Row(
            modifier = Modifier
                .background(color = Color.LightGray.copy(alpha = 0.1f))
                .fillMaxWidth()
                .height(45.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Text(modifier = Modifier
                .padding(start = 5.dp, end = 5.dp, top = 13.dp, bottom = 0.dp),
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold,
                fontSize = TextUnit(18f, TextUnitType.Sp),
                maxLines = 1,
                text = stringResource(R.string.sum))
            Row {
                Label(stringResource(R.string.label_calculated_carbs),
                    Modifier
                        .padding(top = 2.dp, bottom = 0.dp)
                )
                Text(modifier = Modifier
                    .padding(start = 0.dp, end = 5.dp, top = 13.dp, bottom = 0.dp),
                    color = Color.Red,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold,
                    fontSize = TextUnit(20f, TextUnitType.Sp),
                    maxLines = 1,
                    text = mealViewModel.calculateCarbs()
                )
            }
            Row {
                Label(stringResource(R.string.label_calculated_fpe),
                    Modifier
                        .width(110.dp)
                        .padding(top = 2.dp, bottom = 0.dp))
                Text(modifier = Modifier
                    .padding(start = 0.dp, end = 5.dp, top = 13.dp, bottom = 0.dp),
                    color = Color.Red,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold,
                    fontSize = TextUnit(20f, TextUnitType.Sp),
                    maxLines = 1,
                    text = mealViewModel.calculateFPE())
            }
        }
    }
}

