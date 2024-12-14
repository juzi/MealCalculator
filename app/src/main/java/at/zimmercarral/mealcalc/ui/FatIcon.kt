package at.zimmercarral.mealcalc.ui

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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import at.zimmercarral.mealcalc.ui.theme.AppTheme

val FatIcon: ImageVector
    @Composable
    get() {
        val current = _fatIcon
        if (current != null) return current

        return ImageVector.Builder(
            name = "AppTheme.FatIcon",
            defaultWidth = 800.0.dp,
            defaultHeight = 800.0.dp,
            viewportWidth = 511.999f,
            viewportHeight = 511.999f,
        ).apply {
            // m161.25 134.56 -.2 -.2 a12.39 12.39 0 0 0 -17.5 17.52 l.19 .2 a12.35 12.35 0 0 0 17.51 0 12.4 12.4 0 0 0 0 -17.53 m38.62 303.13 c-60.94 0 -110.52 -54.74 -110.52 -122.03 0 -34.05 18.03 -81.13 50.77 -132.59 a12.38 12.38 0 1 0 -20.9 -13.3 c-35.75 56.18 -54.64 106.63 -54.64 145.9 0 80.94 60.7 146.79 135.3 146.79 a12.39 12.39 0 0 0 0 -24.77
            path(
                fill = SolidColor(colorScheme.primary),
            ) {
                // M 161.25 134.56
                moveTo(x = 161.25f, y = 134.56f)
                // l -0.2 -0.2
                lineToRelative(dx = -0.2f, dy = -0.2f)
                // a 12.39 12.39 0 0 0 -17.5 17.52
                arcToRelative(
                    a = 12.39f,
                    b = 12.39f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -17.5f,
                    dy1 = 17.52f,
                )
                // l 0.19 0.2
                lineToRelative(dx = 0.19f, dy = 0.2f)
                // a 12.35 12.35 0 0 0 17.51 0
                arcToRelative(
                    a = 12.35f,
                    b = 12.35f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 17.51f,
                    dy1 = 0.0f,
                )
                // a 12.4 12.4 0 0 0 0 -17.53
                arcToRelative(
                    a = 12.4f,
                    b = 12.4f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 0.0f,
                    dy1 = -17.53f,
                )
                // m 38.62 303.13
                moveToRelative(dx = 38.62f, dy = 303.13f)
                // c -60.94 0 -110.52 -54.74 -110.52 -122.03
                curveToRelative(
                    dx1 = -60.94f,
                    dy1 = 0.0f,
                    dx2 = -110.52f,
                    dy2 = -54.74f,
                    dx3 = -110.52f,
                    dy3 = -122.03f,
                )
                // c 0 -34.05 18.03 -81.13 50.77 -132.59
                curveToRelative(
                    dx1 = 0.0f,
                    dy1 = -34.05f,
                    dx2 = 18.03f,
                    dy2 = -81.13f,
                    dx3 = 50.77f,
                    dy3 = -132.59f,
                )
                // a 12.38 12.38 0 1 0 -20.9 -13.3
                arcToRelative(
                    a = 12.38f,
                    b = 12.38f,
                    theta = 0.0f,
                    isMoreThanHalf = true,
                    isPositiveArc = false,
                    dx1 = -20.9f,
                    dy1 = -13.3f,
                )
                // c -35.75 56.18 -54.64 106.63 -54.64 145.9
                curveToRelative(
                    dx1 = -35.75f,
                    dy1 = 56.18f,
                    dx2 = -54.64f,
                    dy2 = 106.63f,
                    dx3 = -54.64f,
                    dy3 = 145.9f,
                )
                // c 0 80.94 60.7 146.79 135.3 146.79
                curveToRelative(
                    dx1 = 0.0f,
                    dy1 = 80.94f,
                    dx2 = 60.7f,
                    dy2 = 146.79f,
                    dx3 = 135.3f,
                    dy3 = 146.79f,
                )
                // a 12.39 12.39 0 0 0 0 -24.77
                arcToRelative(
                    a = 12.39f,
                    b = 12.39f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 0.0f,
                    dy1 = -24.77f,
                )
            }
            // M375.23 80.48 a12.4 12.4 0 0 0 -17.16 .02 c-1.97 1.9 -21.53 20.9 -44.42 49.27 C265.57 58.44 210.89 5.79 208.44 3.44 a12.4 12.4 0 0 0 -17.15 .02 c-1.8 1.72 -44.38 42.89 -87.62 101.71 -58.81 80 -88.63 150.82 -88.63 210.49 C15.04 423.92 97.96 512 199.87 512 c58.3 0 112.96 -29.2 147.83 -78.5 q9.3 1.44 18.96 1.46 c71.85 0 130.3 -61.98 130.3 -138.17 0 -98.66 -116.76 -211.55 -121.73 -216.31 M199.87 487.23 c-88.26 0 -160.06 -76.96 -160.06 -171.57 0 -113.22 125.75 -250.4 160.09 -285.73 18 18.4 61.01 64.55 97.78 120.55 a478 478 0 0 0 -22.55 33.48 c-23.27 37.85 -36.14 72.39 -38.4 103.16 l-.05 .72 q-.12 1.8 -.2 3.6 0 .49 -.04 .99 a131 131 0 0 0 .04 9.86 l.02 .71 a145 145 0 0 0 .34 5.6 l.01 .1 a144 144 0 0 0 11.2 45.26 l.03 .07 a142 142 0 0 0 4.27 9.01 A138 138 0 0 0 265.58 384 a133 133 0 0 0 38.73 34.1 l.45 .26 2.39 1.34 .89 .48 q1.99 1.06 4 2.06 .59 .28 1.16 .56 a129 129 0 0 0 5.42 2.44 q.94 .4 1.9 .78 l1.44 .59 c-30.28 38.23 -74.85 60.63 -122.09 60.63 m166.8 -77.04 a99 99 0 0 1 -25.82 -3.44 l-1.34 -.37 a98 98 0 0 1 -2.77 -.84 l-1.38 -.44 a100 100 0 0 1 -7.5 -2.85 l-1.5 -.66 a101 101 0 0 1 -6.62 -3.24 l-.98 -.53 a104 104 0 0 1 -7.45 -4.51 l-.14 -.1 c-27.76 -18.57 -46.66 -50.53 -49.63 -86.47 v-.07 a120 120 0 0 1 -.3 -4.7 l-.01 -.54 q-.1 -2.3 -.1 -4.63 0 -2.04 .09 -4.08 l.05 -1.07 .21 -3.3 .07 -.84 c3.11 -35.76 23.36 -74.39 44.73 -106.1 22.5 -33.27 47.57 -60.94 60.4 -74.35 26.55 27.66 105.5 116.1 105.5 189.74 .01 62.52 -47.33 113.39 -105.52 113.39
            path(
                fill = SolidColor(colorScheme.primary),
            ) {
                // M 375.23 80.48
                moveTo(x = 375.23f, y = 80.48f)
                // a 12.4 12.4 0 0 0 -17.16 0.02
                arcToRelative(
                    a = 12.4f,
                    b = 12.4f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -17.16f,
                    dy1 = 0.02f,
                )
                // c -1.97 1.9 -21.53 20.9 -44.42 49.27
                curveToRelative(
                    dx1 = -1.97f,
                    dy1 = 1.9f,
                    dx2 = -21.53f,
                    dy2 = 20.9f,
                    dx3 = -44.42f,
                    dy3 = 49.27f,
                )
                // C 265.57 58.44 210.89 5.79 208.44 3.44
                curveTo(
                    x1 = 265.57f,
                    y1 = 58.44f,
                    x2 = 210.89f,
                    y2 = 5.79f,
                    x3 = 208.44f,
                    y3 = 3.44f,
                )
                // a 12.4 12.4 0 0 0 -17.15 0.02
                arcToRelative(
                    a = 12.4f,
                    b = 12.4f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -17.15f,
                    dy1 = 0.02f,
                )
                // c -1.8 1.72 -44.38 42.89 -87.62 101.71
                curveToRelative(
                    dx1 = -1.8f,
                    dy1 = 1.72f,
                    dx2 = -44.38f,
                    dy2 = 42.89f,
                    dx3 = -87.62f,
                    dy3 = 101.71f,
                )
                // c -58.81 80 -88.63 150.82 -88.63 210.49
                curveToRelative(
                    dx1 = -58.81f,
                    dy1 = 80.0f,
                    dx2 = -88.63f,
                    dy2 = 150.82f,
                    dx3 = -88.63f,
                    dy3 = 210.49f,
                )
                // C 15.04 423.92 97.96 512 199.87 512
                curveTo(
                    x1 = 15.04f,
                    y1 = 423.92f,
                    x2 = 97.96f,
                    y2 = 512.0f,
                    x3 = 199.87f,
                    y3 = 512.0f,
                )
                // c 58.3 0 112.96 -29.2 147.83 -78.5
                curveToRelative(
                    dx1 = 58.3f,
                    dy1 = 0.0f,
                    dx2 = 112.96f,
                    dy2 = -29.2f,
                    dx3 = 147.83f,
                    dy3 = -78.5f,
                )
                // q 9.3 1.44 18.96 1.46
                quadToRelative(
                    dx1 = 9.3f,
                    dy1 = 1.44f,
                    dx2 = 18.96f,
                    dy2 = 1.46f,
                )
                // c 71.85 0 130.3 -61.98 130.3 -138.17
                curveToRelative(
                    dx1 = 71.85f,
                    dy1 = 0.0f,
                    dx2 = 130.3f,
                    dy2 = -61.98f,
                    dx3 = 130.3f,
                    dy3 = -138.17f,
                )
                // c 0 -98.66 -116.76 -211.55 -121.73 -216.31
                curveToRelative(
                    dx1 = 0.0f,
                    dy1 = -98.66f,
                    dx2 = -116.76f,
                    dy2 = -211.55f,
                    dx3 = -121.73f,
                    dy3 = -216.31f,
                )
                // M 199.87 487.23
                moveTo(x = 199.87f, y = 487.23f)
                // c -88.26 0 -160.06 -76.96 -160.06 -171.57
                curveToRelative(
                    dx1 = -88.26f,
                    dy1 = 0.0f,
                    dx2 = -160.06f,
                    dy2 = -76.96f,
                    dx3 = -160.06f,
                    dy3 = -171.57f,
                )
                // c 0 -113.22 125.75 -250.4 160.09 -285.73
                curveToRelative(
                    dx1 = 0.0f,
                    dy1 = -113.22f,
                    dx2 = 125.75f,
                    dy2 = -250.4f,
                    dx3 = 160.09f,
                    dy3 = -285.73f,
                )
                // c 18 18.4 61.01 64.55 97.78 120.55
                curveToRelative(
                    dx1 = 18.0f,
                    dy1 = 18.4f,
                    dx2 = 61.01f,
                    dy2 = 64.55f,
                    dx3 = 97.78f,
                    dy3 = 120.55f,
                )
                // a 478 478 0 0 0 -22.55 33.48
                arcToRelative(
                    a = 478.0f,
                    b = 478.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -22.55f,
                    dy1 = 33.48f,
                )
                // c -23.27 37.85 -36.14 72.39 -38.4 103.16
                curveToRelative(
                    dx1 = -23.27f,
                    dy1 = 37.85f,
                    dx2 = -36.14f,
                    dy2 = 72.39f,
                    dx3 = -38.4f,
                    dy3 = 103.16f,
                )
                // l -0.05 0.72
                lineToRelative(dx = -0.05f, dy = 0.72f)
                // q -0.12 1.8 -0.2 3.6
                quadToRelative(
                    dx1 = -0.12f,
                    dy1 = 1.8f,
                    dx2 = -0.2f,
                    dy2 = 3.6f,
                )
                // q 0 0.49 -0.04 0.99
                quadToRelative(
                    dx1 = 0.0f,
                    dy1 = 0.49f,
                    dx2 = -0.04f,
                    dy2 = 0.99f,
                )
                // a 131 131 0 0 0 0.04 9.86
                arcToRelative(
                    a = 131.0f,
                    b = 131.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 0.04f,
                    dy1 = 9.86f,
                )
                // l 0.02 0.71
                lineToRelative(dx = 0.02f, dy = 0.71f)
                // a 145 145 0 0 0 0.34 5.6
                arcToRelative(
                    a = 145.0f,
                    b = 145.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 0.34f,
                    dy1 = 5.6f,
                )
                // l 0.01 0.1
                lineToRelative(dx = 0.01f, dy = 0.1f)
                // a 144 144 0 0 0 11.2 45.26
                arcToRelative(
                    a = 144.0f,
                    b = 144.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 11.2f,
                    dy1 = 45.26f,
                )
                // l 0.03 0.07
                lineToRelative(dx = 0.03f, dy = 0.07f)
                // a 142 142 0 0 0 4.27 9.01
                arcToRelative(
                    a = 142.0f,
                    b = 142.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 4.27f,
                    dy1 = 9.01f,
                )
                // A 138 138 0 0 0 265.58 384
                arcTo(
                    horizontalEllipseRadius = 138.0f,
                    verticalEllipseRadius = 138.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    x1 = 265.58f,
                    y1 = 384.0f,
                )
                // a 133 133 0 0 0 38.73 34.1
                arcToRelative(
                    a = 133.0f,
                    b = 133.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 38.73f,
                    dy1 = 34.1f,
                )
                // l 0.45 0.26
                lineToRelative(dx = 0.45f, dy = 0.26f)
                // l 2.39 1.34
                lineToRelative(dx = 2.39f, dy = 1.34f)
                // l 0.89 0.48
                lineToRelative(dx = 0.89f, dy = 0.48f)
                // q 1.99 1.06 4 2.06
                quadToRelative(
                    dx1 = 1.99f,
                    dy1 = 1.06f,
                    dx2 = 4.0f,
                    dy2 = 2.06f,
                )
                // q 0.59 0.28 1.16 0.56
                quadToRelative(
                    dx1 = 0.59f,
                    dy1 = 0.28f,
                    dx2 = 1.16f,
                    dy2 = 0.56f,
                )
                // a 129 129 0 0 0 5.42 2.44
                arcToRelative(
                    a = 129.0f,
                    b = 129.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 5.42f,
                    dy1 = 2.44f,
                )
                // q 0.94 0.4 1.9 0.78
                quadToRelative(
                    dx1 = 0.94f,
                    dy1 = 0.4f,
                    dx2 = 1.9f,
                    dy2 = 0.78f,
                )
                // l 1.44 0.59
                lineToRelative(dx = 1.44f, dy = 0.59f)
                // c -30.28 38.23 -74.85 60.63 -122.09 60.63
                curveToRelative(
                    dx1 = -30.28f,
                    dy1 = 38.23f,
                    dx2 = -74.85f,
                    dy2 = 60.63f,
                    dx3 = -122.09f,
                    dy3 = 60.63f,
                )
                // m 166.8 -77.04
                moveToRelative(dx = 166.8f, dy = -77.04f)
                // a 99 99 0 0 1 -25.82 -3.44
                arcToRelative(
                    a = 99.0f,
                    b = 99.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -25.82f,
                    dy1 = -3.44f,
                )
                // l -1.34 -0.37
                lineToRelative(dx = -1.34f, dy = -0.37f)
                // a 98 98 0 0 1 -2.77 -0.84
                arcToRelative(
                    a = 98.0f,
                    b = 98.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -2.77f,
                    dy1 = -0.84f,
                )
                // l -1.38 -0.44
                lineToRelative(dx = -1.38f, dy = -0.44f)
                // a 100 100 0 0 1 -7.5 -2.85
                arcToRelative(
                    a = 100.0f,
                    b = 100.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -7.5f,
                    dy1 = -2.85f,
                )
                // l -1.5 -0.66
                lineToRelative(dx = -1.5f, dy = -0.66f)
                // a 101 101 0 0 1 -6.62 -3.24
                arcToRelative(
                    a = 101.0f,
                    b = 101.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -6.62f,
                    dy1 = -3.24f,
                )
                // l -0.98 -0.53
                lineToRelative(dx = -0.98f, dy = -0.53f)
                // a 104 104 0 0 1 -7.45 -4.51
                arcToRelative(
                    a = 104.0f,
                    b = 104.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -7.45f,
                    dy1 = -4.51f,
                )
                // l -0.14 -0.1
                lineToRelative(dx = -0.14f, dy = -0.1f)
                // c -27.76 -18.57 -46.66 -50.53 -49.63 -86.47
                curveToRelative(
                    dx1 = -27.76f,
                    dy1 = -18.57f,
                    dx2 = -46.66f,
                    dy2 = -50.53f,
                    dx3 = -49.63f,
                    dy3 = -86.47f,
                )
                // v -0.07
                verticalLineToRelative(dy = -0.07f)
                // a 120 120 0 0 1 -0.3 -4.7
                arcToRelative(
                    a = 120.0f,
                    b = 120.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -0.3f,
                    dy1 = -4.7f,
                )
                // l -0.01 -0.54
                lineToRelative(dx = -0.01f, dy = -0.54f)
                // q -0.1 -2.3 -0.1 -4.63
                quadToRelative(
                    dx1 = -0.1f,
                    dy1 = -2.3f,
                    dx2 = -0.1f,
                    dy2 = -4.63f,
                )
                // q 0 -2.04 0.09 -4.08
                quadToRelative(
                    dx1 = 0.0f,
                    dy1 = -2.04f,
                    dx2 = 0.09f,
                    dy2 = -4.08f,
                )
                // l 0.05 -1.07
                lineToRelative(dx = 0.05f, dy = -1.07f)
                // l 0.21 -3.3
                lineToRelative(dx = 0.21f, dy = -3.3f)
                // l 0.07 -0.84
                lineToRelative(dx = 0.07f, dy = -0.84f)
                // c 3.11 -35.76 23.36 -74.39 44.73 -106.1
                curveToRelative(
                    dx1 = 3.11f,
                    dy1 = -35.76f,
                    dx2 = 23.36f,
                    dy2 = -74.39f,
                    dx3 = 44.73f,
                    dy3 = -106.1f,
                )
                // c 22.5 -33.27 47.57 -60.94 60.4 -74.35
                curveToRelative(
                    dx1 = 22.5f,
                    dy1 = -33.27f,
                    dx2 = 47.57f,
                    dy2 = -60.94f,
                    dx3 = 60.4f,
                    dy3 = -74.35f,
                )
                // c 26.55 27.66 105.5 116.1 105.5 189.74
                curveToRelative(
                    dx1 = 26.55f,
                    dy1 = 27.66f,
                    dx2 = 105.5f,
                    dy2 = 116.1f,
                    dx3 = 105.5f,
                    dy3 = 189.74f,
                )
                // c 0.01 62.52 -47.33 113.39 -105.52 113.39
                curveToRelative(
                    dx1 = 0.01f,
                    dy1 = 62.52f,
                    dx2 = -47.33f,
                    dy2 = 113.39f,
                    dx3 = -105.52f,
                    dy3 = 113.39f,
                )
            }
            // M359.76 184.8 a12.5 12.5 0 0 0 -5.26 -7.87 13 13 0 0 0 -4.46 -1.84 12.3 12.3 0 0 0 -9.3 1.84 12.37 12.37 0 0 0 -5.5 10.3 12.5 12.5 0 0 0 3.62 8.76 13 13 0 0 0 4.02 2.68 12.5 12.5 0 0 0 7.16 .7 12.44 12.44 0 0 0 9.97 -12.15 q0 -1.21 -.25 -2.41 m6.9 175.85 c-30.87 0 -55.99 -28.65 -55.99 -63.86 0 -17.48 8.55 -41.26 24.7 -68.75 a12.38 12.38 0 1 0 -21.35 -12.55 c-18.66 31.74 -28.12 59.1 -28.12 81.3 0 48.87 36.23 88.63 80.76 88.63 a12.39 12.39 0 0 0 0 -24.77
            path(
                fill = SolidColor(colorScheme.primary),
            ) {
                // M 359.76 184.8
                moveTo(x = 359.76f, y = 184.8f)
                // a 12.5 12.5 0 0 0 -5.26 -7.87
                arcToRelative(
                    a = 12.5f,
                    b = 12.5f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -5.26f,
                    dy1 = -7.87f,
                )
                // a 13 13 0 0 0 -4.46 -1.84
                arcToRelative(
                    a = 13.0f,
                    b = 13.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -4.46f,
                    dy1 = -1.84f,
                )
                // a 12.3 12.3 0 0 0 -9.3 1.84
                arcToRelative(
                    a = 12.3f,
                    b = 12.3f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -9.3f,
                    dy1 = 1.84f,
                )
                // a 12.37 12.37 0 0 0 -5.5 10.3
                arcToRelative(
                    a = 12.37f,
                    b = 12.37f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -5.5f,
                    dy1 = 10.3f,
                )
                // a 12.5 12.5 0 0 0 3.62 8.76
                arcToRelative(
                    a = 12.5f,
                    b = 12.5f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 3.62f,
                    dy1 = 8.76f,
                )
                // a 13 13 0 0 0 4.02 2.68
                arcToRelative(
                    a = 13.0f,
                    b = 13.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 4.02f,
                    dy1 = 2.68f,
                )
                // a 12.5 12.5 0 0 0 7.16 0.7
                arcToRelative(
                    a = 12.5f,
                    b = 12.5f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 7.16f,
                    dy1 = 0.7f,
                )
                // a 12.44 12.44 0 0 0 9.97 -12.15
                arcToRelative(
                    a = 12.44f,
                    b = 12.44f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 9.97f,
                    dy1 = -12.15f,
                )
                // q 0 -1.21 -0.25 -2.41
                quadToRelative(
                    dx1 = 0.0f,
                    dy1 = -1.21f,
                    dx2 = -0.25f,
                    dy2 = -2.41f,
                )
                // m 6.9 175.85
                moveToRelative(dx = 6.9f, dy = 175.85f)
                // c -30.87 0 -55.99 -28.65 -55.99 -63.86
                curveToRelative(
                    dx1 = -30.87f,
                    dy1 = 0.0f,
                    dx2 = -55.99f,
                    dy2 = -28.65f,
                    dx3 = -55.99f,
                    dy3 = -63.86f,
                )
                // c 0 -17.48 8.55 -41.26 24.7 -68.75
                curveToRelative(
                    dx1 = 0.0f,
                    dy1 = -17.48f,
                    dx2 = 8.55f,
                    dy2 = -41.26f,
                    dx3 = 24.7f,
                    dy3 = -68.75f,
                )
                // a 12.38 12.38 0 1 0 -21.35 -12.55
                arcToRelative(
                    a = 12.38f,
                    b = 12.38f,
                    theta = 0.0f,
                    isMoreThanHalf = true,
                    isPositiveArc = false,
                    dx1 = -21.35f,
                    dy1 = -12.55f,
                )
                // c -18.66 31.74 -28.12 59.1 -28.12 81.3
                curveToRelative(
                    dx1 = -18.66f,
                    dy1 = 31.74f,
                    dx2 = -28.12f,
                    dy2 = 59.1f,
                    dx3 = -28.12f,
                    dy3 = 81.3f,
                )
                // c 0 48.87 36.23 88.63 80.76 88.63
                curveToRelative(
                    dx1 = 0.0f,
                    dy1 = 48.87f,
                    dx2 = 36.23f,
                    dy2 = 88.63f,
                    dx3 = 80.76f,
                    dy3 = 88.63f,
                )
                // a 12.39 12.39 0 0 0 0 -24.77
                arcToRelative(
                    a = 12.39f,
                    b = 12.39f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 0.0f,
                    dy1 = -24.77f,
                )
            }
        }.build().also { _fatIcon = it }
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
                imageVector = FatIcon,
                contentDescription = null,
                modifier = Modifier
                    .width((800.0).dp)
                    .height((800.0).dp),
            )
        }
    }
}

@Suppress("ObjectPropertyName")
private var _fatIcon: ImageVector? = null
