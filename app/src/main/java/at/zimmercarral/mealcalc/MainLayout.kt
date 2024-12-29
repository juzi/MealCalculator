package at.zimmercarral.mealcalc

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import at.zimmercarral.mealcalc.edit.EditItemDialog
import at.zimmercarral.mealcalc.edit.EditItemDialogViewModel
import at.zimmercarral.mealcalc.edit.Focus
import at.zimmercarral.mealcalc.meal.MealLayout
import at.zimmercarral.mealcalc.meal.MealViewModel
import at.zimmercarral.mealcalc.search.SearchBar
import at.zimmercarral.mealcalc.search.SearchViewModel

@Composable
fun MainLayout(
    innerPadding: PaddingValues,
    editItemDialogViewModel: EditItemDialogViewModel,
    mealViewModel: MealViewModel,
    searchViewModel: SearchViewModel
) {
    Column(verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .background(color = colorScheme.surfaceContainer)
    ) {
        SearchBar(searchViewModel,
            onEditItem = {
                editItemDialogViewModel.setItem(it)
                editItemDialogViewModel.setInUpdateMode(false)
                editItemDialogViewModel.setInitialFocus(Focus.AMOUNT)
                editItemDialogViewModel.setVisible(true)
            },
            onAddItem = {
                mealViewModel.addOrUpdateItem(it)
            }
        )
        EditItemDialog(editItemDialogViewModel,
            onAddOrUpdateItem = mealViewModel::addOrUpdateItem,
            onDismiss = { editItemDialogViewModel.setVisible(false) })
        MealLayout(mealViewModel,
            onEditItem = { item ->
                editItemDialogViewModel.setItem(item)
                editItemDialogViewModel.setInUpdateMode(true)
                editItemDialogViewModel.setInitialFocus(Focus.AMOUNT)
                editItemDialogViewModel.setVisible(true)
            },
            onAddNewItem = {
                editItemDialogViewModel.clear()
                editItemDialogViewModel.setInUpdateMode(false)
                editItemDialogViewModel.setInitialFocus(Focus.NAME)
                editItemDialogViewModel.setVisible(true)
            })
    }
}
