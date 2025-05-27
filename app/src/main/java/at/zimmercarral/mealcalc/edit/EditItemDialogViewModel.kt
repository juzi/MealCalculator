package at.zimmercarral.mealcalc.edit

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import at.zimmercarral.mealcalc.data.db.dao.FoodItemDao
import at.zimmercarral.mealcalc.data.db.model.FoodItem
import java.time.LocalDateTime
import java.util.UUID

class EditItemDialogViewModel(
    private val dao: FoodItemDao
) : ViewModel() {
    var index = mutableStateOf(0)

    var uuid = UUID.randomUUID().toString()
        private set
    var name = mutableStateOf("")
        private set
    var fatPer100g = mutableStateOf("")
        private set
    var carbsPer100g = mutableStateOf("")
        private set
    var proteinPer100g = mutableStateOf("")
        private set
    var amount = mutableStateOf(TextFieldValue(""))
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
            Focus.AMOUNT -> {
                amountFocusRequester.value.requestFocus()
            }

            else -> {}
        }
    }

    fun selectAllAmountText() {
        Log.d("ViewModelLog",
            "selectAllAmountText called. Current amount TextFieldValue: ${amount.value}") // Log the whole TextFieldValue
        val currentText = amount.value.text
        if (currentText.isNotEmpty()) {
            val newSelection = TextRange(0, currentText.length)
            amount.value = amount.value.copy(
                selection = newSelection
            )
            // Log AFTER attempting to update
            Log.d("ViewModelLog", "New amount TextFieldValue after selection: ${amount.value}")
        } else {
            Log.d("ViewModelLog", "Amount text is empty, not attempting selection.")
        }
    }

    fun getFoodItem(): FoodItem {
        return FoodItem(uuid,
            name.value,
            zeroFloatIfEmpty(fatPer100g.value),
            zeroFloatIfEmpty(carbsPer100g.value),
            zeroFloatIfEmpty(proteinPer100g.value),
            zeroIntIfEmpty(amount.value.text),
            LocalDateTime.now(),
            false)
    }

    fun saveItem() {
        dao.updateOrInsert(getFoodItem())
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

    fun setIndex(index: Int) {
        this.index.value = index
    }

    fun setItem(item: FoodItem) {
        uuid = item.uuid
        name.value = item.name
        fatPer100g.value = item.fatPer100g.toString()
        carbsPer100g.value = item.carbsPer100g.toString()
        proteinPer100g.value = item.proteinPer100g.toString()
        amount.value = TextFieldValue(item.amount.toString())
    }

    fun clear() {
        uuid = UUID.randomUUID().toString()
        name.value = ""
        fatPer100g.value = ""
        carbsPer100g.value = ""
        proteinPer100g.value = ""
        amount.value = TextFieldValue("")
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

    fun setAmount(amount: TextFieldValue) {
        this.amount.value = amount
    }

    // If you still need to set amount from a String elsewhere:
    fun setAmountFromString(amountStr: String) {
        this.amount.value = TextFieldValue(amountStr)
    }

    fun calculateCarbs(): String {
        return "0"
    }

    fun calculateFPE(): String {
        return "0"
    }

    fun setInitialFocus(focus: Focus) {
        initialFocus.value = focus
    }
}
