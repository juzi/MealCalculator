package at.zimmercarral.mealcalc.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
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
import at.zimmercarral.mealcalc.data.db.model.FoodItem

@Composable
fun SearchItemDialog(
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

    // Search overlay dialog
    AnimatedVisibility(visible = searchViewModel.visible.value) {
        Dialog(
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
                usePlatformDefaultWidth = false
            ),
            onDismissRequest = { searchViewModel.setVisibility(false) })
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
                        searchViewModel.focusOnQueryField()
                    }
                    // Search input field
                    TextField(
                        shape = RoundedCornerShape(20.dp),
                        value = searchViewModel.searchQuery.value,
                        onValueChange = { searchViewModel.setSearchQuery(it) },
                        maxLines = 1,
                        placeholder = { Text("Search items") },
                        leadingIcon = {
                            Icon(Icons.Default.Search, contentDescription = "Search")
                        },
                        trailingIcon = {
                            if (searchViewModel.searchQuery.value.isNotEmpty()) {
                                IconButton(onClick = { searchViewModel.setSearchQuery("") }) {
                                    Icon(Icons.Rounded.Clear, contentDescription = "Clear")
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .focusRequester(searchViewModel.searchFieldFocusRequester.value),
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
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        contentPadding = PaddingValues(5.dp),
                    ) {
                        items(filteredItems) { item ->
                            Row(modifier = Modifier
                                .fillMaxWidth()
                                .border(width = 1.dp,
                                    color = colorScheme.outlineVariant.copy(alpha = 0.2f),
                                    shape = RoundedCornerShape(10.dp)
                                ),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = item.name,
                                    maxLines = 1,
                                    modifier = Modifier
                                        .fillMaxWidth(0.75f)
                                        .clickable {
                                            searchViewModel.setVisibility(false)
                                            onEditItem(item)
                                        }
                                        .padding(16.dp)
                                )
                                Icon(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .padding(top = 15.dp, end = 5.dp)
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
                                        .padding(top = 12.dp)
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
        }
        LaunchedEffect(Unit) {
            searchViewModel.focusOnQueryField()
        }
    }
}