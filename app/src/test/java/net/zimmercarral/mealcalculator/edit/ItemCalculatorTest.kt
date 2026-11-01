package net.zimmercarral.mealcalculator.edit

import org.junit.Assert.assertEquals
import org.junit.Test

class ItemCalculatorTest {

    private val calc = ItemCalculator()

    @Test
    fun `all zero inputs return zeroed values`() {
        val r = calc.calculateValues(fat = 0f, carbs = 0f, protein = 0f, amount = 0)
        assertEquals(CalculatedValues(), r)
    }

    @Test
    fun `100 grams return the per-100g values unchanged`() {
        val r = calc.calculateValues(fat = 10f, carbs = 50f, protein = 20f, amount = 100)
        assertEquals(50f, r.carbs, EPS)
        assertEquals(10f, r.fat, EPS)
        assertEquals(20f, r.protein, EPS)
    }

    @Test
    fun `values scale linearly with the amount eaten`() {
        val r = calc.calculateValues(fat = 5f, carbs = 60f, protein = 7f, amount = 250)
        assertEquals(150f, r.carbs, EPS)
        assertEquals(12.5f, r.fat, EPS)
        assertEquals(17.5f, r.protein, EPS)
    }

    @Test
    fun `zero amount yields zero regardless of nutrient values`() {
        val r = calc.calculateValues(fat = 10f, carbs = 50f, protein = 20f, amount = 0)
        assertEquals(CalculatedValues(), r)
    }

    @Test
    fun `nutrients are not mixed up`() {
        val r = calc.calculateValues(fat = 1f, carbs = 2f, protein = 3f, amount = 100)
        assertEquals(CalculatedValues(carbs = 2f, fat = 1f, protein = 3f), r)
    }

    @Test
    fun `fractional nutrient values are preserved`() {
        val r = calc.calculateValues(fat = 0.4f, carbs = 4.8f, protein = 3.4f, amount = 200)
        assertEquals(9.6f, r.carbs, EPS)
        assertEquals(0.8f, r.fat, EPS)
        assertEquals(6.8f, r.protein, EPS)
    }

    companion object {
        private const val EPS = 1e-4f
    }
}
