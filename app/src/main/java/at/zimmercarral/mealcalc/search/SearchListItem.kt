package at.zimmercarral.mealcalc.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import at.zimmercarral.mealcalc.R
import at.zimmercarral.mealcalc.data.db.model.FoodItem
import at.zimmercarral.mealcalc.ui.theme.AppTheme

@Composable
fun SearchListItem(
    item: FoodItem,
    searchViewModel: SearchViewModel,
    backgroundColor: Color,
    onEditItem: (FoodItem) -> Unit,
    onAddItem: (FoodItem) -> Unit
) {
    SearchListItem(
        item = item,
        backgroundColor = backgroundColor,
        onEditItem = onEditItem,
        onAddItem = onAddItem,
        onAction = {
            searchViewModel.setSearchQuery("")
            searchViewModel.setExpanded(false)
        }
    )
}

@Composable
fun SearchListItem(
    item: FoodItem,
    backgroundColor: Color,
    onEditItem: (FoodItem) -> Unit,
    onAddItem: (FoodItem) -> Unit,
    onAction: () -> Unit
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(8.dp))
        .background(color = backgroundColor)
        .padding(bottom = 6.dp,
            start = 8.dp,
            end = 5.dp), // Re-added small start padding for individual items
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically // Align items vertically
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .clickable { // Make the entire item name and amount clickable for editing
                    onAction()
                    onEditItem(item)
                },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            var itemName = item.name.substring(0, item.name.length.coerceAtMost(25)).trim()

            if (item.name.length > 25) {
                itemName += "..."
            }
            Text(
                text = itemName,
                maxLines = 1,
                color = colorScheme.onSurface,
                modifier = Modifier
                    .padding(top = 8.dp, start = 5.dp, end = 5.dp))
            Text(
                text = item.amount.toString() + "gr",
                maxLines = 1,
                color = colorScheme.onSurface,
                modifier = Modifier
                    .padding(top = 8.dp)
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                modifier = Modifier
                    .size(48.dp)
                    .padding(top = 5.dp, end = 5.dp)
                    .clickable {
                        onAction()
                        onAddItem(item)
                    },
                imageVector = Icons.Rounded.Add,
                contentDescription = stringResource(R.string.add_without_editing),
                tint = colorScheme.primary,
            )
        }
    }
}

@Preview
@PreviewLightDark
@Composable
private fun SearchListItemPreview() {
    val item = FoodItem(
        uuid = "123",
        name = "Apfelpfannkuchen mit Schlagobers",
        fatPer100g = 0f,
        carbsPer100g = 12f,
        proteinPer100g = 0f,
        amount = 100,
        lastModifiedAt = null,
        isDeleted = false,
    )
    AppTheme {
        SearchListItem(
            item = item,
            backgroundColor = colorScheme.surfaceVariant,
            onEditItem = {},
            onAddItem = {},
            onAction = {}
        )
    }
}
