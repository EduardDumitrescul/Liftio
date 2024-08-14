package com.example.fitnesstracker.ui.theme
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun AppTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val appColors = if (!useDarkTheme) {
        lightColorTheme
    } else {
        darkColorTheme
    }
    val typography = CustomTypography()
    val shapes = CustomShapes()
    val dimensions = Dimensions()

    CompositionLocalProvider (
        LocalLightColors provides appColors,
        LocalCustomTypography provides typography,
        LocalCustomShapes provides shapes,
        LocalDimensions provides dimensions,
        content = content,
    )
}

object AppTheme {
    val colors: ColorScheme
        @Composable
        get() = LocalLightColors.current

    val typography: CustomTypography
        @Composable
        get() = LocalCustomTypography.current

    val shapes: CustomShapes
        @Composable
        get() = LocalCustomShapes.current

    val dimensions: Dimensions
        @Composable
        get() = LocalDimensions.current
}
