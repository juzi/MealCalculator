package at.zimmercarral.mealcalc.data.cloud

import android.util.Log
import at.zimmercarral.mealcalc.data.db.dao.FoodItemDao
import at.zimmercarral.mealcalc.data.db.model.FoodItem
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class FirestoreSyncService(
    private val dao: FoodItemDao,
    private val cloud: FirestoreFoodItemRepository,
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private var listener: ListenerRegistration? = null
    private var hasPushedLocalOnly = false

    fun start() {
        listener?.remove()
        listener = cloud.listen { remoteItems ->
            scope.launch {
                applyRemote(remoteItems)
                if (!hasPushedLocalOnly) {
                    pushLocalOnly(remoteItems)
                    hasPushedLocalOnly = true
                }
            }
        }
    }

    fun stop() {
        listener?.remove()
        listener = null
        scope.cancel()
    }

    private suspend fun applyRemote(remoteItems: List<FoodItem>) {
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

    private suspend fun pushLocalOnly(remoteItems: List<FoodItem>) {
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
