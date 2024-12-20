package at.zimmercarral.mealcalc.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.zimmercarral.mealcalc.data.db.FoodItemEvent
import at.zimmercarral.mealcalc.data.db.FoodItemsState
import at.zimmercarral.mealcalc.data.db.dao.FoodItemDao
import at.zimmercarral.mealcalc.data.db.model.FoodItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class FoodItemViewModel(
    private val foodItemDao: FoodItemDao
) : ViewModel() {

    private val _state = MutableStateFlow(FoodItemsState())
    val state = _state

    fun onEvent(event: FoodItemEvent) {
        when (event) {
            is FoodItemEvent.SaveFoodItem -> {
                val uuid = _state.value.uuid
                val name = _state.value.name
                val fat = _state.value.fat
                val carbs = _state.value.carbs
                val protein = _state.value.protein
                val amount = _state.value.amount
                if (name.isBlank()) {
                    return
                }
                val item =
                    FoodItem(uuid, name, fat, carbs, protein, amount, LocalDateTime.now(), false)
                viewModelScope.launch {
                    foodItemDao.insert(item)
                }
            }

            is FoodItemEvent.SetUuid -> {
                _state.update {
                    it.copy(
                        uuid = event.uuid
                    )
                }
            }

            is FoodItemEvent.SetName -> {
                _state.update {
                    it.copy(
                        name = event.name
                    )
                }
            }

            is FoodItemEvent.SetCarbs -> {
                _state.update {
                    it.copy(
                        carbs = event.carbs
                    )
                }
            }

            is FoodItemEvent.SetFat -> {
                _state.update {
                    it.copy(
                        fat = event.fat
                    )
                }
            }

            is FoodItemEvent.SetProtein -> {
                _state.update {
                    it.copy(
                        protein = event.protein
                    )
                }
            }

            is FoodItemEvent.SetAmount -> {
                _state.update {
                    it.copy(
                        amount = event.amount
                    )
                }
            }

            is FoodItemEvent.SetLastModifiedAt -> {
                _state.update {
                    it.copy(
                        lastModifiedAt = event.lastModifiedAt
                    )
                }
            }

            is FoodItemEvent.SetDeleted -> {
                _state.update {
                    it.copy(
                        isDeleted = event.isDeleted
                    )
                }
            }
        }
    }
}