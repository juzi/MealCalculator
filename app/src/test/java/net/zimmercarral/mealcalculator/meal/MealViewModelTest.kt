package net.zimmercarral.mealcalculator.meal

import net.zimmercarral.mealcalculator.data.db.model.FoodItem
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDateTime

class MealViewModelTest {

    private fun item(
        uuid: String,
        name: String = "n-$uuid",
        fat: Float = 1f,
        carbs: Float = 2f,
        protein: Float = 3f,
        amount: Int = 100,
    ) = FoodItem(
        uuid = uuid,
        name = name,
        fatPer100g = fat,
        carbsPer100g = carbs,
        proteinPer100g = protein,
        amount = amount,
        lastModifiedAt = LocalDateTime.of(2025, 1, 1, 0, 0),
        isDeleted = false,
    )

    @Test
    fun `addOrUpdateItem appends a new item`() {
        val vm = MealViewModel()
        vm.addOrUpdateItem(item("a"))
        assertEquals(1, vm.mealItems.size)
        assertEquals("a", vm.mealItems[0].uuid)
    }

    @Test
    fun `addOrUpdateItem replaces an existing item by uuid in place`() {
        val vm = MealViewModel()
        vm.addOrUpdateItem(item("a", name = "first", amount = 100))
        vm.addOrUpdateItem(item("b", name = "second", amount = 200))
        vm.addOrUpdateItem(item("a", name = "first-updated", amount = 250))
        assertEquals(2, vm.mealItems.size)
        // First entry still at index 0 — update is in place, not append.
        val updated = vm.mealItems[0]
        assertEquals("a", updated.uuid)
        assertEquals("first-updated", updated.name)
        assertEquals(250, updated.amount)
    }

    @Test
    fun `addItems adds many in order`() {
        val vm = MealViewModel()
        vm.addItems(listOf(item("a"), item("b"), item("c")))
        assertEquals(listOf("a", "b", "c"), vm.mealItems.map { it.uuid })
    }

    @Test
    fun `removeItem deletes by equality`() {
        val vm = MealViewModel()
        val a = item("a")
        vm.addOrUpdateItem(a)
        vm.addOrUpdateItem(item("b"))
        vm.removeItem(a)
        assertEquals(listOf("b"), vm.mealItems.map { it.uuid })
    }

    @Test
    fun `removeAllItems clears the list`() {
        val vm = MealViewModel()
        vm.addItems(listOf(item("a"), item("b")))
        vm.removeAllItems()
        assertTrue(vm.mealItems.isEmpty())
    }

    @Test
    fun `getItemsAsJson and setItemsFromJson round-trip the meal`() {
        val src = MealViewModel().apply {
            addItems(listOf(item("a", name = "Apple"), item("b", name = "Bread")))
        }
        val json = src.getItemsAsJson()

        val dst = MealViewModel()
        dst.setItemsFromJson(json)
        // FoodItem.equals ignores timestamps, so this asserts macros/name/amount/deleted match.
        assertEquals(src.mealItems.toList(), dst.mealItems.toList())
    }

    @Test
    fun `setItemsFromJson swallows malformed json and leaves the list untouched`() {
        val vm = MealViewModel()
        vm.addOrUpdateItem(item("a"))
        // Invalid JSON should not throw — exception is logged and ignored.
        vm.setItemsFromJson("not valid json")
        assertEquals(listOf("a"), vm.mealItems.map { it.uuid })
    }
}
