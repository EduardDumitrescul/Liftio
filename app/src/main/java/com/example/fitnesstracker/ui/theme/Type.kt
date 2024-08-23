package com.example.fitnesstracker.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.fitnesstracker.R
import javax.annotation.concurrent.Immutable

@OptIn(ExperimentalTextApi::class)
val JosefineFontFamily = FontFamily(
    Font(
        R.font.josefine_sans_variable_wght,
        variationSettings = FontVariation.Settings(
            FontVariation.weight(FontWeight.Normal.weight),
        )
    )
)

@Immutable
data class CustomTypography(
    val display: TextStyle = TextStyle(
        fontFamily = JosefineFontFamily,
        fontWeight = FontWeight.W600,
        fontSize = 64.sp,
        lineHeight = 80.sp,
        letterSpacing = 0.sp,
    ),
    val title: TextStyle = TextStyle(
        fontFamily = JosefineFontFamily,
        fontWeight = FontWeight.W600,
        fontSize = 28.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp,
        textAlign = TextAlign.Center,
//        platformStyle = PlatformTextStyle(includeFontPadding = false),
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Bottom,
            trim = LineHeightStyle.Trim.None
        ),
    ),
    val headline: TextStyle = TextStyle(
        fontFamily = JosefineFontFamily,
        fontWeight = FontWeight.W600,
        fontSize = 17.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.sp,
    ),
    val body: TextStyle = TextStyle(
        fontFamily = JosefineFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = 17.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.sp,
    ),
    val caption: TextStyle = TextStyle(
        fontFamily = JosefineFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.sp,
    ),
)

val LocalCustomTypography = staticCompositionLocalOf {
    CustomTypography()
}