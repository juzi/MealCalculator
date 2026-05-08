package at.zimmercarral.mealcalc.data.db.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDateTime

class FoodItemTest {

    private fun item(
        uuid: String = "u-1",
        name: String = "Apple",
        fat: Float = 0.2f,
        carbs: Float = 14f,
        protein: Float = 0.3f,
        amount: Int = 100,
        lastModifiedAt: LocalDateTime? = LocalDateTime.of(2025, 1, 1, 0, 0),
        isDeleted: Boolean = false,
    ) = FoodItem(uuid, name, fat, carbs, protein, amount, lastModifiedAt, isDeleted)

    @Test
    fun `equality ignores lastModifiedAt`() {
        val a = item(lastModifiedAt = LocalDateTime.of(2025, 1, 1, 0, 0))
        val b = item(lastModifiedAt = LocalDateTime.of(2026, 6, 6, 6, 6))
        assertEquals(a, b)
        assertEquals(a.hashCode(), b.hashCode())
    }

    @Test
    fun `equality is sensitive to uuid`() {
        assertNotEquals(item(uuid = "a"), item(uuid = "b"))
    }

    @Test
    fun `equality is sensitive to macros and amount`() {
        assertNotEquals(item(fat = 1f), item(fat = 2f))
        assertNotEquals(item(carbs = 10f), item(carbs = 11f))
        assertNotEquals(item(protein = 1f), item(protein = 2f))
        assertNotEquals(item(amount = 100), item(amount = 200))
    }

    @Test
    fun `equality is sensitive to isDeleted`() {
        assertNotEquals(item(isDeleted = false), item(isDeleted = true))
    }

    @Test
    fun `equality is sensitive to name`() {
        assertNotEquals(item(name = "Apple"), item(name = "Banana"))
    }

    @Test
    fun `toString contains the name and isDeleted flag`() {
        val text = item(name = "Apple", isDeleted = true).toString()
        assertTrue("expected name in $text", text.contains("Apple"))
        assertTrue("expected deleted flag in $text", text.contains("true"))
    }

    @Test
    fun `toString tolerates a null lastModifiedAt`() {
        // Should not throw — uses LocalDateTime.now() as a fallback.
        item(lastModifiedAt = null).toString()
    }
}
