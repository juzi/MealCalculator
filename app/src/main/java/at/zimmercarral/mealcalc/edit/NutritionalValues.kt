package at.zimmercarral.mealcalc.edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.room.Room
import at.zimmercarral.mealcalc.R
import at.zimmercarral.mealcalc.data.db.FoodItemDatabase
import at.zimmercarral.mealcalc.data.db.model.FoodItem
import at.zimmercarral.mealcalc.data.repo.FoodItemRepository
import at.zimmercarral.mealcalc.ui.AmountIcon
import at.zimmercarral.mealcalc.ui.CarbsIcon
import at.zimmercarral.mealcalc.ui.FatIcon
import at.zimmercarral.mealcalc.ui.ProteinIcon
import at.zimmercarral.mealcalc.ui.theme.AppTheme

@Composable
fun NutritionalValues(editItemDialogViewModel: EditItemDialogViewModel) {
    Column(modifier = Modifier
        .padding(start = 40.dp, top = 10.dp, end = 20.dp, bottom = 10.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        Row() {
            InputField(
                label = stringResource(R.string.label_fat),
                value = editItemDialogViewModel.fatPer100g.value,
                leadingIcon = FatIcon,
                leadingIconDescription = stringResource(R.string.fat_description),
                onValueChange = {
                    val input = it.replace(",", ".").trim()
                    if (isValidFloat(input)) {
                        editItemDialogViewModel.setFatPer100g(input)
                    }
                },
                modifier = Modifier.focusRequester(editItemDialogViewModel.fatFocusRequester.value),
            )
        }
        Row() {
            InputField(
                label = stringResource(R.string.label_carbs),
                value = editItemDialogViewModel.carbsPer100g.value,
                leadingIcon = CarbsIcon,
                leadingIconDescription = stringResource(R.string.carbs_description),
                onValueChange = {
                    val input = it.replace(",", ".")
                    if (isValidFloat(input)) {
                        editItemDialogViewModel.setCarbsPer100g(input)
                    }
                },
            )
        }
        Row() {
            InputField(
                label = stringResource(R.string.label_protein),
                value = editItemDialogViewModel.proteinPer100g.value,
                leadingIcon = ProteinIcon,
                leadingIconDescription = stringResource(R.string.protein_description),
                onValueChange = {
                    val input = it.replace(",", ".")
                    if (isValidFloat(input)) {
                        editItemDialogViewModel.setProteinPer100g(input)
                    }
                },
            )
        }
        Row() {
            AmountInputField(
                label = stringResource(R.string.label_amount),
                value = editItemDialogViewModel.amount.value,
                leadingIcon = AmountIcon,
                leadingIconDescription = stringResource(R.string.amount_description),
                onValueChange = { newTextFieldValue ->
                    editItemDialogViewModel.setAmount(newTextFieldValue)
                },
                modifier = Modifier.focusRequester(editItemDialogViewModel.amountFocusRequester.value),
                imeAction = ImeAction.Done,
            )
        }
    }
}

private fun isValidInt(input: String): Boolean {
    if (input.isEmpty()) {
        return true
    }
    return input.toIntOrNull() != null
}

private fun isValidFloat(input: String): Boolean {
    if (input.isEmpty()) {
        return true
    }
    return input.toDoubleOrNull() != null
}

@Preview
@PreviewLightDark
@Composable
private fun InputFieldPreview() {
    AppTheme() {
        val context = LocalContext.current
        val db = Room.inMemoryDatabaseBuilder(
            context, FoodItemDatabase::class.java
        ).build()
        val dao = db.dao()
        val viewModel = EditItemDialogViewModel(FoodItemRepository(dao, null))
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
        NutritionalValues(editItemDialogViewModel = viewModel)
    }
}

