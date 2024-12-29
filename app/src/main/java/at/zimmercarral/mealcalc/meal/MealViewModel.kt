package at.zimmercarral.mealcalc.meal

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import at.zimmercarral.mealcalc.data.db.model.FoodItem
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken

class MealViewModel(items: List<FoodItem>) : ViewModel() {
    private val GSON = Gson()

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

    fun addItems(items: List<FoodItem>) {
        items.forEach(this::addOrUpdateItem)
    }

    fun removeItem(item: FoodItem) {
        mealItems.remove(item)
    }

    fun removeAllItems() {
        mealItems.removeAll(mealItems)
    }

    fun getItemsAsJson(): String {
        return GSON.toJson(mealItems.toList());
    }

    fun setItemsFromJson(json: String) {
        try {
            val typeToken = object : TypeToken<List<FoodItem>>() {}.type
            val items = GSON.fromJson<List<FoodItem>>(json, typeToken);
            addItems(items)
        } catch (e: JsonSyntaxException) {
            e.printStackTrace()
        }
    }

}