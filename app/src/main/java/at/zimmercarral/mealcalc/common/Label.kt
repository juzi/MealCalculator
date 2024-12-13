package at.zimmercarral.mealcalc.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import at.zimmercarral.mealcalc.R
import com.example.compose.AppTheme
import com.example.ui.theme.robotoFamily

@Composable
fun Label(text: String, modifier: Modifier = Modifier) {
    Text(modifier = modifier
        .width(60.dp),
        color = colorScheme.onSurface,
        fontFamily = robotoFamily,
        fontWeight = FontWeight.Medium,
        fontSize = TextUnit(19f, TextUnitType.Sp),
        textAlign = TextAlign.End,
        maxLines = 1,
        text = text)
}

@Preview
@PreviewLightDark
@Composable
private fun LabelPreview() {
    AppTheme() {
        Label(text = stringResource(id = R.string.label_protein),
            modifier = Modifier
                .height(45.dp)
                .background(color = colorScheme.surfaceContainerHigh)

        )
    }
}