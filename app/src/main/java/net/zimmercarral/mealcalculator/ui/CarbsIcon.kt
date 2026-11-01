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

val CarbsIcon: ImageVector
    @Composable
    get() {
        val current = _carbsIcon
        if (current != null) return current

        return ImageVector.Builder(
            name = "net.zimmercarral.mealcalculator.ui.theme.AppTheme.CarbsIcon",
            defaultWidth = 800.0.dp,
            defaultHeight = 800.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f,
        ).apply {
            // m12 4 -1.15 1.91 C9.95 7.41 9.5 8.17 9.5 9 s.45 1.58 1.35 3.09 l.72 1.2 c.2 .31 .29 .47 .43 .47 s.24 -.16 .43 -.47 l.72 -1.2 c.9 -1.5 1.35 -2.26 1.35 -3.09 s-.45 -1.58 -1.35 -3.09z m0 0 V2 m8.16 10.6 -2.23 -.04 c-1.75 -.03 -2.63 -.04 -3.35 .37 -.72 .42 -1.15 1.18 -2 2.72 l-.68 1.22 c-.18 .32 -.27 .49 -.2 .61 s.26 .13 .63 .13 l1.4 .03 c1.75 .03 2.63 .04 3.35 -.38 .72 -.41 1.15 -1.18 2 -2.71z m0 0 1.73 -1
            path(
                stroke = SolidColor(colorScheme.primary),
                strokeLineWidth = 1.2f
            ) {
                // M 12 4
                moveTo(x = 12.0f, y = 4.0f)
                // l -1.15 1.91
                lineToRelative(dx = -1.15f, dy = 1.91f)
                // C 9.95 7.41 9.5 8.17 9.5 9
                curveTo(
                    x1 = 9.95f,
                    y1 = 7.41f,
                    x2 = 9.5f,
                    y2 = 8.17f,
                    x3 = 9.5f,
                    y3 = 9.0f,
                )
                // s 0.45 1.58 1.35 3.09
                reflectiveCurveToRelative(
                    dx1 = 0.45f,
                    dy1 = 1.58f,
                    dx2 = 1.35f,
                    dy2 = 3.09f,
                )
                // l 0.72 1.2
                lineToRelative(dx = 0.72f, dy = 1.2f)
                // c 0.2 0.31 0.29 0.47 0.43 0.47
                curveToRelative(
                    dx1 = 0.2f,
                    dy1 = 0.31f,
                    dx2 = 0.29f,
                    dy2 = 0.47f,
                    dx3 = 0.43f,
                    dy3 = 0.47f,
                )
                // s 0.24 -0.16 0.43 -0.47
                reflectiveCurveToRelative(
                    dx1 = 0.24f,
                    dy1 = -0.16f,
                    dx2 = 0.43f,
                    dy2 = -0.47f,
                )
                // l 0.72 -1.2
                lineToRelative(dx = 0.72f, dy = -1.2f)
                // c 0.9 -1.5 1.35 -2.26 1.35 -3.09
                curveToRelative(
                    dx1 = 0.9f,
                    dy1 = -1.5f,
                    dx2 = 1.35f,
                    dy2 = -2.26f,
                    dx3 = 1.35f,
                    dy3 = -3.09f,
                )
                // s -0.45 -1.58 -1.35 -3.09z
                reflectiveCurveToRelative(
                    dx1 = -0.45f,
                    dy1 = -1.58f,
                    dx2 = -1.35f,
                    dy2 = -3.09f,
                )
                close()
                // m 0 0
                moveToRelative(dx = 0.0f, dy = 0.0f)
                // V 2
                verticalLineTo(y = 2.0f)
                // m 8.16 10.6
                moveToRelative(dx = 8.16f, dy = 10.6f)
                // l -2.23 -0.04
                lineToRelative(dx = -2.23f, dy = -0.04f)
                // c -1.75 -0.03 -2.63 -0.04 -3.35 0.37
                curveToRelative(
                    dx1 = -1.75f,
                    dy1 = -0.03f,
                    dx2 = -2.63f,
                    dy2 = -0.04f,
                    dx3 = -3.35f,
                    dy3 = 0.37f,
                )
                // c -0.72 0.42 -1.15 1.18 -2 2.72
                curveToRelative(
                    dx1 = -0.72f,
                    dy1 = 0.42f,
                    dx2 = -1.15f,
                    dy2 = 1.18f,
                    dx3 = -2.0f,
                    dy3 = 2.72f,
                )
                // l -0.68 1.22
                lineToRelative(dx = -0.68f, dy = 1.22f)
                // c -0.18 0.32 -0.27 0.49 -0.2 0.61
                curveToRelative(
                    dx1 = -0.18f,
                    dy1 = 0.32f,
                    dx2 = -0.27f,
                    dy2 = 0.49f,
                    dx3 = -0.2f,
                    dy3 = 0.61f,
                )
                // s 0.26 0.13 0.63 0.13
                reflectiveCurveToRelative(
                    dx1 = 0.26f,
                    dy1 = 0.13f,
                    dx2 = 0.63f,
                    dy2 = 0.13f,
                )
                // l 1.4 0.03
                lineToRelative(dx = 1.4f, dy = 0.03f)
                // c 1.75 0.03 2.63 0.04 3.35 -0.38
                curveToRelative(
                    dx1 = 1.75f,
                    dy1 = 0.03f,
                    dx2 = 2.63f,
                    dy2 = 0.04f,
                    dx3 = 3.35f,
                    dy3 = -0.38f,
                )
                // c 0.72 -0.41 1.15 -1.18 2 -2.71z
                curveToRelative(
                    dx1 = 0.72f,
                    dy1 = -0.41f,
                    dx2 = 1.15f,
                    dy2 = -1.18f,
                    dx3 = 2.0f,
                    dy3 = -2.71f,
                )
                close()
                // m 0 0
                moveToRelative(dx = 0.0f, dy = 0.0f)
                // l 1.73 -1
                lineToRelative(dx = 1.73f, dy = -1.0f)
            }
            // m3.84 12.6 2.23 -.04 c1.75 -.03 2.63 -.04 3.35 .37 .72 .42 1.15 1.18 2 2.72 l.68 1.22 c.18 .32 .27 .49 .2 .61 s-.26 .13 -.63 .13 l-1.4 .03 c-1.75 .03 -2.63 .04 -3.35 -.38 -.72 -.41 -1.15 -1.18 -2 -2.71z m0 0 -1.73 -1 M12 15 v7.5
            path(
                stroke = SolidColor(colorScheme.primary),
                strokeLineWidth = 1.2f
            ) {
                // M 3.84 12.6
                moveTo(x = 3.84f, y = 12.6f)
                // l 2.23 -0.04
                lineToRelative(dx = 2.23f, dy = -0.04f)
                // c 1.75 -0.03 2.63 -0.04 3.35 0.37
                curveToRelative(
                    dx1 = 1.75f,
                    dy1 = -0.03f,
                    dx2 = 2.63f,
                    dy2 = -0.04f,
                    dx3 = 3.35f,
                    dy3 = 0.37f,
                )
                // c 0.72 0.42 1.15 1.18 2 2.72
                curveToRelative(
                    dx1 = 0.72f,
                    dy1 = 0.42f,
                    dx2 = 1.15f,
                    dy2 = 1.18f,
                    dx3 = 2.0f,
                    dy3 = 2.72f,
                )
                // l 0.68 1.22
                lineToRelative(dx = 0.68f, dy = 1.22f)
                // c 0.18 0.32 0.27 0.49 0.2 0.61
                curveToRelative(
                    dx1 = 0.18f,
                    dy1 = 0.32f,
                    dx2 = 0.27f,
                    dy2 = 0.49f,
                    dx3 = 0.2f,
                    dy3 = 0.61f,
                )
                // s -0.26 0.13 -0.63 0.13
                reflectiveCurveToRelative(
                    dx1 = -0.26f,
                    dy1 = 0.13f,
                    dx2 = -0.63f,
                    dy2 = 0.13f,
                )
                // l -1.4 0.03
                lineToRelative(dx = -1.4f, dy = 0.03f)
                // c -1.75 0.03 -2.63 0.04 -3.35 -0.38
                curveToRelative(
                    dx1 = -1.75f,
                    dy1 = 0.03f,
                    dx2 = -2.63f,
                    dy2 = 0.04f,
                    dx3 = -3.35f,
                    dy3 = -0.38f,
                )
                // c -0.72 -0.41 -1.15 -1.18 -2 -2.71z
                curveToRelative(
                    dx1 = -0.72f,
                    dy1 = -0.41f,
                    dx2 = -1.15f,
                    dy2 = -1.18f,
                    dx3 = -2.0f,
                    dy3 = -2.71f,
                )
                close()
                // m 0 0
                moveToRelative(dx = 0.0f, dy = 0.0f)
                // l -1.73 -1
                lineToRelative(dx = -1.73f, dy = -1.0f)
                // M 12 15
                moveTo(x = 12.0f, y = 15.0f)
                // v 7.5
                verticalLineToRelative(dy = 7.5f)
            }
        }.build().also { _carbsIcon = it }
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
                imageVector = CarbsIcon,
                contentDescription = null,
                modifier = Modifier
                    .width((800.0).dp)
                    .height((800.0).dp)

            )
        }
    }
}

@Suppress("ObjectPropertyName")
private var _carbsIcon: ImageVector? = null
