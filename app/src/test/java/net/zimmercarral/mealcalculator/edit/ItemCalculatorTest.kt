package net.zimmercarral.mealcalculator.edit

import org.junit.Assert.assertEquals
import org.junit.Test

class ItemCalculatorTest {

    private val calc = ItemCalculator()

    @Test
    fun `all zero inputs return zeroed values`() {
        val r = calc.calculateValues(fat = 0f, carbs = 0f, protein = 0f, amount = 0)
        assertEquals(0f, r.calculatedBEs, EPS)
        assertEquals(0f, r.calculatedFPEs, EPS)
        assertEquals(0f, r.calculatedBEGrams, EPS)
        assertEquals(0f, r.calculatedFPEGrams, EPS)
    }

    @Test
    fun `pure carbs computes BE only, FPE stays zero`() {
        // gramsPerBE = 1200/50 = 24, BEs = 100/24, BEGrams = BEs*12 = 50.
        val r = calc.calculateValues(fat = 0f, carbs = 50f, protein = 0f, amount = 100)
        assertEquals(100f / 24f, r.calculatedBEs, EPS)
        assertEquals(0f, r.calculatedFPEs, EPS)
        assertEquals(50f, r.calculatedBEGrams, EPS)
        assertEquals(0f, r.calculatedFPEGrams, EPS)
    }

    @Test
    fun `mixed macros produce both BE and FPE`() {
        // gramsPerBE = 24
        // beFett = 24/100*10 = 2.4 ; fettFpes = 2.4/11
        // beEiweiss = 24/100*20 = 4.8 ; eiweissFpes = 4.8/25 = 0.192
        // fpesPerBE = 2.4/11 + 0.192
        // BEs = 100/24 ; FPEs = BEs * fpesPerBE
        val r = calc.calculateValues(fat = 10f, carbs = 50f, protein = 20f, amount = 100)
        val expectedFpesPerBE = 2.4f / 11f + 0.192f
        val expectedBEs = 100f / 24f
        val expectedFPEs = expectedBEs * expectedFpesPerBE
        assertEquals(expectedBEs, r.calculatedBEs, EPS)
        assertEquals(expectedFPEs, r.calculatedFPEs, EPS)
        assertEquals(expectedBEs * 12f, r.calculatedBEGrams, EPS)
        assertEquals(expectedFPEs * 12f, r.calculatedFPEGrams, EPS)
    }

    @Test
    fun `zero carbs falls back to amount-based FPE`() {
        // finalFPEs = 100/11*0.1 + 100/25*0.2
        val r = calc.calculateValues(fat = 10f, carbs = 0f, protein = 20f, amount = 100)
        val expectedFPEs = 100f / 11f * 0.10f + 100f / 25f * 0.20f
        assertEquals(0f, r.calculatedBEs, EPS)
        assertEquals(expectedFPEs, r.calculatedFPEs, EPS)
        assertEquals(0f, r.calculatedBEGrams, EPS)
        assertEquals(expectedFPEs * 12f, r.calculatedFPEGrams, EPS)
    }

    @Test
    fun `zero carbs and zero amount keep everything zero`() {
        val r = calc.calculateValues(fat = 10f, carbs = 0f, protein = 20f, amount = 0)
        assertEquals(0f, r.calculatedBEs, EPS)
        assertEquals(0f, r.calculatedFPEs, EPS)
        assertEquals(0f, r.calculatedBEGrams, EPS)
        assertEquals(0f, r.calculatedFPEGrams, EPS)
    }

    @Test
    fun `pure fat without carbs uses amount-based FPE only`() {
        val r = calc.calculateValues(fat = 10f, carbs = 0f, protein = 0f, amount = 100)
        val expected = 100f / 11f * 0.10f
        assertEquals(expected, r.calculatedFPEs, EPS)
        assertEquals(expected * 12f, r.calculatedFPEGrams, EPS)
    }

    @Test
    fun `pure protein without carbs uses amount-based FPE only`() {
        val r = calc.calculateValues(fat = 0f, carbs = 0f, protein = 25f, amount = 100)
        val expected = 100f / 25f * 0.25f
        assertEquals(expected, r.calculatedFPEs, EPS)
        assertEquals(expected * 12f, r.calculatedFPEGrams, EPS)
    }

    @Test
    fun `BE grams equal twelve times BEs`() {
        val r = calc.calculateValues(fat = 5f, carbs = 60f, protein = 7f, amount = 250)
        assertEquals(r.calculatedBEs * 12f, r.calculatedBEGrams, EPS)
        assertEquals(r.calculatedFPEs * 12f, r.calculatedFPEGrams, EPS)
    }

    companion object {
        private const val EPS = 1e-4f
    }
}
