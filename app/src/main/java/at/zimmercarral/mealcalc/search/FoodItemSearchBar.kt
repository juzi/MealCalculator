package at.zimmercarral.mealcalc.search

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import at.zimmercarral.mealcalc.R
import at.zimmercarral.mealcalc.data.db.model.FoodItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodItemSearchBar(
    searchViewModel: SearchViewModel,
    onEditItem: (FoodItem) -> Unit,
    onAddItem: (FoodItem) -> Unit
) {
    val filteredItems =
        remember(searchViewModel.items.toList(), searchViewModel.searchQuery.value) {
            searchViewModel.items.filter {
                it.name.contains(searchViewModel.searchQuery.value, ignoreCase = true)
            }
        }

    DockedSearchBar(
        modifier = Modifier
            .fillMaxWidth(),
        query = searchViewModel.searchQuery.value,
        onQueryChange = { searchViewModel.setSearchQuery(it) },
        onSearch = { searchViewModel.setVisibility(true) },
        active = searchViewModel.visible.value,
        onActiveChange = { searchViewModel.setVisibility(it) },
        leadingIcon = {
            Icon(imageVector = Icons.Rounded.Menu,
                contentDescription = "Menu")
        },
        trailingIcon = {
            if (searchViewModel.visible.value) {
                Icon(
                    modifier = Modifier.clickable {
                        if (searchViewModel.searchQuery.value == "") {
                            searchViewModel.setVisibility(false)
                        } else {
                            searchViewModel.setSearchQuery("")
                        }
                    },
                    imageVector = Icons.Rounded.Clear,
                    contentDescription = "Clear",
                )
            } else {
                Icon(imageVector = Icons.Rounded.Search,
                    contentDescription = "Search")
            }
        },
        placeholder = { Text(stringResource(R.string.search_in_database)) },
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            contentPadding = PaddingValues(5.dp),
        ) {
            itemsIndexed(filteredItems) { index, item ->
                val backgroundColor = if (index % 2 == 0) {
                    colorScheme.surfaceContainerLow
                } else {
                    colorScheme.surfaceContainerHighest
                }
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .background(color = backgroundColor)
                    .border(width = 1.dp,
                        color = colorScheme.outlineVariant.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(10.dp)
                    ),
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
                                    searchViewModel.setVisibility(false)
                                    onEditItem(item)
                                }
                                .padding(top = 12.dp, start = 5.dp, end = 5.dp))
                        Text(
                            text = item.amount.toString() + "gr",
                            maxLines = 1,
                            modifier = Modifier
                                .clickable {
                                    searchViewModel.setSearchQuery("")
                                    searchViewModel.setVisibility(false)
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
                                searchViewModel.setVisibility(false)
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
                                searchViewModel.setVisibility(false)
                                onAddItem(item)
                            },
                        imageVector = Icons.Rounded.Add,
                        contentDescription = stringResource(R.string.add_without_editing),
                        tint = colorScheme.primary,
                    )
                }
            }
        }
    }
}