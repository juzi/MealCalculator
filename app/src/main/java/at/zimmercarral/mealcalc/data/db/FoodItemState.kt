package at.zimmercarral.mealcalc.data.db

import at.zimmercarral.mealcalc.data.db.model.FoodItem

data class FoodItemsState(
    val items: List<FoodItem> = emptyList(),
    val uuid: String = "",
    val name: String = "",
    val fat: Float = 0f,
    val carbs: Float = 0f,
    val protein: Float = 0f,
    val amount: Int = 0
)