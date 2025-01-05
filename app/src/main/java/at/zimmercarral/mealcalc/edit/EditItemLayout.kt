package at.zimmercarral.mealcalc.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.zimmercarral.mealcalc.R
import at.zimmercarral.mealcalc.data.db.model.FoodItem
import at.zimmercarral.mealcalc.ui.theme.robotoFamily
import kotlinx.coroutines.android.awaitFrame

@Composable
fun EditItemLayout(
    navController: NavController,
    editItemDialogViewModel: EditItemDialogViewModel,
    onDismiss: () -> Unit,
    onAddOrUpdateItem: (FoodItem) -> Unit,
    updateMode: Boolean = false
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val title =
        if (editItemDialogViewModel.inEditMode.value)
            stringResource(R.string.edit_item_title)
        else stringResource(
            R.string.add_item_title)
    Surface(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .background(color = colorScheme.surfaceContainer)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                    })
                },
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 10.dp, bottom = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Back button in the top left corner
                IconButton(
                    modifier = Modifier.size(24.dp),
                    onClick = {
                        editItemDialogViewModel.clear()
                        keyboardController?.hide()
                        onDismiss()
                    }
                ) {
                    Icon(Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = stringResource(R.string.back),
                        modifier = Modifier.fillMaxHeight(),
                        tint = colorScheme.primary)
                }
                Text(fontFamily = robotoFamily,
                    fontSize = TextUnit(19f, TextUnitType.Sp),
                    fontWeight = FontWeight.Normal,
                    maxLines = 1,
                    text = title)
                Spacer(modifier = Modifier.size(24.dp))
            }
            Row(horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                HorizontalDivider(modifier = Modifier
                    .fillMaxWidth(0.45f)
                )
            }
            ItemName(editItemDialogViewModel)
            NutritionalValues(editItemDialogViewModel)
            AddOrUpdateButton(editItemDialogViewModel, onAddOrUpdateItem)
            CalculatedValuesView(editItemDialogViewModel)
        }
    }

    LaunchedEffect(Unit) {
        awaitFrame()
        editItemDialogViewModel.requestFocus(editItemDialogViewModel.initialFocus.value)
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
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .padding(start = 30.dp)
                .height(55.dp),
            shape = RoundedCornerShape(15.dp),
            onClick = {
                onAddOrUpdateItem(editItemDialogViewModel.getFoodItem())
                editItemDialogViewModel.saveItem()
                editItemDialogViewModel.clear()
                editItemDialogViewModel.setVisible(false)
            },
        ) {
            Text(
                text = buttonText,
                fontFamily = robotoFamily,
                fontWeight = FontWeight.Medium,
                fontSize = TextUnit(18f, TextUnitType.Sp),
            )
        }
    }
}




