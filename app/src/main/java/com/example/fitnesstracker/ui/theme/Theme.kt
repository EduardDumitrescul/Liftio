package com.example.fitnesstracker.ui.theme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun AppTheme(
    content: @Composable () -> Unit,
) {
    val appColors = LightColorTheme()

    CompositionLocalProvider (
        LocalCustomColors provides appColors,
        content = content,
    )
}

object AppTheme {
    val colors: LightColorTheme
        @Composable
        get() = LocalCustomColors.current
}