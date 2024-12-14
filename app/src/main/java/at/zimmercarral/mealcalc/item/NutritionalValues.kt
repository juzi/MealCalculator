package at.zimmercarral.mealcalc.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import at.zimmercarral.mealcalc.R
import at.zimmercarral.mealcalc.data.db.model.FoodItem
import com.example.compose.AppTheme

@Composable
fun NutritionalValues(currentItemViewModel: CurrentItemViewModel) {
    Column(modifier = Modifier
        .padding(start = 40.dp, top = 10.dp, end = 20.dp, bottom = 10.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        Row() {
            InputField(
                label = stringResource(R.string.label_fat),
                value = currentItemViewModel.fatPer100g.value,
                leadingIconResource = R.drawable.fat,
                leadingIconDescription = stringResource(R.string.fat_description),
                onValueChange = {
                    val input = it.replace(",", ".")
                    if (isValidFloat(input)) {
                        currentItemViewModel.setFatPer100g(input)
                    }
                },
                modifier = Modifier.focusRequester(currentItemViewModel.fatFocusRequester.value),
            )
        }
        Row() {
            InputField(label = stringResource(R.string.label_carbs),
                value = currentItemViewModel.carbsPer100g.value,
                leadingIconResource = R.drawable.carbs,
                leadingIconDescription = stringResource(R.string.carbs_description),
                onValueChange = {
                    val input = it.replace(",", ".")
                    if (isValidFloat(input)) {
                        currentItemViewModel.setCarbsPer100g(input)
                    }
                }
            )
        }
        Row() {
            InputField(
                label = stringResource(R.string.label_protein),
                value = currentItemViewModel.proteinPer100g.value,
                leadingIconResource = R.drawable.protein,
                leadingIconDescription = stringResource(R.string.protein_description),
                onValueChange = {
                    val input = it.replace(",", ".")
                    if (isValidFloat(input)) {
                        currentItemViewModel.setProteinPer100g(input)
                    }
                },
            )
        }
        Row() {
            InputField(label = stringResource(R.string.label_gram),
                value = currentItemViewModel.amount.value,
                leadingIconResource = R.drawable.weight,
                leadingIconDescription = stringResource(R.string.amount_description),
                onValueChange = {
                    val input = it.replace(",", ".")
                    if (isValidInt(input)) {
                        currentItemViewModel.setAmount(input)
                    }
                },
                modifier = Modifier.focusRequester(currentItemViewModel.amountFocusRequester.value),
                imeAction = ImeAction.Done,
            )
        }
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

@Preview
@PreviewLightDark
@Composable
private fun InputFieldPreview() {
    AppTheme() {
        val viewModel = CurrentItemViewModel()
        viewModel.setItem(FoodItem(
            uuid = "123",
            name = "Apfelpfannkuchen",
            fatPer100g = 0f,
            carbsPer100g = 12f,
            proteinPer100g = 0f,
            amount = 100,
            lastModifiedAt = null,
            isDeleted = false,
        ))
        NutritionalValues(currentItemViewModel = viewModel)
    }
}

