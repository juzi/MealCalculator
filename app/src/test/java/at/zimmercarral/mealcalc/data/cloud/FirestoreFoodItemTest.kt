package at.zimmercarral.mealcalc.data.cloud

import at.zimmercarral.mealcalc.data.db.model.FoodItem
import com.google.firebase.Timestamp
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDateTime
import java.time.ZoneId

class FirestoreFoodItemTest {

    @Test
    fun `from copies all scalar fields and converts the timestamp`() {
        val source = FoodItem(
            uuid = "u-1",
            name = "Apple",
            fatPer100g = 0.2f,
            carbsPer100g = 14.0f,
            proteinPer100g = 0.3f,
            amount = 150,
            lastModifiedAt = LocalDateTime.of(2025, 4, 30, 12, 0, 0, 500_000_000),
            isDeleted = false,
        )
        val dto = FirestoreFoodItem.from(source)
        assertEquals("u-1", dto.uuid)
        assertEquals("Apple", dto.name)
        assertEquals(0.2, dto.fatPer100g, 1e-6)
        assertEquals(14.0, dto.carbsPer100g, 1e-6)
        assertEquals(0.3, dto.proteinPer100g, 1e-6)
        assertEquals(150L, dto.amount)
        assertEquals(false, dto.deleted)
        assertNotNull(dto.lastModifiedAt)
        assertEquals(500_000_000, dto.lastModifiedAt!!.nanoseconds)
    }

    @Test
    fun `from substitutes now when lastModifiedAt is null`() {
        val source = FoodItem(
            uuid = "u-1",
            name = "Apple",
            fatPer100g = 0f,
            carbsPer100g = 0f,
            proteinPer100g = 0f,
            amount = 0,
            lastModifiedAt = null,
            isDeleted = false,
        )
        val dto = FirestoreFoodItem.from(source)
        assertNotNull(dto.lastModifiedAt)
    }

    @Test
    fun `toFoodItem maps all fields back`() {
        val source = LocalDateTime.of(2025, 4, 30, 12, 0, 0, 500_000_000)
        val instant = source.atZone(ZoneId.systemDefault()).toInstant()
        val dto = FirestoreFoodItem(
            uuid = "u-1",
            name = "Bread",
            fatPer100g = 3.2,
            carbsPer100g = 49.0,
            proteinPer100g = 9.0,
            amount = 60,
            lastModifiedAt = Timestamp(instant.epochSecond, instant.nano),
            deleted = true,
        )
        val item = dto.toFoodItem()
        assertEquals("u-1", item.uuid)
        assertEquals("Bread", item.name)
        assertEquals(3.2f, item.fatPer100g, 1e-4f)
        assertEquals(49.0f, item.carbsPer100g, 1e-4f)
        assertEquals(9.0f, item.proteinPer100g, 1e-4f)
        assertEquals(60, item.amount)
        assertEquals(true, item.isDeleted)
        assertEquals(source, item.lastModifiedAt)
    }

    @Test
    fun `toFoodItem leaves a null Timestamp as a null LocalDateTime`() {
        val dto = FirestoreFoodItem(uuid = "u-1", name = "X", lastModifiedAt = null)
        assertNull(dto.toFoodItem().lastModifiedAt)
    }

    @Test
    fun `from then toFoodItem preserves data ignoring sub-second drift`() {
        val original = FoodItem(
            uuid = "u-1",
            name = "Apple",
            fatPer100g = 0.2f,
            carbsPer100g = 14.0f,
            proteinPer100g = 0.3f,
            amount = 150,
            lastModifiedAt = LocalDateTime.of(2025, 4, 30, 12, 0, 0),
            isDeleted = false,
        )
        val round = FirestoreFoodItem.from(original).toFoodItem()
        // FoodItem.equals ignores lastModifiedAt, so this checks the rest of the fields.
        assertEquals(original, round)
        // And the timestamp itself round-trips at second granularity.
        assertEquals(original.lastModifiedAt, round.lastModifiedAt)
    }

    @Test
    fun `defaults are safe for documents missing fields`() {
        val dto = FirestoreFoodItem()
        val item = dto.toFoodItem()
        assertEquals("", item.uuid)
        assertEquals("", item.name)
        assertEquals(0f, item.fatPer100g, 0f)
        assertEquals(0f, item.carbsPer100g, 0f)
        assertEquals(0f, item.proteinPer100g, 0f)
        assertEquals(0, item.amount)
        assertNull(item.lastModifiedAt)
        assertTrue(!item.isDeleted)
    }
}
