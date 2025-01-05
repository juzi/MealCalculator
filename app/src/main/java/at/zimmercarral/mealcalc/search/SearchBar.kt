package at.zimmercarral.mealcalc.search

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import at.zimmercarral.mealcalc.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onExpandedChange: (Boolean) -> Unit,
) {
    val colors = SearchBarDefaults.colors(
        containerColor = colorScheme.surfaceContainerHighest
    )

    DockedSearchBar(
        inputField = {
            SearchBarDefaults.InputField(
                modifier = modifier
                    .fillMaxWidth(),
                query = "",
                onQueryChange = { },
                onSearch = {},
                expanded = false,
                onExpandedChange = onExpandedChange,
                enabled = true,
                placeholder = { Text(stringResource(R.string.search_in_database)) },
                leadingIcon = {
                    Icon(imageVector = Icons.Rounded.Menu,
                        contentDescription = "Menu")
                },
                trailingIcon = {
                    Icon(imageVector = Icons.Rounded.Search,
                        contentDescription = "Search")
                },
                colors = SearchBarDefaults.inputFieldColors(),
                interactionSource = null,
            )
        },
        expanded = false, //searchViewModel.expanded.value,
        onExpandedChange = {
            //           searchViewModel.setExpanded(it)
        },
        modifier = Modifier
            .padding(start = 5.dp, end = 5.dp, top = 7.dp)
            .fillMaxWidth(),
        shape = SearchBarDefaults.dockedShape,
        colors = colors,
        tonalElevation = SearchBarDefaults.TonalElevation,
        shadowElevation = SearchBarDefaults.ShadowElevation
    ) {
//        LazyColumn(
//            modifier = Modifier.fillMaxHeight(),
//            state = itemListState,
//            verticalArrangement = Arrangement.spacedBy(4.dp),
//            contentPadding = PaddingValues(5.dp),
//        ) {
//            itemsIndexed(filteredItems) { index, item ->
//                val backgroundColor = if (index % 2 == 0) {
//                    colorScheme.surfaceContainerLow
//                } else {
//                    colorScheme.surfaceContainerHighest
//                }
//                SearchListItem(item,
//                    searchViewModel,
//                    backgroundColor,
//                    onEditItem,
//                    onAddItem)
//            }
//        }
    }
}

