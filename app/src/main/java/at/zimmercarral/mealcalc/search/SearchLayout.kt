package at.zimmercarral.mealcalc.search

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import at.zimmercarral.mealcalc.MealScreen
import at.zimmercarral.mealcalc.R
import at.zimmercarral.mealcalc.data.db.model.FoodItem
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchLayout(
    modifier: Modifier = Modifier,
    navController: NavHostController,
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
    val itemListState = rememberLazyListState()
    val colors = SearchBarDefaults.colors(
        containerColor = colorScheme.surfaceContainerHighest
    )
    DockedSearchBar(
        inputField = {
            SearchBarDefaults.InputField(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(searchViewModel.searchFieldFocusRequester.value),
                query = searchViewModel.searchQuery.value,
                onQueryChange = { searchViewModel.setSearchQuery(it) },
                onSearch = { },
                expanded = searchViewModel.expanded.value,
                onExpandedChange = {
                    searchViewModel.setExpanded(it)
                },
                enabled = true,
                placeholder = { Text(stringResource(R.string.search_in_database)) },
                leadingIcon = {
                    Icon(imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = stringResource(R.string.back),
                        modifier = Modifier
                            .clickable { navController.navigate(MealScreen(null)) }
                    )
                },
                trailingIcon = {
                    if (searchViewModel.expanded.value) {
                        Icon(
                            modifier = Modifier.clickable { searchViewModel.setSearchQuery("") },
                            imageVector = Icons.Rounded.Clear,
                            contentDescription = "Clear",
                        )
                    } else {
                        Icon(imageVector = Icons.Rounded.Search,
                            contentDescription = "Search")
                    }
                },
                colors = SearchBarDefaults.inputFieldColors(),
                interactionSource = null,
            )
        },
        expanded = searchViewModel.expanded.value,
        onExpandedChange = {
            searchViewModel.setExpanded(it)
        },
        modifier = Modifier
            .fillMaxSize(),
        shape = SearchBarDefaults.fullScreenShape,
        colors = colors,
        tonalElevation = SearchBarDefaults.TonalElevation,
        shadowElevation = SearchBarDefaults.ShadowElevation
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxHeight(),
            state = itemListState,
            verticalArrangement = Arrangement.spacedBy(4.dp),
            contentPadding = PaddingValues(5.dp),
        ) {
            itemsIndexed(filteredItems) { index, item ->
                val backgroundColor = if (index % 2 == 0) {
                    colorScheme.surfaceContainerLow
                } else {
                    colorScheme.surfaceContainerHighest
                }
                SearchListItem(item,
                    searchViewModel,
                    backgroundColor,
                    onEditItem,
                    onAddItem)
            }
        }
    }
    LaunchedEffect(Unit) {
        awaitFrame()
        searchViewModel.focusOnQueryField()
    }
}

