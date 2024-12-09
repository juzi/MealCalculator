package at.zimmercarral.mealcalc.data.db

sealed interface FoodItemEvent {
    object SaveFoodItem : FoodItemEvent
    data class SetName(val name: String) : FoodItemEvent
    data class setFat(val fat: Float) : FoodItemEvent
    data class setCarbs(val carbs: Float) : FoodItemEvent
    data class setProtein(val protein: Float) : FoodItemEvent
    data class setAmount(val amount: Int) : FoodItemEvent

}