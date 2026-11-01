package net.zimmercarral.mealcalculator.edit

import org.junit.Assert.assertEquals
import org.junit.Test

class CalculatedValuesTest {

    @Test
    fun `default values are zero`() {
        assertEquals(CalculatedValues(0f, 0f, 0f), CalculatedValues())
    }

    @Test
    fun `plus adds each nutrient separately`() {
        val sum = CalculatedValues(carbs = 1f, fat = 2f, protein = 3f) +
            CalculatedValues(carbs = 0.5f, fat = 1.5f, protein = 2.5f)
        assertEquals(1.5f, sum.carbs, EPS)
        assertEquals(3.5f, sum.fat, EPS)
        assertEquals(5.5f, sum.protein, EPS)
    }

    @Test
    fun `plus leaves both operands unchanged`() {
        val a = CalculatedValues(1f, 1f, 1f)
        val b = CalculatedValues(2f, 2f, 2f)
        a + b
        assertEquals(CalculatedValues(1f, 1f, 1f), a)
        assertEquals(CalculatedValues(2f, 2f, 2f), b)
    }

    @Test
    fun `adding zero returns equal values`() {
        val a = CalculatedValues(4f, 5f, 6f)
        assertEquals(a, a + CalculatedValues())
    }

    companion object {
        private const val EPS = 1e-6f
    }
}
