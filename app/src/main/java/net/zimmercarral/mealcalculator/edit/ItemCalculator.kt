package net.zimmercarral.mealcalculator.edit

class ItemCalculator {
    /**
     * Scales nutrient values given per 100 g to the eaten [amount] in grams.
     */
    fun calculateValues(
        fat: Float,
        carbs: Float,
        protein: Float,
        amount: Int,
    ): CalculatedValues {
        val factor = amount / 100f
        return CalculatedValues(
            carbs = carbs * factor,
            fat = fat * factor,
            protein = protein * factor,
        )
    }
}
