package at.zimmercarral.mealcalc.search

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.focus.FocusRequester
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.zimmercarral.mealcalc.data.db.model.FoodItem
import at.zimmercarral.mealcalc.data.repo.FoodItemRepository
import kotlinx.coroutines.launch

class SearchViewModel(repository: FoodItemRepository) : ViewModel() {

    var expanded = mutableStateOf(false)
    val searchFieldFocusRequester = mutableStateOf(FocusRequester())
    var searchQuery = mutableStateOf("")
    var items = mutableStateListOf<FoodItem>()

    init {
        viewModelScope.launch {
            repository.observeVisibleSorted().collect { newItems ->
                items.clear()
                items.addAll(newItems)
            }
        }
    }

    fun setExpanded(visible: Boolean) {
        this.expanded.value = visible
    }

    fun setSearchQuery(query: String) {
        searchQuery.value = query
    }

    fun focusOnQueryField() {
        searchFieldFocusRequester.value.requestFocus()
    }
}
