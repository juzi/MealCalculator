package at.zimmercarral.mealcalc.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
import at.zimmercarral.mealcalc.R
import at.zimmercarral.mealcalc.common.ActionButton
import com.example.compose.AppTheme
import com.example.ui.theme.robotoFamily

@Composable
fun ItemName(name: String, onValueChange: (String) -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        singleLine = true,
        modifier = Modifier
            .background(color = colorScheme.surface)
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        textStyle = TextStyle(
            fontFamily = robotoFamily,
            fontWeight = FontWeight.Normal,
            fontSize = TextUnit(19f, TextUnitType.Sp),
            textAlign = TextAlign.Center,
            color = colorScheme.onSurface
        ),
        colors = TextFieldDefaults.colors(
            cursorColor = colorScheme.primary,
            focusedIndicatorColor = colorScheme.primary,
            unfocusedIndicatorColor = colorScheme.primary,
            focusedContainerColor = colorScheme.surface,
            unfocusedContainerColor = colorScheme.surface,
        ),
        trailingIcon = {
            ActionButton("",
                Icons.Rounded.Clear, color = colorScheme.primary,
                modifier = Modifier.size(24.dp),
                onClick = { onValueChange("") })
        },

        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
            }
        ),
        label = {
            Text(text = stringResource(R.string.label_name),
                fontFamily = robotoFamily,
                fontWeight = FontWeight.Normal,
                fontSize = TextUnit(11f, TextUnitType.Sp),
                textAlign = TextAlign.Center,
                color = colorScheme.onSurface
            )
        },
        value = name,
        onValueChange = onValueChange,
    )
}

@Preview
@PreviewLightDark
@Composable
private fun InputFieldPreview() {
    AppTheme() {
        ItemName(name = "Apfelstrudel mit Sahne", onValueChange = {})
    }
}
