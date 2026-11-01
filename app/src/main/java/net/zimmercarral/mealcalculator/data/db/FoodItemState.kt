package net.zimmercarral.mealcalculator.data.db

import net.zimmercarral.mealcalculator.data.db.model.FoodItem
import java.util.Date

data class FoodItemsState(
    val items: List<FoodItem> = emptyList(),
    val uuid: String = "",
    val name: String = "",
    val fat: Float = 0f,
    val carbs: Float = 0f,
    val protein: Float = 0f,
    val amount: Int = 0,
    val lastModifiedAt: Date? = null,
    val isDeleted: Boolean = false
)