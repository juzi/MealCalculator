package net.zimmercarral.mealcalculator.data.cloud

import android.util.Log
import net.zimmercarral.mealcalculator.data.db.dao.FoodItemDao
import net.zimmercarral.mealcalculator.data.db.model.FoodItem
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FirestoreSyncService(
    private val dao: FoodItemDao,
    private val cloud: FirestoreFoodItemRepository,
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private var listener: ListenerRegistration? = null
    private var hasPushedLocalOnly = false

    private val listenerState = MutableStateFlow(SyncState.PENDING)

    /** The current sync state; brief [SyncState.PENDING] phases are hidden. */
    val state: StateFlow<SyncState> =
        combine(listenerState, cloud.writeFailed) { state, writeFailed ->
            if (writeFailed) SyncState.ERROR else state
        }
            .hideBriefPending()
            .stateIn(scope, SharingStarted.Eagerly, SyncState.PENDING)

    fun start() {
        listener?.remove()
        listener = cloud.listen(
            onChange = { remoteItems ->
                scope.launch {
                    applyRemote(remoteItems)
                    if (!hasPushedLocalOnly) {
                        pushLocalOnly(remoteItems)
                        hasPushedLocalOnly = true
                    }
                }
            },
            onSyncState = { listenerState.value = it },
        )
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
