package net.zimmercarral.mealcalculator.edit

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import net.zimmercarral.mealcalculator.data.db.model.FoodItem
import net.zimmercarral.mealcalculator.data.repo.FoodItemRepository
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.UUID

class EditItemDialogViewModel(
    private val repository: FoodItemRepository
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
        val currentText = amount.value.text
        if (currentText.isNotEmpty()) {
            amount.value = amount.value.copy(selection = TextRange(0, currentText.length))
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
        val item = getFoodItem()
        viewModelScope.launch {
            repository.upsert(item)
        }
    }

    private fun zeroFloatIfEmpty(value: String): Float {
        if (value.isEmpty()) {
            return 0f
        }
        return value.toFloat()
    }

    private fun zeroIntIfEmpty(value: String): Int {
        val numberValue = value.replace(Regex("[^0-9]"), "")
        if (numberValue.isEmpty()) {
            return 0
        }
        return numberValue.toInt()
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
        var digitsOnly = amount.text.replace(Regex("[^0-9]"), "")
        if (digitsOnly.length > 5) {
            digitsOnly = digitsOnly.substring(0, 5)
        }
        // Only update if the filtered text is different from current text
        // This prevents unnecessary recomposition
        if (digitsOnly != this.amount.value.text) {
            this.amount.value = TextFieldValue(
                text = digitsOnly,
                selection = TextRange(digitsOnly.length) // Place cursor at end
            )
        } else {
            // If text hasn't changed, preserve the original selection/cursor position
            this.amount.value = amount.copy(text = digitsOnly)
        }
    }

    fun setInitialFocus(focus: Focus) {
        initialFocus.value = focus
    }

    companion object {
        fun factory(repository: FoodItemRepository) = viewModelFactory {
            initializer { EditItemDialogViewModel(repository) }
        }
    }
}
