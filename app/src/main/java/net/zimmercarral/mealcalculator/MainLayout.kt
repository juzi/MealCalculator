package net.zimmercarral.mealcalculator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.HelpOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import net.zimmercarral.mealcalculator.data.auth.SyncStatusButton
import net.zimmercarral.mealcalculator.data.cloud.SyncState
import net.zimmercarral.mealcalculator.edit.EditItemDialog
import net.zimmercarral.mealcalculator.edit.EditItemDialogViewModel
import net.zimmercarral.mealcalculator.edit.Focus
import net.zimmercarral.mealcalculator.meal.MealLayout
import net.zimmercarral.mealcalculator.meal.MealViewModel
import net.zimmercarral.mealcalculator.search.SearchBar
import net.zimmercarral.mealcalculator.search.SearchViewModel

@Composable
fun MainLayout(
    innerPadding: PaddingValues,
    editItemDialogViewModel: EditItemDialogViewModel,
    mealViewModel: MealViewModel,
    searchViewModel: SearchViewModel,
    syncState: SyncState,
    onSignIn: () -> Unit,
    onSignOut: () -> Unit,
    onDeleteAccount: () -> Unit,
    onShowHelp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(verticalArrangement = Arrangement.Top,
        modifier = modifier
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
                    syncState = syncState,
                    onSignIn = onSignIn,
                    onSignOut = onSignOut,
                    onDeleteAccount = onDeleteAccount,
                )
                IconButton(onClick = onShowHelp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.HelpOutline,
                        contentDescription = stringResource(R.string.help),
                    )
                }
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
