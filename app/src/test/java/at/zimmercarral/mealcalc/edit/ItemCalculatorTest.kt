package at.zimmercarral.mealcalc.edit

import org.junit.Test
import org.junit.Assert.*

// Define locally as its own file is not provided
data class CalculatedValues(val finalBEs: Float, val finalFPEs: Float, val finalBEGrams: Float, val finalFPEGrams: Float)

class ItemCalculatorTest {

    private val calculator = ItemCalculator()
    private val DELTA = 0.001f

    @Test
    fun testCalculateValues_zeroCarbs() {
        val result = calculator.calculateValues(fat = 10f, carbs = 0f, protein = 5f, amount = 100)
        // Expected: finalBEs should be 0. finalFPEs should be calculated based on fat and protein.
        // fatFPEs = 100 / 11.0f * (10f / 100.0f) = 100/11 * 0.1 = 0.9090...
        // proteinFPEs = 100 / 25.0f * (5f / 100.0f) = 4 * 0.05 = 0.2
        // totalFPEs = 0.9090... + 0.2 = 1.1090...
        val expectedFPEs = (100f / 11f * (10f/100f)) + (100f / 25f * (5f/100f))
        assertEquals(0f, result.finalBEs, DELTA)
        assertEquals(expectedFPEs, result.finalFPEs, DELTA)
        assertEquals(0f, result.finalBEGrams, DELTA)
        assertEquals(expectedFPEs * 12f, result.finalFPEGrams, DELTA)
    }

    @Test
    fun testCalculateValues_nonZeroCarbs_zeroFatProtein() {
        val result = calculator.calculateValues(fat = 0f, carbs = 20f, protein = 0f, amount = 100)
        // Expected: fpesPerBE should be 0. finalFPEs should be 0. finalBEs should be calculated.
        // gramsPerBE = 1200f / 20f = 60f
        // finalBEs = 100f / 60f = 1.666...f
        val expectedBEs = 100f / (1200f / 20f)
        assertEquals(expectedBEs, result.finalBEs, DELTA)
        assertEquals(0f, result.finalFPEs, DELTA)
        assertEquals(expectedBEs * 12f, result.finalBEGrams, DELTA)
        assertEquals(0f, result.finalFPEGrams, DELTA)
    }

    @Test
    fun testCalculateValues_allZeros_amount100() {
        val result = calculator.calculateValues(fat = 0f, carbs = 0f, protein = 0f, amount = 100)
        assertEquals(0f, result.finalBEs, DELTA)
        assertEquals(0f, result.finalFPEs, DELTA)
        assertEquals(0f, result.finalBEGrams, DELTA)
        assertEquals(0f, result.finalFPEGrams, DELTA)
    }

    @Test
    fun testCalculateValues_allZeros_amount0() {
        val result = calculator.calculateValues(fat = 0f, carbs = 0f, protein = 0f, amount = 0)
        assertEquals(0f, result.finalBEs, DELTA)
        assertEquals(0f, result.finalFPEs, DELTA)
        assertEquals(0f, result.finalBEGrams, DELTA)
        assertEquals(0f, result.finalFPEGrams, DELTA)
    }

    @Test
    fun testCalculateValues_typicalPositiveValues() {
        val result = calculator.calculateValues(fat = 10f, carbs = 30f, protein = 20f, amount = 150)
        // Calculation based on prompt:
        // gramsPerBE = 1200f / 30f = 40f
        // beFett = (40f / 100f) * 10f = 4f
        // fettFpes = 4f / 11f = 0.363636f
        // beEiweiss = (40f / 100f) * 20f = 8f
        // eiweissFpes = 8f / 25f = 0.32f
        // fpesPerBE = 0.363636f + 0.32f = 0.683636f
        // finalBEs = 150f / 40f = 3.75f
        // finalFPEs = 3.75f * 0.683636f = 2.563635f
        // finalBEGrams = 3.75f * 12f = 45f
        // finalFPEGrams = 2.563635f * 12f = 30.76362f

        val expectedBEs = 3.75f
        val expectedFPEs = 2.563635f
        assertEquals(expectedBEs, result.finalBEs, DELTA)
        assertEquals(expectedFPEs, result.finalFPEs, DELTA)
        assertEquals(expectedBEs * 12f, result.finalBEGrams, DELTA)
        assertEquals(expectedFPEs * 12f, result.finalFPEGrams, DELTA)
    }

    @Test
    fun testCalculateValues_amountZero() {
        val result = calculator.calculateValues(fat = 10f, carbs = 20f, protein = 5f, amount = 0)
        assertEquals(0f, result.finalBEs, DELTA)
        assertEquals(0f, result.finalFPEs, DELTA)
        assertEquals(0f, result.finalBEGrams, DELTA)
        assertEquals(0f, result.finalFPEGrams, DELTA)
    }
}
