package at.zimmercarral.mealcalc.data.db

import java.util.Date

sealed interface FoodItemEvent {
    object SaveFoodItem : FoodItemEvent
    data class SetUuid(val uuid: String) : FoodItemEvent
    data class SetName(val name: String) : FoodItemEvent
    data class SetFat(val fat: Float) : FoodItemEvent
    data class SetCarbs(val carbs: Float) : FoodItemEvent
    data class SetProtein(val protein: Float) : FoodItemEvent
    data class SetAmount(val amount: Int) : FoodItemEvent
    data class SetDeleted(val isDeleted: Boolean) : FoodItemEvent
    data class SetLastModifiedAt(val lastModifiedAt: Date) : FoodItemEvent
}