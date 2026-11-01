package net.zimmercarral.mealcalculator.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import net.zimmercarral.mealcalculator.R
import net.zimmercarral.mealcalculator.ui.CarbsIcon
import net.zimmercarral.mealcalculator.ui.ProteinIcon
import net.zimmercarral.mealcalculator.ui.theme.AppTheme
import net.zimmercarral.mealcalculator.ui.theme.robotoFamily


@Composable
fun InputField(
    label: String = "",
    value: String = "",
    leadingIcon: ImageVector,
    leadingIconDescription: String,
    onValueChange: (String) -> Unit,
    readOnly: Boolean = false,
    modifier: Modifier = Modifier,
    imeAction: ImeAction = ImeAction.Next,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        singleLine = true,
        modifier = modifier
            .background(color = colorScheme.surfaceContainer
            )
            .width(160.dp)
            .height(60.dp),
        readOnly = readOnly,
        textStyle = TextStyle(
            fontFamily = robotoFamily,
            fontWeight = FontWeight.Normal,
            fontSize = TextUnit(17f, TextUnitType.Sp),
            textAlign = TextAlign.Center,
            color = colorScheme.onSurface,
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal,
            imeAction = imeAction,
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
            }
        ),
        leadingIcon = {
            Icon(
                imageVector = leadingIcon,
                contentDescription = leadingIconDescription,
                modifier = Modifier.size(25.dp),
                tint = colorScheme.primary,
            )
        },
        label = {
            Text(text = label,
                fontFamily = robotoFamily,
                fontWeight = FontWeight.Normal,
                fontSize = TextUnit(11f, TextUnitType.Sp),
                textAlign = TextAlign.Center,
                color = colorScheme.primary
            )
        },
        value = value,
        onValueChange = {
            onValueChange(it)
        },
    )
}


@Preview
@PreviewLightDark
@Composable
private fun InputFieldPreview1() {
    AppTheme() {
        InputField(
            label = "Kohlenhydrate",
            value = "",
            leadingIcon = CarbsIcon,
            leadingIconDescription = stringResource(R.string.carbs_description),
            onValueChange = {},
            imeAction = ImeAction.Next,
        )
    }
}

@Preview
@PreviewLightDark
@Composable
private fun InputFieldPreview2() {
    AppTheme() {
        InputField(
            label = "Eiweiß",
            value = "10.5",
            leadingIcon = ProteinIcon,
            leadingIconDescription = stringResource(R.string.protein_description),
            onValueChange = {},
            imeAction = ImeAction.Next
        )
    }
}