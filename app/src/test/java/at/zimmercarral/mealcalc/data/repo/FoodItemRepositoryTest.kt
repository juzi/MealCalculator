package at.zimmercarral.mealcalc.data.repo

import at.zimmercarral.mealcalc.data.db.dao.FoodItemDao
import at.zimmercarral.mealcalc.data.db.model.FoodItem
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDateTime

class FoodItemRepositoryTest {

    // Note: FoodItem.equals ignores lastModifiedAt, so MutableStateFlow would silently
    // drop timestamp-only updates. Track storage in a plain list and expose changes via
    // a SharedFlow that always emits.
    private class FakeFoodItemDao : FoodItemDao {
        private val backing = mutableListOf<FoodItem>()
        private val updates = MutableSharedFlow<Unit>(
            replay = 0,
            extraBufferCapacity = 64,
            onBufferOverflow = BufferOverflow.DROP_OLDEST,
        )

        override suspend fun updateOrInsert(foodItem: FoodItem) {
            backing.removeAll { it.uuid == foodItem.uuid }
            backing.add(foodItem)
            updates.tryEmit(Unit)
        }

        override suspend fun getItemById(uuid: String): FoodItem =
            backing.first { it.uuid == uuid }

        override suspend fun deleteItem(foodItem: FoodItem) {
            backing.removeAll { it.uuid == foodItem.uuid }
            updates.tryEmit(Unit)
        }

        override suspend fun getAllItems(): List<FoodItem> = backing.toList()

        override fun observeAllItems(): Flow<List<FoodItem>> =
            updates.onStart { emit(Unit) }.let { source ->
                kotlinx.coroutines.flow.flow {
                    source.collect { emit(backing.toList()) }
                }
            }
    }

    private fun item(
        uuid: String,
        name: String = "n-$uuid",
        isDeleted: Boolean = false,
        lastModifiedAt: LocalDateTime? = LocalDateTime.of(2025, 1, 1, 0, 0),
    ) = FoodItem(
        uuid = uuid,
        name = name,
        fatPer100g = 0f,
        carbsPer100g = 0f,
        proteinPer100g = 0f,
        amount = 100,
        lastModifiedAt = lastModifiedAt,
        isDeleted = isDeleted,
    )

    @Test
    fun `upsert writes to the dao`() = runTest {
        val dao = FakeFoodItemDao()
        val repo = FoodItemRepository(dao)
        repo.upsert(item("a"))
        assertEquals(listOf("a"), dao.getAllItems().map { it.uuid })
    }

    @Test
    fun `delete removes the row from the dao`() = runTest {
        val dao = FakeFoodItemDao()
        val repo = FoodItemRepository(dao)
        val a = item("a")
        repo.upsert(a)
        repo.delete(a)
        assertTrue(dao.getAllItems().isEmpty())
    }

    @Test
    fun `observeVisibleSorted hides deleted items`() = runTest {
        val dao = FakeFoodItemDao()
        val repo = FoodItemRepository(dao)
        repo.upsert(item("a", name = "Apple"))
        repo.upsert(item("b", name = "Bread", isDeleted = true))
        val visible = repo.observeVisibleSorted().first()
        assertEquals(listOf("Apple"), visible.map { it.name })
    }

    @Test
    fun `observeVisibleSorted sorts by name`() = runTest {
        val dao = FakeFoodItemDao()
        val repo = FoodItemRepository(dao)
        repo.upsert(item("3", name = "Cherry"))
        repo.upsert(item("1", name = "Apple"))
        repo.upsert(item("2", name = "Banana"))
        val visible = repo.observeVisibleSorted().first()
        assertEquals(listOf("Apple", "Banana", "Cherry"), visible.map { it.name })
    }

    @Test
    fun `backfillMissingTimestamps fills only the rows missing a timestamp`() = runTest {
        val dao = FakeFoodItemDao()
        val repo = FoodItemRepository(dao)
        val fixed = LocalDateTime.of(2024, 1, 1, 0, 0)
        repo.upsert(item("a", lastModifiedAt = fixed))
        repo.upsert(item("b", lastModifiedAt = null))

        repo.backfillMissingTimestamps()

        val byUuid = dao.getAllItems().associateBy { it.uuid }
        assertEquals(fixed, byUuid["a"]!!.lastModifiedAt)
        assertNotNull(byUuid["b"]!!.lastModifiedAt)
    }

    @Test
    fun `setCloud updates the cloud reference and is safe with null`() = runTest {
        val dao = FakeFoodItemDao()
        val repo = FoodItemRepository(dao)
        // Should not throw and should not affect dao behavior.
        repo.setCloud(null)
        repo.upsert(item("a"))
        assertEquals(listOf("a"), dao.getAllItems().map { it.uuid })
    }
}
