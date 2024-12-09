package at.zimmercarral.mealcalc.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import at.zimmercarral.mealcalc.data.db.dao.FoodItemDao
import at.zimmercarral.mealcalc.data.db.model.FoodItem
import at.zimmercarral.mealcalc.data.db.model.Converters

@Database(version = 1, entities = [FoodItem::class], exportSchema = false)
@TypeConverters(Converters::class)
abstract class FoodItemDatabase : RoomDatabase() {
    abstract fun dao(): FoodItemDao
}
