package com.example.fitnesstracker.view.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp
import javax.annotation.concurrent.Immutable

@Immutable
data class CustomShapes (
    val roundedSmallCornerShape: RoundedCornerShape = RoundedCornerShape(8.dp)
)

val LocalCustomShapes = staticCompositionLocalOf {
    CustomShapes()
}