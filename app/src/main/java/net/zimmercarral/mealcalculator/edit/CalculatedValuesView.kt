package net.zimmercarral.mealcalculator.edit

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.zimmercarral.mealcalculator.R
import net.zimmercarral.mealcalculator.ui.CarbsIcon
import net.zimmercarral.mealcalculator.ui.FatIcon
import net.zimmercarral.mealcalculator.ui.ProteinIcon

val itemCalculator = ItemCalculator()

@Composable
fun CalculatedValuesView(editItemDialogViewModel: EditItemDialogViewModel) {
    val foodItem = editItemDialogViewModel.getFoodItem()
    val calculatedValues = remember(foodItem) {
        itemCalculator.calculateValues(
            foodItem.fatPer100g,
            foodItem.carbsPer100g,
            foodItem.proteinPer100g,
            foodItem.amount,
        )
    }
    Card(
        modifier = Modifier
            .fillMaxWidth(0.95f)
            .padding(start = 18.dp, top = 10.dp, bottom = 30.dp)
            .border(width = 1.dp, color = colorScheme.primary,
                shape = CardDefaults.outlinedShape),
    ) {
        NutrientTotalsRow(calculatedValues)
    }
}

/** Grams of carbs, fat and protein side by side. */
@Composable
fun NutrientTotalsRow(values: CalculatedValues, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .background(color = colorScheme.surfaceContainer)
            .fillMaxWidth()
            .height(60.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        NutrientTotal(CarbsIcon, R.string.label_carbs, 30.dp, values.carbs,
            Modifier.weight(1f))
        VerticalDivider(modifier = Modifier.fillMaxHeight(0.6f))
        NutrientTotal(FatIcon, R.string.label_fat, 24.dp, values.fat,
            Modifier.weight(1f))
        VerticalDivider(modifier = Modifier.fillMaxHeight(0.6f))
        NutrientTotal(ProteinIcon, R.string.label_protein, 32.dp, values.protein,
            Modifier.weight(1f))
    }
}

@Composable
private fun NutrientTotal(
    icon: ImageVector,
    @StringRes label: Int,
    iconSize: Dp,
    grams: Float,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = stringResource(label),
            modifier = Modifier.size(iconSize),
            tint = colorScheme.primary,
        )
        Text(
            modifier = Modifier.padding(start = 6.dp),
            color = colorScheme.onBackground,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            maxLines = 1,
            text = "%.0f ".format(grams) + stringResource(R.string.gram),
        )
    }
}
