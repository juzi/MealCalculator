package at.zimmercarral.mealcalc.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "fooditems")
data class FoodItem(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo("uuid")
    val uuid: String,

    @ColumnInfo("name")
    val name: String,

    @ColumnInfo("fat")
    val fatPer100g: Float,

    @ColumnInfo("carbs")
    val carbsPer100g: Float,

    @ColumnInfo("protein")
    val proteinPer100g: Float,

    @ColumnInfo("amount")
    val amount: Int,

    @ColumnInfo("lastModifiedAt")
    var lastModifiedAt: LocalDateTime?,

    @ColumnInfo("isDeleted")
    val isDeleted: Boolean

) {
    override fun toString(): String {
        val dateTime = if (lastModifiedAt != null) {
            lastModifiedAt
        } else {
            LocalDateTime.now()
        }

        return "FoodItem(%s, %.1f, %.1f, %.1f, %d, %s, %b)".format(name,
            fatPer100g,
            carbsPer100g,
            proteinPer100g,
            amount,
            dateTime,
            isDeleted)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FoodItem

        if (uuid != other.uuid) return false
        if (name != other.name) return false
        if (fatPer100g != other.fatPer100g) return false
        if (carbsPer100g != other.carbsPer100g) return false
        if (proteinPer100g != other.proteinPer100g) return false
        if (amount != other.amount) return false
        if (isDeleted != other.isDeleted) return false

        return true
    }
}
