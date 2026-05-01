package at.zimmercarral.mealcalc.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import at.zimmercarral.mealcalc.data.db.model.FoodItem
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodItemDao {

    @Upsert
    suspend fun updateOrInsert(foodItem: FoodItem)

    @Query("SELECT * FROM fooditems WHERE uuid = :uuid")
    suspend fun getItemById(uuid: String): FoodItem

    @Delete
    suspend fun deleteItem(foodItem: FoodItem)

    @Query("SELECT * FROM fooditems")
    suspend fun getAllItems(): List<FoodItem>

    @Query("SELECT * FROM fooditems")
    fun observeAllItems(): Flow<List<FoodItem>>
}