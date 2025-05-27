package at.zimmercarral.mealcalc.meal

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import at.zimmercarral.mealcalc.R
import at.zimmercarral.mealcalc.edit.CalculatedValues
import at.zimmercarral.mealcalc.ui.CarbsIcon
import at.zimmercarral.mealcalc.ui.FatIcon
import at.zimmercarral.mealcalc.ui.ProteinIcon


@Composable
fun MealValues(calculatedValues: CalculatedValues) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 5.dp, end = 5.dp, bottom = 10.dp)
            .border(width = 1.dp, color = colorScheme.primary,
                shape = CardDefaults.outlinedShape),
    ) {
        Row(
            modifier = Modifier
                .background(color = colorScheme.surfaceContainer)
                .fillMaxWidth()
                .height(60.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, top = 8.dp, bottom = 10.dp),
            ) {
                Icon(
                    imageVector = CarbsIcon,
                    contentDescription = stringResource(R.string.label_carbs),
                    modifier = Modifier.size(34.dp),
                    tint = colorScheme.primary,
                )
                Text(modifier = Modifier
                    .padding(start = 10.dp, end = 8.dp, top = 8.dp),
                    color = colorScheme.onBackground,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold,
                    fontSize = TextUnit(20f, TextUnitType.Sp),
                    maxLines = 1,
                    text = "%.0f ".format(calculatedValues.calculatedBEGrams) + stringResource(R.string.gram)
                )
            }
            VerticalDivider(modifier = Modifier.fillMaxHeight(0.8f))
            Row(
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 10.dp),
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
                    .padding(start = 10.dp, end = 5.dp, top = 8.dp),
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
