package net.zimmercarral.mealcalculator.meal

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.zimmercarral.mealcalculator.edit.CalculatedValues
import net.zimmercarral.mealcalculator.edit.NutrientTotalsRow

@Composable
fun MealValues(calculatedValues: CalculatedValues) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 5.dp, end = 5.dp, bottom = 10.dp)
            .border(width = 1.dp, color = colorScheme.primary,
                shape = CardDefaults.outlinedShape),
    ) {
        NutrientTotalsRow(calculatedValues)
    }
}
