package net.zimmercarral.mealcalculator.data.db

import androidx.room.TypeConverter
import net.zimmercarral.mealcalculator.data.db.model.FoodItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class FoodItemConverter {
    private val gson: Gson by lazy {
        Gson()
    }

    @TypeConverter
    fun fromString(value: String): List<FoodItem> =
        gson.fromJson(value, object : TypeToken<List<FoodItem>>() {}.type)

    @TypeConverter
    fun fromList(value: List<FoodItem>): String = gson.toJson(value)
}
