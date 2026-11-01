package net.zimmercarral.mealcalculator

import android.app.Application
import androidx.room.Room
import net.zimmercarral.mealcalculator.data.auth.AuthManager
import net.zimmercarral.mealcalculator.data.db.FoodItemDatabase
import net.zimmercarral.mealcalculator.data.db.dao.FoodItemDao
import net.zimmercarral.mealcalculator.data.repo.FoodItemRepository
import com.google.firebase.auth.FirebaseAuth

class MealCalcApplication : Application() {

    private val database: FoodItemDatabase by lazy {
        Room.databaseBuilder(this, FoodItemDatabase::class.java, "food-items.db").build()
    }

    val foodItemDao: FoodItemDao by lazy { database.dao() }

    val foodItemRepository: FoodItemRepository by lazy { FoodItemRepository(foodItemDao) }

    val authManager: AuthManager by lazy {
        AuthManager(FirebaseAuth.getInstance(), getString(R.string.default_web_client_id))
    }
}
