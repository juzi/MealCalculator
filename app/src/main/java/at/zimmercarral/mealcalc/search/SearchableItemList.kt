package at.zimmercarral.mealcalc.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import at.zimmercarral.mealcalc.R
import at.zimmercarral.mealcalc.common.ActionButton
import at.zimmercarral.mealcalc.data.db.model.FoodItem

@Composable
fun SearchableItemList(
    // Now items are a State<List<String>> to allow dynamic updates
    availableitems: List<FoodItem>,
    onItemSelected: (FoodItem) -> Unit
) {
    // State to manage search visibility and input
    var isSearchVisible by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    var items by remember { mutableStateOf(availableitems) }
    val searchFieldFocusRequester = remember { FocusRequester() }


    // Filtered items based on search query - now uses the state's value
    val filteredItems = remember(items, searchQuery) {
        items.filter {
            it.name.contains(searchQuery, ignoreCase = true)
        }
    }

    // Search button to trigger overlay
    ActionButton(stringResource(id = R.string.search_item),
        Icons.Rounded.Search,
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 0.dp)
            .size(70.dp),
        onClick = { isSearchVisible = true })

    // Search overlay dialog
    if (isSearchVisible) {
        Dialog(
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
                usePlatformDefaultWidth = false
            ),
            onDismissRequest = { isSearchVisible = false })
        {
            Surface(
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .fillMaxWidth(0.90f)
                    .height(500.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                ) {
                    LaunchedEffect(Unit) {
                        searchFieldFocusRequester.requestFocus()
                    }
                    // Search input field
                    TextField(
                        shape = RoundedCornerShape(20.dp),
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        maxLines = 1,
                        placeholder = { Text("Search items") },
                        leadingIcon = {
                            Icon(Icons.Default.Search, contentDescription = "Search")
                        },
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                IconButton(onClick = { searchQuery = "" }) {
                                    Icon(Icons.Rounded.Clear, contentDescription = "Clear")
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .focusRequester(searchFieldFocusRequester),
                        textStyle = TextStyle(
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.Normal,
                            fontSize = TextUnit(19f, TextUnitType.Sp),
                            textAlign = TextAlign.Left,
                        ),
                        colors = TextFieldDefaults.colors(
                            // Transparent background and indicator colors for all states
//                            focusedContainerColor = Color.Transparent,
//                            unfocusedContainerColor = Color.Transparent,
//                            disabledContainerColor = Color.Transparent,

                            // Optional: Customize cursor and other color states
                            cursorColor = MaterialTheme.colorScheme.primary,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                    )

                    // Filtered items list
                    LazyColumn {
                        items(filteredItems) { item ->
                            Text(
                                text = item.name,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        onItemSelected(item)
                                        isSearchVisible = false
                                    }
                                    .padding(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}