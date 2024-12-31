package at.zimmercarral.mealcalc.data.sync

import android.os.Handler
import android.os.Looper
import at.zimmercarral.mealcalc.data.db.dao.FoodItemDao
import at.zimmercarral.mealcalc.data.db.model.FoodItem
import at.zimmercarral.mealcalc.data.http.HttpFoodItemRepository
import java.time.LocalDateTime
import java.util.concurrent.Executors

class SynchronizationTask() {
    //    private val sqliteDataRepository = SqliteDataRepository(context)
    private val httpFoodItemRepository = HttpFoodItemRepository()


    fun syncDatabases(
        dao: FoodItemDao,
        onSyncCompleted: () -> Unit
    ) {
        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())
        val localItemsMap = dao.getAllItems().associateBy { it.uuid }
        executor.execute {
            handler.post {
                httpFoodItemRepository.getEntries(
                    onSuccess = { remoteItems ->
                        remoteItems.forEach { item ->
                            if (item.lastModifiedAt == null) {
                                item.lastModifiedAt = LocalDateTime.now()
                            }
                        }
                        val remoteItemsMap = remoteItems.associateBy { it.uuid }
                        synchronizeFoodItems(dao, remoteItemsMap, localItemsMap)
                    })
            }
        }
    }

    fun synchronizeFoodItems(
        dao: FoodItemDao,
        remoteItems: Map<String, FoodItem>,
        localItems: Map<String, FoodItem>
    ) {
        // Process remote entries
        remoteItems.forEach { (remoteUuid, remoteItem) ->
            when {
                !localItems.containsKey(remoteUuid) -> {
                    if (!remoteItem.isDeleted) {
                        dao.updateOrInsert(remoteItem)
                    }
                }

                else -> {
                    if (remoteItem.isDeleted) {
                        dao.deleteItem(remoteItem)
                    } else {
                        val localEntry = localItems[remoteUuid]
                        if (remoteItem != localEntry && localEntry != null &&
                            remoteItem.lastModifiedAt != null &&
                            remoteItem.lastModifiedAt!!.isAfter(localEntry.lastModifiedAt)
                        ) {
                            dao.updateOrInsert(remoteItem)
                        }
                    }
                }
            }
        }

        // Process local entries
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
}