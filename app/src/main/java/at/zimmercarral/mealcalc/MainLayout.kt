package at.zimmercarral.mealcalc

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import at.zimmercarral.mealcalc.item.EditItemDialog
import at.zimmercarral.mealcalc.item.EditItemDialogViewModel
import at.zimmercarral.mealcalc.item.Focus
import at.zimmercarral.mealcalc.meal.MealInfo
import at.zimmercarral.mealcalc.meal.MealViewModel
import at.zimmercarral.mealcalc.search.FoodItemSearchBar
import at.zimmercarral.mealcalc.search.SearchViewModel

@Composable
fun MainLayout(
    editItemDialogViewModel: EditItemDialogViewModel,
    mealViewModel: MealViewModel,
    searchViewModel: SearchViewModel
) {
    Column(verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorScheme.surfaceContainer)
    ) {
        FoodItemSearchBar(searchViewModel,
            onEditItem = {
                editItemDialogViewModel.setItem(it)
                editItemDialogViewModel.setInUpdateMode(false)
                editItemDialogViewModel.setInitialFocus(Focus.AMOUNT)
                editItemDialogViewModel.setVisible(true)
            },
            onAddItem = {
                mealViewModel.addItem(it)
            }
        )
        EditItemDialog(editItemDialogViewModel,
            onAddOrUpdateItem = mealViewModel::addOrUpdateItem,
            onDismiss = { editItemDialogViewModel.setVisible(false) })
        MealInfo(mealViewModel,
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
