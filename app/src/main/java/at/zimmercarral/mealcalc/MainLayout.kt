package at.zimmercarral.mealcalc

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import at.zimmercarral.mealcalc.data.auth.SyncStatusButton
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
    searchViewModel: SearchViewModel,
    isSignedIn: Boolean,
    onSignIn: () -> Unit,
    onSignOut: () -> Unit,
) {
    Column(verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .background(color = colorScheme.surfaceContainer)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SearchBar(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 15.dp),
                searchViewModel = searchViewModel,
                onEditItem = {
                    editItemDialogViewModel.setVisible(true)
                    editItemDialogViewModel.setItem(it)
                    editItemDialogViewModel.setInUpdateMode(false)
                    editItemDialogViewModel.setInitialFocus(Focus.AMOUNT)
                },
                onAddItem = {
                    mealViewModel.addOrUpdateItem(it)
                }
            )
            if (!searchViewModel.expanded.value) {
                SyncStatusButton(
                    isSignedIn = isSignedIn,
                    onSignIn = onSignIn,
                    onSignOut = onSignOut,
                )
            }
        }
        EditItemDialog(editItemDialogViewModel,
            onAddOrUpdateItem = {
                mealViewModel.addOrUpdateItem(it)
            },
            onDismiss = { editItemDialogViewModel.setVisible(false) })
        MealLayout(mealViewModel,
            onEditItem = { index ->
                val item = mealViewModel.mealItems[index]
                editItemDialogViewModel.clear()
                editItemDialogViewModel.setIndex(index)
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
