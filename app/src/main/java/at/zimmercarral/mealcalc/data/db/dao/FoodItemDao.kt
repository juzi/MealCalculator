package at.zimmercarral.mealcalc.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import at.zimmercarral.mealcalc.data.db.model.FoodItem

@Dao
interface FoodItemDao {

    @Upsert
    fun insert(foodItem: FoodItem)

    @Query("SELECT * FROM fooditems WHERE uuid = :uuid")
    fun getItemById(uuid: String): FoodItem

    @Delete
    fun deleteItem(foodItem: FoodItem)

    @Query("SELECT * FROM fooditems")
    fun getAllItems(): LiveData<List<FoodItem>>
}