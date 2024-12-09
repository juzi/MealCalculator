package at.zimmercarral.mealcalc

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircleOutline
import androidx.compose.material.icons.rounded.ArrowCircleDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import at.zimmercarral.mealcalc.meal.MealViewModel

@Composable
fun CurrentItemView(currentItemViewModel: CurrentItemViewModel, mealViewModel: MealViewModel) {
    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            },
        verticalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        ItemName(currentItemViewModel.name.value, currentItemViewModel::setName)
        NutrionalValues(currentItemViewModel, mealViewModel)
        CalculatedValues(currentItemViewModel)
    }
}


@Composable
fun ItemName(name: String, onValueChange: (String) -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 5.dp, end = 5.dp, top = 5.dp))
    {
        Card(
            elevation = CardDefaults.cardElevation(12.dp), // Enhanced shadow
            modifier = Modifier
                .width(400.dp),
            shape = RoundedCornerShape(20.dp),
        ) {
            TextField(
                singleLine = true,
                textStyle = TextStyle(
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Medium,
                    fontSize = TextUnit(15f, TextUnitType.Sp),
                    textAlign = TextAlign.Center,
                ),
                modifier = Modifier
                    .width(400.dp)
                    .height(47.dp)
                    .border(
                        width = 1.dp,
                        color = Color.LightGray,
                        shape = CircleShape
                    ),
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


@Composable
private fun NutrionalValues(
    currentItemViewModel: CurrentItemViewModel,
    mealViewModel: MealViewModel
) {
    Row() {
        ItemInfo(currentItemViewModel)
        Column(modifier = Modifier
            .padding(top = 80.dp, start = 15.dp)
            .weight(0.7f),
            verticalArrangement = Arrangement.Center
        ) {
            var buttonImage = Icons.Rounded.AddCircleOutline
            if (currentItemViewModel.inUpdateMode.value) {
                buttonImage = Icons.Rounded.ArrowCircleDown
            }
            ActionButton(stringResource(id = R.string.add_item_to_meal),
                buttonImage,
                color = Color(80, 127, 80, 255),
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 5.dp)
                    .size(70.dp),
                onClick = {
                    mealViewModel.addOrUpdateItem(currentItemViewModel.getFoodItem())
                })
        }
    }
}


@Composable
fun ItemInfo(currentItemViewModel: CurrentItemViewModel) {
    val rowModifier = Modifier.padding(start = 5.dp, end = 5.dp, top = 5.dp, bottom = 0.dp)
    Column(modifier = Modifier
        .padding(5.dp),
        verticalArrangement = Arrangement.spacedBy((-8).dp)
    ) {
        Row(modifier = rowModifier) {
            Label(text = stringResource(R.string.label_fat))
            InputField(value = currentItemViewModel.fatPer100g.value,
                onValueChange = {
                    val input = it.replace(",", ".")
                    if (isValidFloat(input)) {
                        currentItemViewModel.setFatPer100g(input)
                    }
                },
                modifier = Modifier.focusRequester(currentItemViewModel.fatFocusRequester.value))
        }
        Row(modifier = rowModifier) {
            Label(text = stringResource(R.string.label_carbs))
            InputField(value = currentItemViewModel.carbsPer100g.value,
                onValueChange = {
                    val input = it.replace(",", ".")
                    if (isValidFloat(input)) {
                        currentItemViewModel.setCarbsPer100g(input)
                    }
                })
        }
        Row(modifier = rowModifier) {
            Label(text = stringResource(R.string.label_protein))
            InputField(value = currentItemViewModel.proteinPer100g.value,
                onValueChange = {
                    val input = it.replace(",", ".")
                    if (isValidFloat(input)) {
                        currentItemViewModel.setProteinPer100g(input)
                    }
                })
        }
        Row(modifier = rowModifier) {
            Label(text = stringResource(R.string.label_gram))
            InputField(value = currentItemViewModel.amount.value,
                onValueChange = {
                    val input = it.replace(",", ".")
                    if (isValidInt(input)) {
                        currentItemViewModel.setAmount(input)
                    }
                },
                imeAction = ImeAction.Done)
        }
    }
}


@Composable
private fun CalculatedValues(currentItemViewModel: CurrentItemViewModel) {
    Card(
        modifier = Modifier
            .background(color = Color.Transparent)
            .fillMaxWidth()
            .padding(top = 0.dp, start = 20.dp, end = 20.dp),
        elevation = CardDefaults.cardElevation(5.dp),
    ) {
        Row(
            modifier = Modifier
                .background(color = Color.LightGray.copy(alpha = 0.1f))
                .fillMaxWidth()
                .height(45.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Row {
                Label(stringResource(R.string.label_calculated_carbs),
                    Modifier
                        .padding(bottom = 5.dp)
                )
                Text(modifier = Modifier
                    .padding(start = 0.dp, end = 10.dp, top = 10.dp, bottom = 0.dp),
                    color = Color.Red,
                    fontFamily = FontFamily.SansSerif,
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
                        .padding(bottom = 5.dp))
                Text(modifier = Modifier
                    .padding(start = 0.dp, end = 40.dp, top = 10.dp, bottom = 0.dp),
                    color = Color.Red,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold,
                    fontSize = TextUnit(20f, TextUnitType.Sp),
                    maxLines = 1,
                    text = currentItemViewModel.calculateFPE())
            }
        }
    }
}


@Composable
fun InputField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    imeAction: ImeAction = ImeAction.Next
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Card(
        shape = RoundedCornerShape(30.dp),
        elevation = CardDefaults.cardElevation(10.dp), // Enhanced shadow
        modifier = Modifier
            .width(90.dp)
    ) {
        TextField(
            singleLine = true,
            modifier = modifier
                .width(100.dp)
                .height(45.dp)
                .background(
                    shape = CircleShape,
                    brush = Brush.horizontalGradient(
                        listOf(Color.White, Color.White),
                    ),
                )
                .border(
                    width = 1.dp,
                    color = Color.LightGray,
                    shape = CircleShape
                ),
            textStyle = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Medium,
                fontSize = TextUnit(14f, TextUnitType.Sp),
                textAlign = TextAlign.Center,
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
            value = value,
            onValueChange = {
                onValueChange(it)
            },
        )
    }
}

private fun isValidInt(string: String): Boolean {
    return numberOfOccurences('.', string) == 0 &&
            numberOfOccurences(' ', string) == 0 &&
            numberOfOccurences('-', string) == 0
}

private fun isValidFloat(string: String): Boolean {
    return numberOfOccurences('.', string) <= 1 &&
            numberOfOccurences(' ', string) == 0 &&
            numberOfOccurences('-', string) == 0
}

private fun numberOfOccurences(char: Char, string: String): Int {
    var count = 0
    for (c in string) {
        if (c == char) {
            count++
        }
    }
    return count
}
