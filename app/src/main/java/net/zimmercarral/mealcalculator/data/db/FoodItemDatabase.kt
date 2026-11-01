package net.zimmercarral.mealcalculator.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import net.zimmercarral.mealcalculator.data.db.dao.FoodItemDao
import net.zimmercarral.mealcalculator.data.db.model.FoodItem

@Database(version = 2, entities = [FoodItem::class], exportSchema = false)
@TypeConverters(LocalDateTimeConverter::class, Converters::class)
abstract class FoodItemDatabase : RoomDatabase() {
    abstract fun dao(): FoodItemDao
}
