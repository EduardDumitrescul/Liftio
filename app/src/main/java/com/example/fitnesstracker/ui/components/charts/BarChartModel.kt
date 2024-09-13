package com.example.fitnesstracker.ui.components.charts

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


data class BarChartModel(
    val barModels: List<BarModel>,
    val backgroundColor: Color = Color.White,
    val helperLineColor: Color = Color.LightGray,
    val barColor: Color = Color.Blue,
    val barWidthToGapRatio: Float = 2f,
    val textStyle: TextStyle = TextStyle.Default,
    val leftAxisWidth: Dp = 48.dp,
    val leftAxisSteps: Int = 8,
    val bottomAxisHeight: Dp = 24.dp,
) {
    companion object {
        fun default() = BarChartModel(
            barModels = MutableList(10) {BarModel.default()}
        )
    }
}