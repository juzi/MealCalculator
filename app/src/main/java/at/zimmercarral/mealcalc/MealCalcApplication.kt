package at.zimmercarral.mealcalc

import android.app.Application
import androidx.room.Room
import at.zimmercarral.mealcalc.data.auth.AuthManager
import at.zimmercarral.mealcalc.data.db.FoodItemDatabase
import at.zimmercarral.mealcalc.data.db.dao.FoodItemDao
import at.zimmercarral.mealcalc.data.repo.FoodItemRepository
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
