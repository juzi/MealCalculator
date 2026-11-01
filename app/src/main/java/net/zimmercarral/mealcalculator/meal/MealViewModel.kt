package net.zimmercarral.mealcalculator.meal

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import net.zimmercarral.mealcalculator.data.db.model.FoodItem
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import java.time.LocalDateTime

class MealViewModel : ViewModel() {
    private val GSON = Gson()

    var mealItems = mutableStateListOf<FoodItem>()

    fun addItems(items: List<FoodItem>) {
        items.forEach(this::addOrUpdateItem)
    }

    fun addOrUpdateItem(item: FoodItem) {
        val index = indexOfItem(item)
        if (index >= 0) {
            updateItemAt(index, item)
        } else {
            mealItems.add(item)
        }
    }

    private fun updateItemAt(index: Int, newItem: FoodItem) {
        if (index >= 0) {
            val oldItem = mealItems[index]
            mealItems[index] = oldItem.copy(
                uuid = newItem.uuid,
                name = newItem.name,
                fatPer100g = newItem.fatPer100g,
                carbsPer100g = newItem.carbsPer100g,
                proteinPer100g = newItem.proteinPer100g,
                amount = newItem.amount,
                lastModifiedAt = LocalDateTime.now(),
                isDeleted = false
            )
        }
    }

    private fun indexOfItem(item: FoodItem): Int {
        return mealItems.indexOfFirst { it.uuid == item.uuid }
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
            val items = GSON.fromJson<List<FoodItem>>(json, typeToken)
            addItems(items)
        } catch (e: JsonSyntaxException) {
            Log.e(TAG, "ignoring corrupt saved meal items", e)
        }
    }

    companion object {
        private const val TAG = "MealViewModel"
    }
}