package net.zimmercarral.mealcalculator.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.zimmercarral.mealcalculator.ui.theme.AppTheme

val ProteinIcon: ImageVector
    @Composable
    get() {
        val current = _proteinIcon
        if (current != null) return current

        return ImageVector.Builder(
            name = "AppTheme.ProteinIcon",
            defaultWidth = 800.0.dp,
            defaultHeight = 800.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f,
        ).apply {
            // M9.3 5.68 a3.35 3.35 0 0 1 5.4 0 l.77 1.05 a16 16 0 0 1 3.01 7.7 5.4 5.4 0 0 1 -3.9 5.83 l-.77 .22 a6.6 6.6 0 0 1 -3.62 0 l-.77 -.22 a5.4 5.4 0 0 1 -3.9 -5.83 16 16 0 0 1 3.01 -7.7z
            path(
                stroke = SolidColor(colorScheme.primary),
                strokeLineWidth = 1.2f
            ) {
                // M 9.3 5.68
                moveTo(x = 9.3f, y = 5.68f)
                // a 3.35 3.35 0 0 1 5.4 0
                arcToRelative(
                    a = 3.35f,
                    b = 3.35f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 5.4f,
                    dy1 = 0.0f,
                )
                // l 0.77 1.05
                lineToRelative(dx = 0.77f, dy = 1.05f)
                // a 16 16 0 0 1 3.01 7.7
                arcToRelative(
                    a = 16.0f,
                    b = 16.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 3.01f,
                    dy1 = 7.7f,
                )
                // a 5.4 5.4 0 0 1 -3.9 5.83
                arcToRelative(
                    a = 5.4f,
                    b = 5.4f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -3.9f,
                    dy1 = 5.83f,
                )
                // l -0.77 0.22
                lineToRelative(dx = -0.77f, dy = 0.22f)
                // a 6.6 6.6 0 0 1 -3.62 0
                arcToRelative(
                    a = 6.6f,
                    b = 6.6f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -3.62f,
                    dy1 = 0.0f,
                )
                // l -0.77 -0.22
                lineToRelative(dx = -0.77f, dy = -0.22f)
                // a 5.4 5.4 0 0 1 -3.9 -5.83
                arcToRelative(
                    a = 5.4f,
                    b = 5.4f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -3.9f,
                    dy1 = -5.83f,
                )
                // a 16 16 0 0 1 3.01 -7.7z
                arcToRelative(
                    a = 16.0f,
                    b = 16.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 3.01f,
                    dy1 = -7.7f,
                )
                close()
            }
        }.build().also { _proteinIcon = it }
    }

@Preview
@Composable
private fun IconPreview() {
    AppTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                imageVector = ProteinIcon,
                contentDescription = null,
                modifier = Modifier
                    .width((800.0).dp)
                    .height((800.0).dp),
            )
        }
    }
}

@Suppress("ObjectPropertyName")
private var _proteinIcon: ImageVector? = null
