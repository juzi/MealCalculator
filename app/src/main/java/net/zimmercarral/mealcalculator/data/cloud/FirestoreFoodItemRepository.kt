package net.zimmercarral.mealcalculator.data.cloud

import android.util.Log
import net.zimmercarral.mealcalculator.data.db.model.FoodItem
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Source
import kotlinx.coroutines.tasks.await

class FirestoreFoodItemRepository(
    private val firestore: FirebaseFirestore,
    private val userId: String,
) {
    private fun collection() = firestore
        .collection("users")
        .document(userId)
        .collection("foodItems")

    fun listen(onChange: (List<FoodItem>) -> Unit): ListenerRegistration =
        collection().addSnapshotListener { snapshot, error ->
            if (error != null) {
                Log.w(TAG, "snapshot listener error", error)
                return@addSnapshotListener
            }
            if (snapshot == null) return@addSnapshotListener
            val items = snapshot.documents.mapNotNull {
                it.toObject(FirestoreFoodItem::class.java)?.toFoodItem()
            }
            onChange(items)
        }

    fun upsert(item: FoodItem) {
        val dto = FirestoreFoodItem.from(item)
        collection()
            .document(item.uuid)
            .set(dto)
            .addOnFailureListener { Log.w(TAG, "upsert failed for ${item.uuid}", it) }
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
