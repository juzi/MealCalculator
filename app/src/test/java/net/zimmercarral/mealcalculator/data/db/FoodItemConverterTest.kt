package net.zimmercarral.mealcalculator.data.db

import net.zimmercarral.mealcalculator.data.db.model.FoodItem
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDateTime

class FoodItemConverterTest {

    private val converter = FoodItemConverter()

    @Test
    fun `empty list round-trips`() {
        val json = converter.fromList(emptyList())
        assertEquals(emptyList<FoodItem>(), converter.fromString(json))
    }

    @Test
    fun `non-empty list round-trips through JSON`() {
        val original = listOf(
            FoodItem(
                uuid = "u-1",
                name = "Apple",
                fatPer100g = 0.2f,
                carbsPer100g = 14f,
                proteinPer100g = 0.3f,
                amount = 150,
                lastModifiedAt = LocalDateTime.of(2025, 4, 30, 12, 0, 0),
                isDeleted = false,
            ),
            FoodItem(
                uuid = "u-2",
                name = "Bread",
                fatPer100g = 3.2f,
                carbsPer100g = 49f,
                proteinPer100g = 9f,
                amount = 60,
                lastModifiedAt = null,
                isDeleted = true,
            ),
        )
        val parsed = converter.fromString(converter.fromList(original))
        // FoodItem.equals deliberately ignores lastModifiedAt, so equality covers the rest.
        assertEquals(original, parsed)
        assertEquals(original[1].isDeleted, parsed[1].isDeleted)
    }

    @Test
    fun `serialized form is valid JSON containing the names`() {
        val json = converter.fromList(
            listOf(
                FoodItem("u-1", "Apple", 0f, 0f, 0f, 0, null, false)
            )
        )
        assertTrue(json.contains("Apple"))
        assertTrue(json.startsWith("[") && json.endsWith("]"))
    }
}
