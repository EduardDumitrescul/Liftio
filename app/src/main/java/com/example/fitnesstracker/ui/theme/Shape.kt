package com.example.fitnesstracker.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp
import javax.annotation.concurrent.Immutable

@Immutable
data class CustomShapes (
    val roundedSmallCornerShape: RoundedCornerShape = RoundedCornerShape(8.dp),
    val roundedFullCornerShape: RoundedCornerShape = RoundedCornerShape(100)
)

val LocalCustomShapes = staticCompositionLocalOf {
    CustomShapes()
}