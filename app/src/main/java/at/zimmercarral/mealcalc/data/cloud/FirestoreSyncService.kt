package at.zimmercarral.mealcalc.data.cloud

import android.util.Log
import at.zimmercarral.mealcalc.data.db.dao.FoodItemDao
import at.zimmercarral.mealcalc.data.db.model.FoodItem
import com.google.firebase.firestore.ListenerRegistration

class FirestoreSyncService(
    private val dao: FoodItemDao,
    private val cloud: FirestoreFoodItemRepository,
) {
    private var listener: ListenerRegistration? = null
    private var hasPushedLocalOnly = false

    fun start(onItemsChanged: (List<FoodItem>) -> Unit) {
        listener?.remove()
        listener = cloud.listen { remoteItems ->
            applyRemote(remoteItems)
            if (!hasPushedLocalOnly) {
                pushLocalOnly(remoteItems)
                hasPushedLocalOnly = true
            }
            onItemsChanged(dao.getAllItems())
        }
    }

    fun stop() {
        listener?.remove()
        listener = null
    }

    private fun applyRemote(remoteItems: List<FoodItem>) {
        val localByUuid = dao.getAllItems().associateBy { it.uuid }
        remoteItems.forEach { remote ->
            val local = localByUuid[remote.uuid]
            when {
                local == null -> {
                    if (!remote.isDeleted) dao.updateOrInsert(remote)
                }
                remote.isDeleted -> dao.deleteItem(local)
                remoteIsNewer(remote, local) -> dao.updateOrInsert(remote)
            }
        }
    }

    private fun pushLocalOnly(remoteItems: List<FoodItem>) {
        val remoteUuids = remoteItems.map { it.uuid }.toSet()
        val locals = dao.getAllItems()
        locals.filter { it.uuid !in remoteUuids }.forEach {
            Log.i(TAG, "pushing local-only item ${it.uuid} to cloud")
            cloud.upsert(it)
        }
    }

    private fun remoteIsNewer(remote: FoodItem, local: FoodItem): Boolean {
        val remoteAt = remote.lastModifiedAt ?: return false
        val localAt = local.lastModifiedAt ?: return true
        return remoteAt.isAfter(localAt)
    }

    companion object {
        private const val TAG = "FirestoreSync"
    }
}
