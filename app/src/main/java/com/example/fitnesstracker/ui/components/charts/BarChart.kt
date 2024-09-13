package com.example.fitnesstracker.ui.components.charts

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.draw
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.example.fitnesstracker.ui.theme.AppTheme


@Composable
fun BarChart(
    model: BarChartModel,
    modifier: Modifier = Modifier,
) {
    val textMeasurer = rememberTextMeasurer()

    Surface(
        modifier = modifier,
        color = model.backgroundColor
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            val drawer = BarChartDrawer(this, model, textMeasurer)
            drawer.drawLeftAxis()
            drawer.drawBars()

        }
    }
}

private class BarChartDrawer(
    private val scope: DrawScope,
    private val model: BarChartModel,
    private val textMeasurer: TextMeasurer,
) {
    private val height: Float get() = scope.size.height
    private val width: Float get() = scope.size.width

    private val textHeight get() = textMeasurer.measure("0", style = model.textStyle).size.height

    private val leftAxisWidth: Float get() = model.leftAxisWidth.value * scope.density
    private val bottomAxisHeight: Float get() = model.bottomAxisHeight.value * scope.density

    private val actualChartWidth: Float get() = width - leftAxisWidth
    private val actualChartHeight: Float get() = height - bottomAxisHeight


    private val leftAxisStepValue: Int get() {
        val max = model.barModels.maxBy { it.value }.value
        var greatestPowerOf10 = 1;
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
                    x = leftAxisWidth - textLayoutResult.size.width - 4f * scope.density,
                    y = leftAxisGapHeight * i.toFloat(),
                ),
            )


            val startOffset = Offset(
                leftAxisWidth,
                leftAxisGapHeight * i + textLayoutResult.size.height / 2f)

            val endOffset = Offset(
                scope.size.width / 1f,
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
        get() = actualChartWidth / ((model.barWidthToGapRatio + 1) * model.barModels.size + 1)
    private val barWidth: Float
        get() = barGapWidth * model.barWidthToGapRatio

    fun drawBars() {
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
                    x = leftAxisWidth + (i + 1) * barGapWidth + (i * barWidth),
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




@Composable
@Preview
private fun PreviewBarChart() {
    AppTheme {
        BarChart(
            model = BarChartModel.default(),
            modifier = Modifier.width(400.dp).height(240.dp)
        )
    }
}