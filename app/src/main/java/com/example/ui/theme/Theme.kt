package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.model.ReadingTheme

private val ParchmentColorScheme = lightColorScheme(
    primary = ParchmentPrimary,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFF0E5D3),
    onPrimaryContainer = ParchmentPrimary,
    secondary = ParchmentSecondary,
    onSecondary = Color.White,
    background = ParchmentBackground,
    onBackground = ParchmentOnSurface,
    surface = ParchmentSurface,
    onSurface = ParchmentOnSurface,
    surfaceVariant = Color(0xFFE5DCC9),
    onSurfaceVariant = Color(0xFF4A3E31)
)

private val LightBibleColorScheme = lightColorScheme(
    primary = LightPrimary,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFD6E3FF),
    onPrimaryContainer = LightPrimary,
    secondary = Color(0xFF4B6079),
    onSecondary = Color.White,
    background = LightBg,
    onBackground = LightOnBg,
    surface = LightSurface,
    onSurface = LightOnBg,
    surfaceVariant = Color(0xFFE0E5EC),
    onSurfaceVariant = Color(0xFF43474E)
)

private val DarkBibleColorScheme = darkColorScheme(
    primary = DarkPrimary,
    onPrimary = Color(0xFF3F2E00),
    primaryContainer = Color(0xFF5B4300),
    onPrimaryContainer = Color(0xFFFFDF9E),
    secondary = Color(0xFFC5C6D0),
    onSecondary = Color(0xFF2E3138),
    background = DarkBg,
    onBackground = DarkOnBg,
    surface = DarkSurface,
    onSurface = DarkOnBg,
    surfaceVariant = Color(0xFF282E3A),
    onSurfaceVariant = Color(0xFFC4C6D0)
)

private val HighContrastColorScheme = darkColorScheme(
    primary = HighContrastPrimary,
    onPrimary = Color.Black,
    primaryContainer = Color(0xFF332A00),
    onPrimaryContainer = HighContrastPrimary,
    secondary = HighContrastSecondary,
    onSecondary = Color.Black,
    background = HighContrastBg,
    onBackground = HighContrastOnBg,
    surface = HighContrastSurface,
    onSurface = HighContrastOnBg,
    surfaceVariant = Color(0xFF262626),
    onSurfaceVariant = Color.White
)

@Composable
fun BaebeleTheme(
    readingTheme: ReadingTheme = ReadingTheme.PARCHMENT,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme: ColorScheme = when (readingTheme) {
        ReadingTheme.PARCHMENT -> ParchmentColorScheme
        ReadingTheme.LIGHT -> LightBibleColorScheme
        ReadingTheme.DARK -> DarkBibleColorScheme
        ReadingTheme.HIGH_CONTRAST -> HighContrastColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
