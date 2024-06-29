package com.example.fitnesstracker.ui.theme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun AppTheme(
    content: @Composable () -> Unit,
) {
    val appColors = LightColorTheme()
    val typography = CustomTypography()
    val shapes = CustomShapes()
    val dimensions = Dimensions()

    CompositionLocalProvider (
        LocalCustomColors provides appColors,
        LocalCustomTypography provides typography,
        LocalCustomShapes provides shapes,
        LocalDimensions provides dimensions,
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

    val shapes: CustomShapes
        @Composable
        get() = LocalCustomShapes.current

    val dimensions: Dimensions
        @Composable
        get() = LocalDimensions.current
}
