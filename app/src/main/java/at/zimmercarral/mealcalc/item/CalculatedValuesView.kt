package at.zimmercarral.mealcalc.item

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import at.zimmercarral.mealcalc.R
import at.zimmercarral.mealcalc.ui.CarbsIcon
import at.zimmercarral.mealcalc.ui.FatIcon
import at.zimmercarral.mealcalc.ui.ProteinIcon

val itemCalculator = ItemCalculator()

@Composable
fun CalculatedValuesView(editItemDialogViewModel: EditItemDialogViewModel) {
    val calculatedValues = remember(editItemDialogViewModel.getFoodItem()) {
        itemCalculator.calculateValues(
            editItemDialogViewModel.getFoodItem().fatPer100g,
            editItemDialogViewModel.getFoodItem().carbsPer100g,
            editItemDialogViewModel.getFoodItem().proteinPer100g,
            editItemDialogViewModel.getFoodItem().amount)
    }
    Card(
        modifier = Modifier
            .fillMaxWidth(0.95f)
            .padding(start = 18.dp, top = 10.dp, bottom = 30.dp)
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
                    .padding(start = 15.dp, end = 10.dp, top = 10.dp, bottom = 10.dp),
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
                    text = "%.0f".format(calculatedValues.calculatedBEGrams) + " " + stringResource(
                        R.string.gram)
                )
            }
            Row(
                modifier = Modifier
                    .padding(start = 15.dp, end = 10.dp, top = 10.dp, bottom = 10.dp),
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
                        .padding(start = 4.dp, top = 8.dp)
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
                    text = "%.0f".format(calculatedValues.calculatedFPEGrams) + " " + stringResource(
                        R.string.gram)
                )
            }
        }
    }
}

