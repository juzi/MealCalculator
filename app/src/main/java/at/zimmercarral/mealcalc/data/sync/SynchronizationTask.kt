package at.zimmercarral.mealcalc.data.sync

import android.os.Handler
import android.os.Looper
import at.zimmercarral.mealcalc.data.db.model.FoodItem
import at.zimmercarral.mealcalc.data.http.HttpFoodItemRepository
import at.zimmercarral.mealcalc.meal.MealViewModel
import java.util.concurrent.Executors

class SynchronizationTask() {
    //    private val sqliteDataRepository = SqliteDataRepository(context)
    private val httpFoodItemRepository = HttpFoodItemRepository()


    fun syncDatabases(mealViewModel: MealViewModel) {
        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())
        executor.execute {
            handler.post {
                httpFoodItemRepository.getEntries(
                    onSuccess = { items ->
                        synchronizeFoodItems(items)
                    })
            }
        }
    }

    fun synchronizeFoodItems(remoteItems: List<FoodItem>) {
        return
    }
//        val localItems = sqliteDataRepository.getAllNutritionalData()
//
//        // Process remote entries
//        remoteItems.forEach { (remoteUuid, remoteItem) ->
//            when {
//                !localItems.containsKey(remoteUuid) -> {
//                    if (!remoteItem.deleted) {
//                        sqliteDataRepository.saveNutritionalData(remoteItem)
//                    }
//                }
//
//                else -> {
//                    if (remoteItem.deleted) {
//                        sqliteDataRepository.deleteNutritionalData(remoteItem)
//                    } else {
//                        val localEntry = localItems[remoteUuid]
//                        if (remoteItem.differentValues(localEntry) &&
//                            localEntry != null &&
//                            DateUtil.isAfter(remoteItem.lastchanged, localEntry.lastchanged)
//                        ) {
//                            sqliteDataRepository.updateNutritionalData(remoteItem)
//                        }
//                    }
//                }
//            }
//        }
//
//        // Process local entries
//        localItems.forEach { (localUuid, localValues) ->
//            when {
//                !remoteItems.containsKey(localUuid) -> {
//                    httpDataRepository.saveEntry(localValues)
//                }
//
//                else -> {
//                    val remoteEntry = remoteItems[localUuid]
//                    if (localValues.differentValues(remoteEntry) &&
//                        remoteEntry != null &&
//                        DateUtil.isAfter(localValues.lastchanged, remoteEntry.lastchanged)
//                    ) {
//                        httpDataRepository.updateEntry(localValues)
//                    }
//                }
//            }
//        }
}