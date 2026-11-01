package net.zimmercarral.mealcalculator.meal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import net.zimmercarral.mealcalculator.R
import kotlinx.coroutines.CoroutineScope

@Composable
fun MealActionButtons(
    mealViewModel: MealViewModel,
    scope: CoroutineScope = rememberCoroutineScope(),
    mealDeleted: String = stringResource(R.string.meal_deleted),
    undo: String = stringResource(R.string.undo),
    onAddNewItem: () -> Unit = {}
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 20.dp),
        horizontalArrangement = Arrangement.End
    ) {
        MealDeleteButton(mealViewModel, scope, mealDeleted, undo)

        FloatingActionButton(
            onClick = onAddNewItem,
            modifier = Modifier
                .padding(end = 25.dp),
            containerColor = colorScheme.primary,
            contentColor = colorScheme.onPrimary,

            ) {
            Icon(modifier = Modifier.size(30.dp),
                imageVector = Icons.Rounded.Add,
                contentDescription = "Add new item"
            )
        }
    }
}
