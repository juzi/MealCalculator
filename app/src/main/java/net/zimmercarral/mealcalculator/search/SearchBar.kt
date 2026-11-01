package net.zimmercarral.mealcalculator.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.zimmercarral.mealcalculator.R
import net.zimmercarral.mealcalculator.data.db.model.FoodItem
import net.zimmercarral.mealcalculator.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    searchViewModel: SearchViewModel,
    onEditItem: (FoodItem) -> Unit,
    onAddItem: (FoodItem) -> Unit
) {
    SearchBar(
        modifier = modifier,
        query = searchViewModel.searchQuery.value,
        onQueryChange = { searchViewModel.setSearchQuery(it) },
        expanded = searchViewModel.expanded.value,
        onExpandedChange = { searchViewModel.setExpanded(it) },
        items = searchViewModel.items,
        onEditItem = onEditItem,
        onAddItem = onAddItem,
        onAction = {
            searchViewModel.setSearchQuery("")
            searchViewModel.setExpanded(false)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchBar(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    items: List<FoodItem>,
    onEditItem: (FoodItem) -> Unit,
    onAddItem: (FoodItem) -> Unit,
    onAction: () -> Unit
) {
    val filteredItems =
        remember(items.toList(), query) {
            items.filter {
                it.name.contains(query, ignoreCase = true)
            }
        }
    val itemListState = rememberLazyListState()
    val colors = SearchBarDefaults.colors(
        containerColor = colorScheme.surfaceContainerLow
    )

    androidx.compose.material3.SearchBar(
        inputField = {
            SearchBarDefaults.InputField(
                modifier = Modifier.fillMaxWidth(),
                query = query,
                onQueryChange = onQueryChange,
                onSearch = {
                    if (filteredItems.isNotEmpty()) {
                        onEditItem(filteredItems.first())
                    }
                },
                expanded = expanded,
                onExpandedChange = onExpandedChange,
                enabled = true,
                placeholder = { Text(stringResource(R.string.search_in_database)) },
                leadingIcon = {
                    if (expanded) {
                        IconButton(onClick = { onExpandedChange(false) }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                                contentDescription = stringResource(R.string.back),
                            )
                        }
                    } else {
                        Icon(
                            imageVector = Icons.Rounded.Search,
                            contentDescription = stringResource(R.string.search_item)
                        )
                    }
                },
                trailingIcon = if (expanded) {
                    {
                        IconButton(onClick = { onQueryChange("") }) {
                            Icon(
                                imageVector = Icons.Rounded.Clear,
                                contentDescription = stringResource(R.string.clear),
                            )
                        }
                    }
                } else null,
                colors = SearchBarDefaults.inputFieldColors(),
                interactionSource = null,
            )
        },
        expanded = expanded,
        onExpandedChange = onExpandedChange,
        modifier = if (expanded) Modifier.fillMaxWidth() else modifier,
        shape = SearchBarDefaults.dockedShape,
        colors = colors,
        tonalElevation = SearchBarDefaults.TonalElevation,
        shadowElevation = SearchBarDefaults.ShadowElevation,
        windowInsets = WindowInsets(0.dp)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxHeight(),
            state = itemListState,
            verticalArrangement = Arrangement.spacedBy(4.dp),
            contentPadding = PaddingValues(top = 5.dp, bottom = 5.dp, start = 5.dp, end = 5.dp),
        ) {
            itemsIndexed(filteredItems) { index, item ->
                val backgroundColor = if (index % 2 == 1) {
                    colorScheme.surfaceContainerHigh
                } else {
                    colorScheme.surfaceContainerLow
                }
                SearchListItem(
                    item = item,
                    backgroundColor = backgroundColor,
                    onEditItem = onEditItem,
                    onAddItem = onAddItem,
                    onAction = onAction
                )
            }
        }
    }
}

@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SearchBarPreview() {
    val items = listOf(
        FoodItem("1", "Apple", 0.3f, 14f, 0.3f, 100, null, false),
        FoodItem("2", "Banana", 0.3f, 23f, 1.1f, 100, null, false),
        FoodItem("3", "Cherry", 0.3f, 12f, 1f, 100, null, false)
    )
    AppTheme {
        SearchBar(
            query = "a",
            onQueryChange = {},
            expanded = true,
            onExpandedChange = {},
            items = items,
            onEditItem = {},
            onAddItem = {},
            onAction = {}
        )
    }
}

