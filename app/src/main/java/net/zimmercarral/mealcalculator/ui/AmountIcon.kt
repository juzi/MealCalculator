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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.zimmercarral.mealcalculator.ui.theme.AppTheme

val AmountIcon: ImageVector
    @Composable
    get() {
        val current = _amountIcon
        if (current != null) return current

        return ImageVector.Builder(
            name = "AppTheme.AmountIcon",
            defaultWidth = 800.0.dp,
            defaultHeight = 800.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f,
        ).apply {
            // M16.5 5 h3.9 A.6 .6 0 0 1 21 5.6 v14.8 a.6 .6 0 0 1 -.6 .6 H3.6 A.6 .6 0 0 1 3 20.4 V5.6 A.6 .6 0 0 1 3.6 5 h3.9
            path(
                stroke = SolidColor(colorScheme.primary),
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round,
                strokeLineWidth = 1.5f,
            ) {
                // M 16.5 5
                moveTo(x = 16.5f, y = 5.0f)
                // h 3.9
                horizontalLineToRelative(dx = 3.9f)
                // A 0.6 0.6 0 0 1 21 5.6
                arcTo(
                    horizontalEllipseRadius = 0.6f,
                    verticalEllipseRadius = 0.6f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    x1 = 21.0f,
                    y1 = 5.6f,
                )
                // v 14.8
                verticalLineToRelative(dy = 14.8f)
                // a 0.6 0.6 0 0 1 -0.6 0.6
                arcToRelative(
                    a = 0.6f,
                    b = 0.6f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -0.6f,
                    dy1 = 0.6f,
                )
                // H 3.6
                horizontalLineTo(x = 3.6f)
                // A 0.6 0.6 0 0 1 3 20.4
                arcTo(
                    horizontalEllipseRadius = 0.6f,
                    verticalEllipseRadius = 0.6f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    x1 = 3.0f,
                    y1 = 20.4f,
                )
                // V 5.6
                verticalLineTo(y = 5.6f)
                // A 0.6 0.6 0 0 1 3.6 5
                arcTo(
                    horizontalEllipseRadius = 0.6f,
                    verticalEllipseRadius = 0.6f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    x1 = 3.6f,
                    y1 = 5.0f,
                )
                // h 3.9
                horizontalLineToRelative(dx = 3.9f)
            }
            // m16.28 6.33 .2 -1.23 V4.9 l-.2 -1.23 A2 2 0 0 0 14.3 2 H9.69 a2 2 0 0 0 -1.97 1.67 L7.52 4.9 v.2 l.2 1.23 A2 2 0 0 0 9.7 8 h4.62 a2 2 0 0 0 1.97 -1.67 M12 8 l-1 -2.5 M7 17 h10
            path(
                stroke = SolidColor(colorScheme.primary),
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round,
                strokeLineWidth = 1.5f,
            ) {
                // M 16.28 6.33
                moveTo(x = 16.28f, y = 6.33f)
                // l 0.2 -1.23
                lineToRelative(dx = 0.2f, dy = -1.23f)
                // V 4.9
                verticalLineTo(y = 4.9f)
                // l -0.2 -1.23
                lineToRelative(dx = -0.2f, dy = -1.23f)
                // A 2 2 0 0 0 14.3 2
                arcTo(
                    horizontalEllipseRadius = 2.0f,
                    verticalEllipseRadius = 2.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    x1 = 14.3f,
                    y1 = 2.0f,
                )
                // H 9.69
                horizontalLineTo(x = 9.69f)
                // a 2 2 0 0 0 -1.97 1.67
                arcToRelative(
                    a = 2.0f,
                    b = 2.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -1.97f,
                    dy1 = 1.67f,
                )
                // L 7.52 4.9
                lineTo(x = 7.52f, y = 4.9f)
                // v 0.2
                verticalLineToRelative(dy = 0.2f)
                // l 0.2 1.23
                lineToRelative(dx = 0.2f, dy = 1.23f)
                // A 2 2 0 0 0 9.7 8
                arcTo(
                    horizontalEllipseRadius = 2.0f,
                    verticalEllipseRadius = 2.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    x1 = 9.7f,
                    y1 = 8.0f,
                )
                // h 4.62
                horizontalLineToRelative(dx = 4.62f)
                // a 2 2 0 0 0 1.97 -1.67
                arcToRelative(
                    a = 2.0f,
                    b = 2.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 1.97f,
                    dy1 = -1.67f,
                )
                // M 12 8
                moveTo(x = 12.0f, y = 8.0f)
                // l -1 -2.5
                lineToRelative(dx = -1.0f, dy = -2.5f)
                // M 7 17
                moveTo(x = 7.0f, y = 17.0f)
                // h 10
                horizontalLineToRelative(dx = 10.0f)
            }
        }.build().also { _amountIcon = it }
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
                imageVector = AmountIcon,
                contentDescription = null,
                modifier = Modifier
                    .width((800.0).dp)
                    .height((800.0).dp),
            )
        }
    }
}

@Suppress("ObjectPropertyName")
private var _amountIcon: ImageVector? = null
