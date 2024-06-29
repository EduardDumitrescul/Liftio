package com.example.fitnesstracker.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import javax.annotation.concurrent.Immutable

@Immutable
data class Dimensions (
    val iconSmall: Dp = 24.dp,
    val iconNormal: Dp = 32.dp,
    val iconLarge: Dp = 48.dp,
    val border: Dp = 1.dp,
    val paddingSmall: Dp = 4.dp,
    val paddingNormal: Dp = 8.dp,
    val paddingLarge: Dp = 16.dp,
    val spacingNormal: Dp = 8.dp,
    val spacingLarge: Dp = 16.dp,
    val elevationHigh: Dp = 8.dp,
)

val LocalDimensions = staticCompositionLocalOf {
    Dimensions()
}