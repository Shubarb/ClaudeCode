package com.emojibattery.app.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = BrandPurple,
    onPrimary = White,
    secondary = BrandYellow,
    onSecondary = TextPrimary,
    background = SurfaceLight,
    onBackground = TextPrimary,
    surface = White,
    onSurface = TextPrimary,
)

@Composable
fun EmojiBatteryTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = Typography(),
        content = content,
    )
}
