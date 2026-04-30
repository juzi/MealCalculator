package at.zimmercarral.mealcalc.data.cloud

import at.zimmercarral.mealcalc.data.db.model.FoodItem
import com.google.firebase.Timestamp
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

data class FirestoreFoodItem(
    var uuid: String = "",
    var name: String = "",
    var fatPer100g: Double = 0.0,
    var carbsPer100g: Double = 0.0,
    var proteinPer100g: Double = 0.0,
    var amount: Long = 0,
    var lastModifiedAt: Timestamp? = null,
    var deleted: Boolean = false,
) {
    fun toFoodItem(): FoodItem = FoodItem(
        uuid = uuid,
        name = name,
        fatPer100g = fatPer100g.toFloat(),
        carbsPer100g = carbsPer100g.toFloat(),
        proteinPer100g = proteinPer100g.toFloat(),
        amount = amount.toInt(),
        lastModifiedAt = lastModifiedAt?.toLocalDateTime(),
        isDeleted = deleted,
    )

    companion object {
        fun from(item: FoodItem): FirestoreFoodItem = FirestoreFoodItem(
            uuid = item.uuid,
            name = item.name,
            fatPer100g = item.fatPer100g.toDouble(),
            carbsPer100g = item.carbsPer100g.toDouble(),
            proteinPer100g = item.proteinPer100g.toDouble(),
            amount = item.amount.toLong(),
            lastModifiedAt = (item.lastModifiedAt ?: LocalDateTime.now()).toTimestamp(),
            deleted = item.isDeleted,
        )
    }
}

private fun Timestamp.toLocalDateTime(): LocalDateTime =
    LocalDateTime.ofInstant(Instant.ofEpochSecond(seconds, nanoseconds.toLong()), ZoneId.systemDefault())

private fun LocalDateTime.toTimestamp(): Timestamp {
    val instant = atZone(ZoneId.systemDefault()).toInstant()
    return Timestamp(instant.epochSecond, instant.nano)
}
