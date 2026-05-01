package at.zimmercarral.mealcalc.meal

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import at.zimmercarral.mealcalc.data.db.model.FoodItem
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import java.time.LocalDateTime

class MealViewModel(items: List<FoodItem>) : ViewModel() {
    private val GSON = Gson()
    private var insulinReminderManager: InsulinReminderManager? = null

    var mealItems = mutableStateListOf<FoodItem>().apply {
        addAll(items)
    }

    fun setInsulinReminderManager(insulinReminderManager: InsulinReminderManager) {
        this.insulinReminderManager = insulinReminderManager
    }

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
        insulinReminderManager?.startOrResetTimer()
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
        if (mealItems.isEmpty()) {
            insulinReminderManager?.cancelTimer()
        }
    }

    fun removeAllItems() {
        mealItems.removeAll(mealItems)
        insulinReminderManager?.cancelTimer()
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