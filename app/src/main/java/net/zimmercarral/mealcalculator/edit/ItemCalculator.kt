package net.zimmercarral.mealcalculator.edit

class ItemCalculator() {
    fun calculateValues(
        fat: Float,
        carbs: Float,
        protein: Float,
        amount: Int
    ): CalculatedValues {
        var finalBEs = 0.0f
        var finalFPEs = 0.0f
        var finalBEGrams: Float
        var finalFPEGrams: Float
        var gramsPerBE = 0.0f
        var fpesPerBE = 0.0f

        if (carbs != 0.0f) {
            gramsPerBE = 1200.0f / carbs
        }

        if (gramsPerBE != 0.0f && (fat != 0.0f || protein != 0.0f)) {
            val beFett = (gramsPerBE / 100.0f) * fat
            val fettFpes = beFett / 11.0f
            val beEiweiss = (gramsPerBE / 100.0f) * protein
            val eiweissFpes = beEiweiss / 25.0f
            fpesPerBE = fettFpes + eiweissFpes
        }

        if (gramsPerBE != 0.0f) {
            finalBEs = amount / gramsPerBE
            if (fpesPerBE != 0.0f) {
                finalFPEs = finalBEs * fpesPerBE
            }
        } else if (amount != 0) {
            finalFPEs = amount / 11.0f * (fat / 100.0f)
            finalFPEs += amount / 25.0f * (protein / 100.0f)
        }

        finalBEGrams = finalBEs * 12.0f
        finalFPEGrams = finalFPEs * 12.0f

        return CalculatedValues(finalBEs, finalFPEs, finalBEGrams, finalFPEGrams)
    }

}