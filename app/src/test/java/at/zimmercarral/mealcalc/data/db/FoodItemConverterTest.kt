package at.zimmercarral.mealcalc.data.db

import at.zimmercarral.mealcalc.data.db.model.FoodItem
import com.google.gson.JsonSyntaxException
import org.junit.Test
import org.junit.Assert.*
import java.time.LocalDateTime

class FoodItemConverterTest {

    private val converter = FoodItemConverter()

    @Test
    fun fromList_emptyList_returnsEmptyJsonArrayString() {
        val list = emptyList<FoodItem>()
        assertEquals("[]", converter.fromList(list))
    }

    @Test
    fun fromString_emptyJsonArrayString_returnsEmptyList() {
        val json = "[]"
        assertTrue(converter.fromString(json).isEmpty())
    }

    @Test
    fun roundTrip_singleItem_restoresEqualItem() {
        val foodItem = FoodItem(
            uuid = "test-uuid-1",
            name = "Test Food 1",
            fatPer100g = 10.0f,
            carbsPer100g = 20.0f,
            proteinPer100g = 5.0f,
            amount = 100,
            lastModifiedAt = null,
            isDeleted = false
        )
        val list = listOf(foodItem)
        val json = converter.fromList(list)
        val resultList = converter.fromString(json)
        assertEquals(1, resultList.size)
        assertEquals(foodItem, resultList[0]) // Relies on FoodItem.equals
    }

    @Test
    fun roundTrip_multipleItems_restoresEqualItems() {
        val foodItem1 = FoodItem(
            uuid = "test-uuid-m1",
            name = "Apple Pie",
            fatPer100g = 15.5f,
            carbsPer100g = 45.0f,
            proteinPer100g = 3.2f,
            amount = 120,
            lastModifiedAt = LocalDateTime.now(),
            isDeleted = false
        )
        val foodItem2 = FoodItem(
            uuid = "test-uuid-m2",
            name = "Chicken Breast",
            fatPer100g = 3.6f,
            carbsPer100g = 0.0f,
            proteinPer100g = 31.0f,
            amount = 200,
            lastModifiedAt = null,
            isDeleted = true // Test with isDeleted true
        )
        val list = listOf(foodItem1, foodItem2)
        val json = converter.fromList(list)
        val resultList = converter.fromString(json)
        assertEquals(2, resultList.size)
        assertTrue(resultList.contains(foodItem1))
        assertTrue(resultList.contains(foodItem2))
    }

    @Test(expected = JsonSyntaxException::class)
    fun fromString_invalidJson_throwsJsonSyntaxException() {
        val invalidJson = "this is not valid json {,"
        converter.fromString(invalidJson)
    }

    @Test(expected = JsonSyntaxException::class)
    fun fromString_jsonOfDifferentStructure_throwsJsonSyntaxException() {
        // This JSON is valid but does not represent a List of FoodItem objects
        val differentJson = "{\"key\":\"value\", \"number\":123}"
        converter.fromString(differentJson)
    }
}
