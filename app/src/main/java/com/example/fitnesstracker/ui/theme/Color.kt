package com.example.fitnesstracker.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import javax.annotation.concurrent.Immutable


@Immutable
data class ColorScheme(
    val white: Color,
    val black: Color,
    val green: Color,
    val red: Color,
    val background: Color,
    val onBackground: Color,
    val container: Color,
    val onContainer: Color,
    val containerVariant: Color,
    val onContainerVariant: Color,
    val outline: Color,
    val primary: Color,
    val onPrimary: Color,
    val primaryVariant: Color,
    val onPrimaryVariant: Color,
)

val lightColorTheme = ColorScheme(
    white = Color(0xFFF0F0F5),
    black = Color(0xFF0A0A0F),
    green = Color(0xFF2EB82E),
    red = Color(0xFFB82E2E),
    background = Color(0xFFE0E0EB),
    onBackground = Color(0xFF29293D),
    container = Color(0xFFF0F0F5),
    onContainer = Color(0xFF29293D),
    containerVariant = Color(0xFFD9E1F2),
    onContainerVariant = Color(0xFF131339),
    outline = Color (0xFF8585AD),
    primary = Color (0xFF0A265C),
    onPrimary = Color (0xFFE6E6E6),
    primaryVariant = Color(0xFFA3BFF5),
    onPrimaryVariant = Color(0xFF1F1F2E),
)

val darkColorTheme = ColorScheme(
    white = Color(0xFFF0F0F5),
    black = Color(0xFF0A0A0F),
    green = Color(0xFF53C653),
    red = Color(0xFF8F3D3D),
    background = Color(0xFF16151B),
    onBackground = Color(0xFFBEC1C1),
    container = Color(0xFF1F1E26),
    onContainer = Color(0xFFC8C6D2),
    containerVariant = Color(0xFF2E2D39),
    onContainerVariant = Color(0xFFC1B9C6),
    outline = Color (0xFF5C5B60),
    primary = Color (0xFF5C5EF6),
    onPrimary = Color (0xFF090103),
    primaryVariant = Color(0xFF52527A),
    onPrimaryVariant = Color(0xFFDFE3EC),
)


val LocalLightColors = staticCompositionLocalOf {
    lightColorTheme
}
val LocalDarkColors = staticCompositionLocalOf {
    darkColorTheme
}
