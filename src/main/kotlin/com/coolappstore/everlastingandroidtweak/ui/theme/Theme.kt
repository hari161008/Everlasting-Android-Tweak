package com.coolappstore.everlastingandroidtweak.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF8BCAFF),
    onPrimary = Color(0xFF003352),
    primaryContainer = Color(0xFF004A74),
    onPrimaryContainer = Color(0xFFCDE5FF),
    secondary = Color(0xFFB3C9E0),
    onSecondary = Color(0xFF1D3447),
    secondaryContainer = Color(0xFF344B5E),
    onSecondaryContainer = Color(0xFFCFE5FC),
    tertiary = Color(0xFFC7C0EA),
    onTertiary = Color(0xFF2F2C4D),
    tertiaryContainer = Color(0xFF464365),
    onTertiaryContainer = Color(0xFFE4DCFF),
    background = Color(0xFF0F1417),
    onBackground = Color(0xFFDDE3EA),
    surface = Color(0xFF0F1417),
    onSurface = Color(0xFFDDE3EA),
    surfaceVariant = Color(0xFF40484F),
    onSurfaceVariant = Color(0xFFC0C7D0),
    outline = Color(0xFF8A9199),
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF006397),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFCDE5FF),
    onPrimaryContainer = Color(0xFF001D31),
    secondary = Color(0xFF4D6678),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFCFE5FC),
    onSecondaryContainer = Color(0xFF081E2D),
    tertiary = Color(0xFF5D5B7D),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFE4DCFF),
    onTertiaryContainer = Color(0xFF191836),
    background = Color(0xFFF7F9FC),
    onBackground = Color(0xFF181C1F),
    surface = Color(0xFFF7F9FC),
    onSurface = Color(0xFF181C1F),
    surfaceVariant = Color(0xFFDDE3EA),
    onSurfaceVariant = Color(0xFF40484F),
    outline = Color(0xFF71787F),
)

@Composable
fun EverlastingTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Color.Transparent.toArgb()
            window.navigationBarColor = Color.Transparent.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
