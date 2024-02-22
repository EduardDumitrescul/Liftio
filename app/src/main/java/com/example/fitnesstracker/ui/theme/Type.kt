package com.example.fitnesstracker.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.fitnesstracker.R
import javax.annotation.concurrent.Immutable

val JosefineFontFamily = FontFamily(
    Font(R.font.josefine_sans_variable_wght)
)

@Immutable
data class CustomTypography(
    val display: TextStyle = TextStyle(
        fontFamily = JosefineFontFamily,
        fontWeight = FontWeight.W600,
        fontSize = 64.sp,
        lineHeight = 72.sp,
        letterSpacing = 0.sp,
    ),
    val title: TextStyle = TextStyle(
        fontFamily = JosefineFontFamily,
        fontWeight = FontWeight.W600,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp,
    ),
    val headline: TextStyle = TextStyle(
        fontFamily = JosefineFontFamily,
        fontWeight = FontWeight.W600,
        fontSize = 20.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
    ),
    val body: TextStyle = TextStyle(
        fontFamily = JosefineFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = 16.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp,
    ),
    val caption: TextStyle = TextStyle(
        fontFamily = JosefineFontFamily,
        fontWeight = FontWeight.W600,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp,
    ),
)

val LocalCustomTypography = staticCompositionLocalOf {
    CustomTypography()
}