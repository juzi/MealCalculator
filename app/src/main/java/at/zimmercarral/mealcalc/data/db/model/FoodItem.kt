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
    val lastModifiedAt: LocalDateTime?,

    @ColumnInfo("isDeleted")
    val isDeleted: Boolean

)