package at.zimmercarral.mealcalc.item

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import at.zimmercarral.mealcalc.R
import at.zimmercarral.mealcalc.data.db.model.FoodItem

@Composable
fun EditItemDialog(
    editItemDialogViewModel: EditItemDialogViewModel,
    onDismiss: () -> Unit,
    onAddOrUpdateItem: (FoodItem) -> Unit,
) {
    if (editItemDialogViewModel.isVisible.value) {
        val focusManager = LocalFocusManager.current
        Dialog(
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
                usePlatformDefaultWidth = false
            ),
            onDismissRequest = { onDismiss() }
        ) {
            Surface(
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .fillMaxWidth(0.93f)
            ) {
                Column(
                    modifier = Modifier
                        .background(color = colorScheme.surface)
                        .pointerInput(Unit) {
                            detectTapGestures(onTap = {
                                focusManager.clearFocus()
                            })
                        },
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                ) {
                    Spacer(modifier = Modifier.height(10.dp))
                    ItemName(editItemDialogViewModel.name.value, editItemDialogViewModel::setName)
                    NutritionalValues(editItemDialogViewModel)
                    CalculatedValues(editItemDialogViewModel)
                    AddOrUpdateButton(editItemDialogViewModel, onAddOrUpdateItem)
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
        LaunchedEffect(Unit) {
            editItemDialogViewModel.requestFocus(editItemDialogViewModel.initialFocus.value)
        }
    }
}


@Composable
fun AddOrUpdateButton(
    editItemDialogViewModel: EditItemDialogViewModel,
    onAddOrUpdateItem: (FoodItem) -> Unit,
) {
    Column(modifier = Modifier
        .padding(top = 10.dp, start = 50.dp),
        verticalArrangement = Arrangement.Center
    ) {
        var buttonText = stringResource(R.string.add_item)
        if (editItemDialogViewModel.inEditMode.value) {
            buttonText = stringResource(R.string.update_item)
        }
        Button(
            modifier = Modifier.fillMaxWidth(0.9f),
            onClick = {
                onAddOrUpdateItem(editItemDialogViewModel.getFoodItem())
            },
        ) {
            Text(text = buttonText)
        }
    }
}



