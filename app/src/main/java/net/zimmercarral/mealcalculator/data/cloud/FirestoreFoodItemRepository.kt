package net.zimmercarral.mealcalculator.data.cloud

import android.util.Log
import net.zimmercarral.mealcalculator.data.db.model.FoodItem
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.MetadataChanges
import com.google.firebase.firestore.Source
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await

class FirestoreFoodItemRepository(
    private val firestore: FirebaseFirestore,
    private val userId: String,
) {
    private fun collection() = firestore
        .collection("users")
        .document(userId)
        .collection("foodItems")

    private val _writeFailed = MutableStateFlow(false)

    /** True once the server rejected a write. Stays true for this repository. */
    val writeFailed: StateFlow<Boolean> = _writeFailed.asStateFlow()

    /**
     * Listens to the user's food items.
     *
     * [onChange] receives the items on the first snapshot and whenever documents
     * change. [onSyncState] receives the sync state on every snapshot, including
     * metadata-only ones (write acknowledged, connection lost or regained), and
     * [SyncState.ERROR] if the listener fails, after which Firestore stops it.
     */
    fun listen(
        onChange: (List<FoodItem>) -> Unit,
        onSyncState: (SyncState) -> Unit,
    ): ListenerRegistration {
        var isFirstSnapshot = true
        return collection().addSnapshotListener(MetadataChanges.INCLUDE) { snapshot, error ->
            if (error != null) {
                Log.w(TAG, "snapshot listener error", error)
                onSyncState(SyncState.ERROR)
                return@addSnapshotListener
            }
            if (snapshot == null) return@addSnapshotListener
            // Metadata-only snapshots have no document changes; skip the item merge for them.
            if (isFirstSnapshot || snapshot.documentChanges.isNotEmpty()) {
                isFirstSnapshot = false
                val items = snapshot.documents.mapNotNull {
                    it.toObject(FirestoreFoodItem::class.java)?.toFoodItem()
                }
                onChange(items)
            }
            onSyncState(
                SyncState.fromSnapshot(
                    hasPendingWrites = snapshot.metadata.hasPendingWrites(),
                    isFromCache = snapshot.metadata.isFromCache,
                )
            )
        }
    }

    fun upsert(item: FoodItem) {
        val dto = FirestoreFoodItem.from(item)
        collection()
            .document(item.uuid)
            .set(dto)
            .addOnFailureListener {
                Log.w(TAG, "upsert failed for ${item.uuid}", it)
                _writeFailed.value = true
            }
    }

    /**
     * Hard-deletes every food item stored for this user, tombstones included.
     * Reads from the server (not the local cache) so no document is missed,
     * which also makes this fail fast when offline.
     */
    suspend fun deleteAll() {
        val docs = collection().get(Source.SERVER).await().documents
        docs.chunked(MAX_BATCH_SIZE).forEach { chunk ->
            val batch = firestore.batch()
            chunk.forEach { batch.delete(it.reference) }
            batch.commit().await()
        }
    }

    companion object {
        private const val TAG = "FirestoreFoodItem"
        private const val MAX_BATCH_SIZE = 500
    }
}
