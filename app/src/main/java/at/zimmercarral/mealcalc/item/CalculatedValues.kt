package at.zimmercarral.mealcalc.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
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
import at.zimmercarral.mealcalc.common.Label
import com.example.ui.theme.robotoFamily

@Composable
fun CalculatedValues(currentItemViewModel: CurrentItemViewModel) {
    Card(
        modifier = Modifier
            .background(color = colorScheme.surface)
            .fillMaxWidth()
            .padding(top = 5.dp, start = 20.dp, end = 20.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = colorScheme.surfaceContainerHigh)
                .height(45.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Row {
                Label(stringResource(R.string.label_calculated_carbs),
                    Modifier
                        .background(color = colorScheme.surfaceContainerHigh)
                        .padding(top = 10.dp, end = 15.dp)
                )
                Text(modifier = Modifier
                    .padding(start = 0.dp, end = 10.dp, top = 10.dp, bottom = 0.dp),
                    color = colorScheme.error,
                    fontFamily = robotoFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = TextUnit(20f, TextUnitType.Sp),
                    maxLines = 1,
                    text = currentItemViewModel.calculateCarbs()
                )
            }
            Row {
                Label(stringResource(R.string.label_calculated_fpe),
                    Modifier
                        .width(110.dp)
                        .background(color = colorScheme.surfaceContainerHigh)
                        .padding(top = 10.dp, end = 15.dp))
                Text(modifier = Modifier
                    .padding(start = 0.dp, end = 40.dp, top = 10.dp, bottom = 0.dp),
                    color = colorScheme.error,
                    fontFamily = robotoFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = TextUnit(20f, TextUnitType.Sp),
                    maxLines = 1,
                    text = currentItemViewModel.calculateFPE())
            }
        }
    }
}
