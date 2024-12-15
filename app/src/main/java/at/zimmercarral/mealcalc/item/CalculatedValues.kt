package at.zimmercarral.mealcalc.item

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import at.zimmercarral.mealcalc.R
import at.zimmercarral.mealcalc.ui.theme.robotoFamily

@Composable
fun CalculatedValues(editItemDialogViewModel: EditItemDialogViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth(0.95f)
            .padding(start = 25.dp, top = 10.dp, bottom = 10.dp)
            .border(width = 1.dp, color = colorScheme.primary,
                shape = CardDefaults.outlinedShape),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Row(
            modifier = Modifier
                .background(color = colorScheme.surfaceContainer)
                .fillMaxWidth()
                .height(45.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Row {
                Text(modifier = Modifier
                    .padding(end = 10.dp, top = 10.dp),
                    color = colorScheme.primary,
                    fontFamily = robotoFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = TextUnit(15f, TextUnitType.Sp),
                    maxLines = 1,
                    text = stringResource(R.string.label_carbs)
                )
                Text(modifier = Modifier
                    .padding(end = 10.dp, top = 10.dp),
                    color = colorScheme.error,
                    fontFamily = robotoFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = TextUnit(15f, TextUnitType.Sp),
                    maxLines = 1,
                    // TODO calculate carbs
                    text = editItemDialogViewModel.carbsPer100g.value
                )
            }
            Row {
                Text(modifier = Modifier
                    .padding(end = 10.dp, top = 10.dp),
                    color = colorScheme.primary,
                    fontFamily = robotoFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = TextUnit(15f, TextUnitType.Sp),
                    maxLines = 1,
                    text = stringResource(R.string.label_calculated_fpe)
                )
                Text(modifier = Modifier
                    .padding(end = 5.dp, top = 10.dp),
                    color = colorScheme.error,
                    fontFamily = robotoFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = TextUnit(15f, TextUnitType.Sp),
                    maxLines = 1,
                    // TODO calculate fpe
                    text = editItemDialogViewModel.fatPer100g.value
                )
            }
        }
    }
}

