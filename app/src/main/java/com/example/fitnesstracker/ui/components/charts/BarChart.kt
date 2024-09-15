package com.example.fitnesstracker.ui.components.charts

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.fitnesstracker.ui.theme.AppTheme

//TODO add title
//TODO refactor

@Composable
fun BarChart(
    model: BarChartModel,
    modifier: Modifier = Modifier,
) {
    val textMeasurer = rememberTextMeasurer()
    val scrollState = rememberScrollState()

    Surface(
        modifier = modifier
            .height(model.height)  // Use model's defined height
            .fillMaxWidth()
            .horizontalScroll(scrollState)
            .padding(end = model.leftAxisWidth/2),
        color = model.backgroundColor
    ) {
        Canvas(
            modifier = Modifier
                .width(model.chartWidth)  // Ensure horizontal scroll by setting the chart width
                .fillMaxHeight()  // Make the Canvas fill the available height
                .padding(bottom = 16.dp)  // Optional: Add padding for labels or axis
        ) {
            val drawer = BarChartDrawer(this, model, textMeasurer, scrollState.value)
            drawer.drawLeftAxis()
            drawer.drawBars()
        }
    }
}

private class BarChartDrawer(
    private val scope: DrawScope,
    private val model: BarChartModel,
    private val textMeasurer: TextMeasurer,
    private val scrollOffset: Int,
) {
    private val height: Float get() = scope.size.height
    private val width: Float get() = scope.size.width

    private val textHeight get() = textMeasurer.measure("0", style = model.textStyle).size.height

    private val leftAxisWidth: Float get() = model.leftAxisWidth.value * scope.density
    private val bottomAxisHeight: Float get() = model.bottomAxisHeight.value * scope.density

    private val actualChartWidth: Float get() = width - leftAxisWidth
    private val actualChartHeight: Float get() = height - bottomAxisHeight


    private val leftAxisStepValue: Int get() {
        if(model.barModels.isEmpty()) {
            return 10
        }
        val max = model.barModels.maxByOrNull { it.value }!!.value
        if(max == 0) {
            return 5
        }

        var greatestPowerOf10 = 1
        while(10 * greatestPowerOf10 < 0.8 * max / model.leftAxisSteps) {
            greatestPowerOf10 *= 10
        }
        val stepValue = ((max / model.leftAxisSteps / greatestPowerOf10 + 1) * greatestPowerOf10)
        return stepValue
    }
    private val maxChartValue: Int get() {
        return leftAxisStepValue * model.leftAxisSteps
    }

    fun drawLeftAxis() {
        val leftAxisGapHeight: Float = (actualChartHeight - textHeight / 2) / (model.leftAxisSteps)
        for(i in 0 until model.leftAxisSteps + 1) {
            val textLayoutResult: TextLayoutResult =
                textMeasurer.measure(
                    text = AnnotatedString(((model.leftAxisSteps - i) * leftAxisStepValue).toString()),
                    style = model.textStyle,
                )
            scope.drawText(
                textLayoutResult = textLayoutResult,
                topLeft = Offset(
                    x = scrollOffset + leftAxisWidth - textLayoutResult.size.width - 4f * scope.density,
                    y = leftAxisGapHeight * i.toFloat(),
                ),
            )


            val startOffset = Offset(
                scrollOffset + leftAxisWidth,
                leftAxisGapHeight * i + textLayoutResult.size.height / 2f)

            val endOffset = Offset(
                scrollOffset + scope.size.width / 1f,
                leftAxisGapHeight * i + textLayoutResult.size.height / 2f)

            scope.drawLine(
                color = model.helperLineColor,
                start = startOffset,
                end =  endOffset,
                strokeWidth = 1f * scope.density,
            )
        }
    }

    private val barGapWidth: Float
        get() = model.barGapWidth.value * scope.density
    private val barWidth: Float
        get() = model.barWidth.value * scope.density

    fun drawBars() {
        scope.clipRect(
            left = leftAxisWidth + scrollOffset,
            top = 0f,
            bottom = height,
            right = width + scrollOffset
        ) {
            for(i in 0 until model.barModels.size) {
                val barModel = model.barModels[i]

                val textLayoutResult: TextLayoutResult =
                    textMeasurer.measure(
                        text = AnnotatedString(barModel.label),
                        style = model.textStyle,
                    )
                scope.drawText(
                    textLayoutResult = textLayoutResult,
                    topLeft = Offset(
                        x = leftAxisWidth + (i + 1) * barGapWidth + ((i + 0.5f) * barWidth) - textLayoutResult.size.width/2,
                        y = actualChartHeight + 4 * scope.density
                    ),
                )


                val barHeight = (actualChartHeight - textHeight / 2) * (barModel.value * 1f / maxChartValue)
                val topLeftOffset = Offset(
                    leftAxisWidth + (i + 1) * barGapWidth + (i * barWidth),
                    actualChartHeight - barHeight
                )
                val size = Size(barWidth, barHeight)

                scope.drawRoundRect(
                    model.barColor,
                    topLeft = topLeftOffset,
                    size = size,
                    cornerRadius = CornerRadius(8f, 8f)
                )

            }
        }
    }
}




@Composable
@Preview
private fun PreviewBarChart() {
    AppTheme {
        BarChart(
            model = BarChartModel.default(),
            modifier = Modifier
        )
    }
}