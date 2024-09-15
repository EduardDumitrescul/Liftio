package com.example.fitnesstracker.ui.components.charts

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times


data class BarChartModel(
    val height: Dp = 240.dp,
    val width: Dp = 400.dp,
    val backgroundColor: Color = Color.White,
    val helperLineColor: Color = Color.LightGray,

    val barModels: List<BarModel>,
    val barWidth: Dp = 12.dp,
    val barColor: Color = Color.Blue,

    val barGapWidth: Dp = 8.dp,

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

    val chartWidth: Dp get() = leftAxisWidth + (barModels.size * (barWidth + barGapWidth))
}