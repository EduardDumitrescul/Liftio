package com.example.fitnesstracker.ui.theme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun AppTheme(
    content: @Composable () -> Unit,
) {
    val appColors = LightColorTheme()
    val typography = CustomTypography()

    CompositionLocalProvider (
        LocalCustomColors provides appColors,
        LocalCustomTypography provides typography,
        content = content,
    )
}

object AppTheme {
    val colors: LightColorTheme
        @Composable
        get() = LocalCustomColors.current

    val typography: CustomTypography
        @Composable
        get() = LocalCustomTypography.current
}