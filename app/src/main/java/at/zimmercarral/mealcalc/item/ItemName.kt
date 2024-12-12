package at.zimmercarral.mealcalc.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp

@Composable
fun ItemName(name: String, onValueChange: (String) -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 5.dp, end = 5.dp, top = 5.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Card(
            elevation = CardDefaults.cardElevation(12.dp), // Enhanced shadow
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(35.dp),
            shape = RoundedCornerShape(20.dp),
        ) {
            BasicTextField(
                singleLine = true,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 8.dp)
                    .background(
//                    shape = RoundedCornerShape(10.dp),
                        color = Color.Transparent
                    ),
                textStyle = TextStyle(
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Medium,
                    fontSize = TextUnit(17f, TextUnitType.Sp),
                    textAlign = TextAlign.Center,
                    color = colorScheme.onSurface
                ),
                decorationBox = { innerTextField ->
                    Row(modifier = Modifier.padding(horizontal = 5.dp)) {
                        innerTextField()
                        Icon(Icons.Rounded.Edit, contentDescription = null)

                    }
                },
//                colors = TextFieldDefaults.colors(
//                    cursorColor = colorScheme.primary,
//                    focusedIndicatorColor = Color.Transparent,
//                    unfocusedIndicatorColor = Color.Transparent
//                ),
//                trailingIcon = {
//                    ActionButton(stringResource(id = R.string.edit_name),
//                        Icons.Rounded.Edit, color = colorScheme.primary,
//                        modifier = Modifier.size(20.dp),
//                        onClick = { /*TODO*/ })
//                },

                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                    }
                ),
                value = name,
                onValueChange = onValueChange,
            )
        }
    }
}
