package at.zimmercarral.mealcalc

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import at.zimmercarral.mealcalc.item.EditItemDialog
import at.zimmercarral.mealcalc.item.EditItemDialogViewModel
import at.zimmercarral.mealcalc.meal.MealInfo
import at.zimmercarral.mealcalc.meal.MealViewModel

@Composable
fun MainLayout(editItemDialogViewModel: EditItemDialogViewModel, mealViewModel: MealViewModel) {
    Column(verticalArrangement = Arrangement.Top,
        modifier = Modifier.fillMaxSize()) {
        TopActionIcons(editItemDialogViewModel, mealViewModel)
        EditItemDialog(editItemDialogViewModel,
            onAddOrUpdateItem = mealViewModel::addOrUpdateItem,
            onDismiss = { editItemDialogViewModel.setVisible(false) })
        MealInfo(editItemDialogViewModel, mealViewModel)
    }
}
