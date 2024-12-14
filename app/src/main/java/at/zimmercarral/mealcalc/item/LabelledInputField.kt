package at.zimmercarral.mealcalc.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import at.zimmercarral.mealcalc.R
import at.zimmercarral.mealcalc.common.Label
import com.example.compose.AppTheme

@Composable
fun LabelledInputField(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    imeAction: ImeAction = ImeAction.Next
) {
    Row() {
        Label(text = label,
            modifier = modifier
                .padding(top = 16.dp, end = 13.dp)
                .background(color = colorScheme.surface))
        InputField(value = value,
            onValueChange = onValueChange,
            modifier = modifier,
            imeAction = imeAction,
            leadingIconResource = R.drawable.carbs,
            leadingIconDescription = stringResource(R.string.carbs_description))

    }
}

@Preview
@PreviewLightDark
@Composable
private fun InputFieldPreview() {
    AppTheme() {
        Column {
            LabelledInputField(label = "EW",
                value = "10.34",
                onValueChange = {},
                imeAction = ImeAction.Next)
            LabelledInputField(label = "Fett",
                value = "100.34",
                onValueChange = {},
                imeAction = ImeAction.Next)
        }
    }
}