package at.zimmercarral.mealcalc

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Save
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun TopActionIcons(currentItemViewModel: CurrentItemViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        shape = RoundedCornerShape(30.dp),
        elevation = CardDefaults.cardElevation(12.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Color.LightGray.copy(alpha = 0.1f)),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            ActionButton(stringResource(id = R.string.clear_values),
                Icons.Rounded.Clear, color = Color(120, 0, 0, 0),
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 0.dp)
                    .size(70.dp),
                onClick = {
                    currentItemViewModel.clear()
                    currentItemViewModel.setInUpdateMode(false)
                    currentItemViewModel.requestFatFocus()
                })
            ActionButton(stringResource(id = R.string.save_item),
                Icons.Rounded.Save, color = Color(0, 120, 0, 0),
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 0.dp)
                    .size(70.dp),
                onClick = { /*TODO*/ })

            ActionButton(stringResource(id = R.string.search_item),
                Icons.Rounded.Search, color = Color(0, 0, 120, 0),
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 0.dp)
                    .size(70.dp),
                onClick = { /*TODO*/ })
        }
    }
}

