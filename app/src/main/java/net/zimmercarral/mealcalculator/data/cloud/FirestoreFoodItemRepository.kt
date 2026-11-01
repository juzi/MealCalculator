package net.zimmercarral.mealcalculator.data.cloud

import android.util.Log
import net.zimmercarral.mealcalculator.data.db.model.FoodItem
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

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

    companion object {
        private const val TAG = "FirestoreFoodItem"
    }
}
