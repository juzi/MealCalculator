package at.zimmercarral.mealcalc.item

data class CalculatedValues(
    var calculatedBEs: Float,
    var calculatedFPEs: Float,
    var calculatedBEGrams: Float,
    var calculatedFPEGrams: Float
) {
    fun add(calculatedValues: CalculatedValues): CalculatedValues {
        calculatedBEs += calculatedValues.calculatedBEs
        calculatedFPEs += calculatedValues.calculatedFPEs
        calculatedBEGrams += calculatedValues.calculatedBEGrams
        calculatedFPEGrams += calculatedValues.calculatedFPEGrams
        return this
    }
}
