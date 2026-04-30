package at.zimmercarral.mealcalc.data.repo

import at.zimmercarral.mealcalc.data.cloud.FirestoreFoodItemRepository
import at.zimmercarral.mealcalc.data.db.dao.FoodItemDao
import at.zimmercarral.mealcalc.data.db.model.FoodItem

class FoodItemRepository(
    private val dao: FoodItemDao,
    initialCloud: FirestoreFoodItemRepository? = null,
) {
    @Volatile
    private var cloud: FirestoreFoodItemRepository? = initialCloud

    fun setCloud(cloud: FirestoreFoodItemRepository?) {
        this.cloud = cloud
    }

    fun upsert(item: FoodItem) {
        dao.updateOrInsert(item)
        cloud?.upsert(item)
    }

    fun delete(item: FoodItem) {
        val tombstone = item.copy(isDeleted = true, lastModifiedAt = java.time.LocalDateTime.now())
        dao.deleteItem(item)
        cloud?.upsert(tombstone)
    }

    fun getAll(): List<FoodItem> = dao.getAllItems()
}
