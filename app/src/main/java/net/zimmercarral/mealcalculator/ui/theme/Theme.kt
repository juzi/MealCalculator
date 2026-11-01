package net.zimmercarral.mealcalculator.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Light theme colors - mild blue tones
private val Blue10 = Color(0xFFF8FAFF)
private val Blue20 = Color(0xFFE8F2FF)
private val Blue30 = Color(0xFFD1E4FF)
private val Blue40 = Color(0xFFA8CCFF)
private val Blue50 = Color(0xFF7BB3FF)
private val Blue60 = Color(0xFF4A90E2)
private val Blue70 = Color(0xFF2C5AA0)
private val Blue80 = Color(0xFF1E3A5F)
private val Blue90 = Color(0xFF0F1B2D)
private val Blue95 = Color(0xFF080F1A)

// Neutral colors with slight blue tint
private val BlueGray10 = Color(0xFFFCFCFE)
private val BlueGray20 = Color(0xFFF7F8FA)
private val BlueGray30 = Color(0xFFE8EAF0)
private val BlueGray40 = Color(0xFFD3D7E0)
private val BlueGray50 = Color(0xFFAEB5C4)
private val BlueGray60 = Color(0xFF8892A4)
private val BlueGray70 = Color(0xFF6B7485)
private val BlueGray80 = Color(0xFF4F5866)
private val BlueGray90 = Color(0xFF343B48)
private val BlueGray95 = Color(0xFF1A1E2A)

// Error colors
private val Red40 = Color(0xFFBA1A1A)
private val Red80 = Color(0xFFFFB4AB)

val lightScheme = lightColorScheme(
    primary = Blue60,
    onPrimary = Color.White,
    primaryContainer = Blue20,
    onPrimaryContainer = Blue80,

    secondary = Blue50,
    onSecondary = Color.White,
    secondaryContainer = Blue30,
    onSecondaryContainer = Blue70,

    tertiary = Blue40,
    onTertiary = Color.White,
    tertiaryContainer = Blue10,
    onTertiaryContainer = Blue80,

    error = Red40,
    onError = Color.White,
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002),

    background = BlueGray10,
    onBackground = BlueGray90,

    surface = BlueGray10,
    onSurface = BlueGray90,
    surfaceVariant = BlueGray30,
    onSurfaceVariant = BlueGray70,

    surfaceContainer = BlueGray20,
    surfaceContainerHigh = BlueGray30,
    surfaceContainerHighest = BlueGray40,
    surfaceContainerLow = BlueGray10,
    surfaceContainerLowest = Color.White,

    outline = BlueGray60,
    outlineVariant = BlueGray40,

    inverseSurface = BlueGray90,
    inverseOnSurface = BlueGray20,
    inversePrimary = Blue40,
)

val darkScheme = darkColorScheme(
    primary = Blue40,
    onPrimary = Blue90,
    primaryContainer = Blue70,
    onPrimaryContainer = Blue20,

    secondary = Blue50,
    onSecondary = Blue80,
    secondaryContainer = Blue80,
    onSecondaryContainer = Blue30,

    tertiary = Blue30,
    onTertiary = Blue90,
    tertiaryContainer = Blue80,
    onTertiaryContainer = Blue20,

    error = Red80,
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6),

    background = BlueGray95,
    onBackground = BlueGray20,

    surface = BlueGray95,
    onSurface = BlueGray20,
    surfaceVariant = BlueGray80,
    onSurfaceVariant = BlueGray40,

    surfaceContainer = BlueGray90,
    surfaceContainerHigh = BlueGray80,
    surfaceContainerHighest = BlueGray70,
    surfaceContainerLow = BlueGray95,
    surfaceContainerLowest = Color(0xFF000000),

    outline = BlueGray60,
    outlineVariant = BlueGray80,

    inverseSurface = BlueGray20,
    inverseOnSurface = BlueGray90,
    inversePrimary = Blue60,
)

@Immutable
data class ColorFamily(
    val color: Color,
    val onColor: Color,
    val colorContainer: Color,
    val onColorContainer: Color
)

val unspecified_scheme = ColorFamily(
    Color.Unspecified, Color.Unspecified, Color.Unspecified, Color.Unspecified
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color disabled for consistent blue theme
    dynamicColor: Boolean = false,
    content: @Composable() () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> darkScheme
        else -> lightScheme
    }

    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            // window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}

