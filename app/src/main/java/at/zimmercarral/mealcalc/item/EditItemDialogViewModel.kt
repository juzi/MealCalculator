package at.zimmercarral.mealcalc.item

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.focus.FocusRequester
import at.zimmercarral.mealcalc.data.db.model.FoodItem
import java.time.LocalDateTime
import java.util.UUID

class EditItemDialogViewModel {
    var uuid = UUID.randomUUID().toString()
        private set
    var name = mutableStateOf("Unknown")
        private set
    var fatPer100g = mutableStateOf("")
        private set
    var carbsPer100g = mutableStateOf("")
        private set
    var proteinPer100g = mutableStateOf("")
        private set
    var amount = mutableStateOf("")
        private set

    var isVisible = mutableStateOf(false)
        private set
    var inEditMode = mutableStateOf(false)
        private set

    var nameFocusRequester = mutableStateOf(FocusRequester())
    var fatFocusRequester = mutableStateOf(FocusRequester())
    var amountFocusRequester = mutableStateOf(FocusRequester())
    var initialFocus = mutableStateOf(Focus.AMOUNT)

    fun requestFocus(focus: Focus) {
        when (focus) {
            Focus.NAME -> nameFocusRequester.value.requestFocus()
            Focus.FAT -> fatFocusRequester.value.requestFocus()
            Focus.AMOUNT -> amountFocusRequester.value.requestFocus()
            else -> {}
        }
    }

    fun getFoodItem(): FoodItem {
        return FoodItem(uuid,
            name.value,
            zeroFloatIfEmpty(fatPer100g.value),
            zeroFloatIfEmpty(carbsPer100g.value),
            zeroFloatIfEmpty(proteinPer100g.value),
            zeroIntIfEmpty(amount.value),
            LocalDateTime.now(),
            false)
    }

    private fun zeroFloatIfEmpty(value: String): Float {
        if (value.isEmpty()) {
            return 0f
        }
        return value.toFloat()
    }

    private fun zeroIntIfEmpty(value: String): Int {
        if (value.isEmpty()) {
            return 0
        }
        return value.toInt()
    }

    fun setInUpdateMode(updating: Boolean) {
        inEditMode.value = updating
    }

    fun setVisible(visible: Boolean) {
        isVisible.value = visible
    }

    fun setItem(item: FoodItem) {
        println("setting item " + item.name)
        uuid = item.uuid
        name.value = item.name
        fatPer100g.value = item.fatPer100g.toString()
        carbsPer100g.value = item.carbsPer100g.toString()
        proteinPer100g.value = item.proteinPer100g.toString()
        amount.value = item.amount.toString()
    }

    fun clear() {
        uuid = UUID.randomUUID().toString()
        name.value = "Unknown"
        fatPer100g.value = ""
        carbsPer100g.value = ""
        proteinPer100g.value = ""
        amount.value = ""
    }

    fun setName(name: String) {
        this.name.value = name
    }

    fun setFatPer100g(fatStr: String) {
        fatPer100g.value = fatStr
    }

    fun setCarbsPer100g(carbStr: String) {
        carbsPer100g.value = carbStr
    }

    fun setProteinPer100g(proteinStr: String) {
        proteinPer100g.value = proteinStr
    }

    fun setAmount(amountStr: String) {
        this.amount.value = amountStr
    }

    fun calculateCarbs(): String {
        return "0"
    }

    fun calculateFPE(): String {
        return "0"
    }

    fun setInitialFocus(amount: Focus) {

    }


}
