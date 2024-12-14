package at.zimmercarral.mealcalc.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Save
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import at.zimmercarral.mealcalc.R
import com.example.compose.AppTheme

@Composable
fun ActionButton(
    description: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(icon,
            contentDescription = description,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent),
            tint = colorScheme.primary)
    }
}

@Preview
@PreviewLightDark
@Composable
private fun ActionButtonPreview() {
    AppTheme() {
        ActionButton(stringResource(id = R.string.save_item),
            Icons.Rounded.Save,
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 0.dp)
                .size(70.dp),
            onClick = { /*TODO*/ })
    }
}