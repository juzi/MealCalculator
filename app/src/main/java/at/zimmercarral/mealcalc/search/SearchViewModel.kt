package at.zimmercarral.mealcalc.search

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.focus.FocusRequester
import androidx.lifecycle.ViewModel
import at.zimmercarral.mealcalc.data.db.model.FoodItem

class SearchViewModel(initialItems: List<FoodItem>) : ViewModel() {

    var expanded = mutableStateOf(false)
    val searchFieldFocusRequester = mutableStateOf(FocusRequester())
    var searchQuery = mutableStateOf("")
    var items = mutableStateListOf<FoodItem>().apply {
        addAll(initialItems)
    }


    fun setExpanded(visible: Boolean) {
        this.expanded.value = visible
    }

    fun setSearchQuery(query: String) {
        searchQuery.value = query
    }

    fun setItems(newItems: List<FoodItem>) {
        items.clear()
        items.addAll(newItems)
    }

    fun focusOnQueryField() {
        searchFieldFocusRequester.value.requestFocus()
    }
}
