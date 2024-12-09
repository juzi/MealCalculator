package at.zimmercarral.mealcalc

import androidx.lifecycle.ViewModel
import at.zimmercarral.mealcalc.data.db.model.FoodItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel(availableItems: List<FoodItem>) : ViewModel() {
    private val _currentItemState = MutableStateFlow(availableItems.get(0))
    val currentItem = _currentItemState.asStateFlow()

    private val _mealItemsState = MutableStateFlow(listOf<FoodItem>())
    val mealItems = _mealItemsState.asStateFlow()

    override fun onCleared() {
        super.onCleared()
        println("Viewmodel cleared")
    }
}

