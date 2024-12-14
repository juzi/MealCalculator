package at.zimmercarral.mealcalc

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Save
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import at.zimmercarral.mealcalc.common.ActionButton
import at.zimmercarral.mealcalc.item.EditItemDialogViewModel
import at.zimmercarral.mealcalc.item.Focus
import at.zimmercarral.mealcalc.meal.MealViewModel
import at.zimmercarral.mealcalc.search.SearchableItemList

@Composable
fun TopActionIcons(
    editItemDialogViewModel: EditItemDialogViewModel,
    mealViewModel: MealViewModel
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        shape = RoundedCornerShape(30.dp),
        elevation = CardDefaults.cardElevation(22.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(colorScheme.surfaceVariant),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            ActionButton(stringResource(id = R.string.clear_values),
                Icons.Rounded.Clear,
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 0.dp)
                    .size(70.dp),
                onClick = {
                    editItemDialogViewModel.clear()
                    editItemDialogViewModel.setInUpdateMode(false)
                    editItemDialogViewModel.setInitialFocus(Focus.AMOUNT)
                    editItemDialogViewModel.setVisible(true)
                })
            ActionButton(stringResource(id = R.string.save_item),
                Icons.Rounded.Save,
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 0.dp)
                    .size(70.dp),
                onClick = { /*TODO*/ })

//            ActionButton(stringResource(id = R.string.search_item),
//                Icons.Rounded.Search, color = Color(0, 0, 120, 0),
//                modifier = Modifier
//                    .padding(horizontal = 10.dp, vertical = 0.dp)
//                    .size(70.dp),
//                onClick = { /*TODO*/ })
            SearchableItemList(
                availableitems = mealViewModel.mealItems.toList(),
                onItemSelected = {
                    editItemDialogViewModel.setItem(it)
                    //editItemDialogViewModel.requestAmountFocus()
                }
            )
        }
    }
}

