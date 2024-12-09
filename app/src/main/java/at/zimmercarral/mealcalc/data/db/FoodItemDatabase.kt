package at.zimmercarral.mealcalc.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import at.zimmercarral.mealcalc.data.db.dao.FoodItemDao
import at.zimmercarral.mealcalc.data.db.model.FoodItem

@Database(version = 1, entities = [FoodItem::class], exportSchema = false)
abstract class FoodItemDatabase : RoomDatabase() {
    abstract fun dao(): FoodItemDao
}
