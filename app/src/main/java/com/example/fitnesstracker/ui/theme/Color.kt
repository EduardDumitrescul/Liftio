package com.example.fitnesstracker.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import javax.annotation.concurrent.Immutable


@Immutable
data class LightColorTheme(
    val white: Color = Color(0xFFF0F0F5),
    val black: Color = Color(0xFF0A0A0F),
    val green: Color = Color(0xFF2EB82E),
    val red: Color = Color(0xFFB82E2E),
    val background: Color = Color(0xFFE0E0EB),
    val onBackground: Color = Color(0xFF29293D),
    val container: Color = Color(0xFFF0F0F5),
    val onContainer: Color = Color(0xFF29293D),
    val containerVariant: Color = Color(0xFFD9E1F2),
    val onContainerVariant: Color = Color(0xFF131339),
    val outline: Color = Color(0xFF8585AD),
    val primary: Color = Color(0xFF0A265C),
    val onPrimary: Color = Color(0xFFE6E6E6),
    val primaryVariant: Color = Color(0xFFA3BFF5),
    val onPrimaryVariant: Color = Color(0xFF1F1F2E),
)

val LocalCustomColors = staticCompositionLocalOf {
    LightColorTheme()
}
