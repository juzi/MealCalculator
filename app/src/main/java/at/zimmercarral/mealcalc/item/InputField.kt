package at.zimmercarral.mealcalc.item

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
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
import com.example.compose.AppTheme
import com.example.ui.theme.robotoFamily


@Composable
fun InputField(
    label: String = "",
    value: String = "",
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    imeAction: ImeAction = ImeAction.Next
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        singleLine = true,
        modifier = modifier
            .width(130.dp)
            .height(55.dp),
        textStyle = TextStyle(
            fontFamily = robotoFamily,
            fontWeight = FontWeight.Normal,
            fontSize = TextUnit(16f, TextUnitType.Sp),
            textAlign = TextAlign.Center,
            color = colorScheme.primary
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
        label = {
            Text(text = label,
                fontFamily = robotoFamily,
                fontWeight = FontWeight.Normal,
                fontSize = TextUnit(11f, TextUnitType.Sp),
                textAlign = TextAlign.Center,
                color = colorScheme.onSurface
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
            label = "Fett",
            value = "10.34",
            onValueChange = {},
            imeAction = ImeAction.Next)
    }
}

@Preview
@PreviewLightDark
@Composable
private fun InputFieldPreview2() {
    AppTheme() {
        InputField(
            label = "Eiweiß",
            value = "",
            onValueChange = {},
            imeAction = ImeAction.Next)
    }
}