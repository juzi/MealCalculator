package net.zimmercarral.mealcalculator.edit

/** Grams of each macronutrient in an eaten amount of food. */
data class CalculatedValues(
    val carbs: Float = 0f,
    val fat: Float = 0f,
    val protein: Float = 0f,
) {
    operator fun plus(other: CalculatedValues) = CalculatedValues(
        carbs = carbs + other.carbs,
        fat = fat + other.fat,
        protein = protein + other.protein,
    )
}
