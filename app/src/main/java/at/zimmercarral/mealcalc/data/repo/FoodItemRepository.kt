package at.zimmercarral.mealcalc.data.repo

import at.zimmercarral.mealcalc.data.cloud.FirestoreFoodItemRepository
import at.zimmercarral.mealcalc.data.db.dao.FoodItemDao
import at.zimmercarral.mealcalc.data.db.model.FoodItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime

class FoodItemRepository(
    private val dao: FoodItemDao,
    initialCloud: FirestoreFoodItemRepository? = null,
) {
    @Volatile
    private var cloud: FirestoreFoodItemRepository? = initialCloud

    fun setCloud(cloud: FirestoreFoodItemRepository?) {
        this.cloud = cloud
    }

    suspend fun upsert(item: FoodItem) {
        dao.updateOrInsert(item)
        cloud?.upsert(item)
    }

    suspend fun delete(item: FoodItem) {
        val tombstone = item.copy(isDeleted = true, lastModifiedAt = LocalDateTime.now())
        dao.deleteItem(item)
        cloud?.upsert(tombstone)
    }

    fun observeVisibleSorted(): Flow<List<FoodItem>> =
        dao.observeAllItems().map { list ->
            list.filter { !it.isDeleted }.sortedBy { it.name }
        }

    suspend fun backfillMissingTimestamps() {
        val now = LocalDateTime.now()
        dao.getAllItems()
            .filter { it.lastModifiedAt == null }
            .forEach { dao.updateOrInsert(it.copy(lastModifiedAt = now)) }
    }
}
