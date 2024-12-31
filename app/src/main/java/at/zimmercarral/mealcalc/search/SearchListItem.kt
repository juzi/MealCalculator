package at.zimmercarral.mealcalc.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import at.zimmercarral.mealcalc.R
import at.zimmercarral.mealcalc.data.db.model.FoodItem

@Composable
fun SearchListItem(
    item: FoodItem,
    searchViewModel: SearchViewModel,
    backgroundColor: Color,
    onEditItem: (FoodItem) -> Unit,
    onAddItem: (FoodItem) -> Unit
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .background(color = backgroundColor),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.fillMaxWidth(0.8f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            var itemName = item.name.substring(0, Math.min(item.name.length, 25)).trim()
            if (item.name.length > 25) {
                itemName += "..."
            }
            Text(
                text = itemName,
                maxLines = 1,
                modifier = Modifier
                    .clickable {
                        searchViewModel.setSearchQuery("")
                        searchViewModel.setExpanded(false)
                        onEditItem(item)
                    }
                    .padding(top = 12.dp, start = 5.dp, end = 5.dp))
            Text(
                text = item.amount.toString() + "gr",
                maxLines = 1,
                modifier = Modifier
                    .clickable {
                        searchViewModel.setSearchQuery("")
                        searchViewModel.setExpanded(false)
                        onEditItem(item)
                    }
                    .padding(top = 12.dp)
            )
        }
        Icon(
            modifier = Modifier
                .size(35.dp)
                .padding(top = 12.dp, end = 5.dp)
                .clickable {
                    searchViewModel.setSearchQuery("")
                    searchViewModel.setExpanded(false)
                    onEditItem(item)
                },
            imageVector = Icons.Rounded.Edit,
            contentDescription = stringResource(R.string.edit_item_before_adding),
            tint = colorScheme.primary,
        )
        Icon(
            modifier = Modifier
                .size(48.dp)
                .padding(top = 5.dp, end = 5.dp)
                .clickable {
                    searchViewModel.setSearchQuery("")
                    searchViewModel.setExpanded(false)
                    onAddItem(item)
                },
            imageVector = Icons.Rounded.Add,
            contentDescription = stringResource(R.string.add_without_editing),
            tint = colorScheme.primary,
        )
    }
}