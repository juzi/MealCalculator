package net.zimmercarral.mealcalculator.edit

import org.junit.Assert.assertEquals
import org.junit.Assert.assertSame
import org.junit.Test

class CalculatedValuesTest {

    @Test
    fun `add accumulates each component`() {
        val a = CalculatedValues(1f, 2f, 3f, 4f)
        val b = CalculatedValues(0.5f, 1.5f, 2.5f, 3.5f)
        a.add(b)
        assertEquals(1.5f, a.calculatedBEs, 1e-6f)
        assertEquals(3.5f, a.calculatedFPEs, 1e-6f)
        assertEquals(5.5f, a.calculatedBEGrams, 1e-6f)
        assertEquals(7.5f, a.calculatedFPEGrams, 1e-6f)
    }

    @Test
    fun `add returns the same instance it was called on`() {
        val a = CalculatedValues(0f, 0f, 0f, 0f)
        val b = CalculatedValues(1f, 1f, 1f, 1f)
        assertSame(a, a.add(b))
    }

    @Test
    fun `add does not mutate the operand`() {
        val a = CalculatedValues(1f, 1f, 1f, 1f)
        val b = CalculatedValues(2f, 2f, 2f, 2f)
        a.add(b)
        assertEquals(2f, b.calculatedBEs, 1e-6f)
        assertEquals(2f, b.calculatedFPEs, 1e-6f)
        assertEquals(2f, b.calculatedBEGrams, 1e-6f)
        assertEquals(2f, b.calculatedFPEGrams, 1e-6f)
    }
}
