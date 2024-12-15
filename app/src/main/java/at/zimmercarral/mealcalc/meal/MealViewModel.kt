package at.zimmercarral.mealcalc.meal

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import at.zimmercarral.mealcalc.data.db.model.FoodItem

class MealViewModel(items: List<FoodItem>) : ViewModel() {

    var mealItems = mutableStateListOf<FoodItem>().apply {
        addAll(items)
    }

    fun addOrUpdateItem(item: FoodItem) {
        val index = indexOfItem(item)
        if (index >= 0) {
            updateItem(index, item)
        } else {
            mealItems.add(item)
        }
    }

    private fun indexOfItem(item: FoodItem): Int {
        return mealItems.indexOfFirst { it.uuid == item.uuid }
    }

    private fun updateItem(index: Int, item: FoodItem) {
        mealItems[index] = mealItems[index].copy(
            name = item.name,
            fatPer100g = item.fatPer100g,
            carbsPer100g = item.carbsPer100g,
            proteinPer100g = item.proteinPer100g,
            amount = item.amount
        )
    }

    fun addItem(foodItem: FoodItem) {
        mealItems.add(foodItem)
    }

    fun addItems(items: List<FoodItem>) {
        mealItems.addAll(items)
    }

    fun removeItem(item: FoodItem) {
        mealItems.remove(item)
    }

    fun removeAllItems() {
        mealItems.removeAll(mealItems)
    }

    fun calculateFPE(): String {
        return "0"
    }

    fun calculateCarbs(): String {
        return "0"
    }


}