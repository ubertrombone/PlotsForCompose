@file:Suppress("DuplicatedCode")
@file:OptIn(ExperimentalTextApi::class)

package com.joshrose.plotsforcompose.axis.x.continuous

import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import com.joshrose.plotsforcompose.axis.config.ContinuousAxisConfig
import com.joshrose.plotsforcompose.axis.config.util.Multiplier
import com.joshrose.plotsforcompose.axis.util.AxisPosition.XAxisPosition
import com.joshrose.plotsforcompose.axis.util.AxisPosition.YAxisPosition
import com.joshrose.plotsforcompose.axis.util.Range
import com.joshrose.plotsforcompose.axis.x.util.drawXAxis
import com.joshrose.plotsforcompose.axis.x.util.drawXFloatLabel
import com.joshrose.plotsforcompose.axis.x.util.drawXGuideline
import com.joshrose.plotsforcompose.axis.x.util.drawXTick
import com.joshrose.plotsforcompose.util.calculateOffset

fun DrawScope.continuousXAxis(
    config: ContinuousAxisConfig,
    labels: List<Float>,
    xRangeValues: Range<Float>,
    xAxisPosition: XAxisPosition,
    yAxisPosition: YAxisPosition,
    drawYAxis: Boolean,
    drawZero: Boolean = true,
    range: Float,
    textMeasurer: TextMeasurer
) {
    labels.forEachIndexed { index, label ->
        val x = getX(
            width = size.width,
            xValues = xRangeValues,
            label = label,
            range = range,
            rangeAdj = config.labels.rangeAdjustment,
            index = index,
            labelsSize = labels.size
        )

        val y = when (xAxisPosition) {
            XAxisPosition.TOP -> 0f
            XAxisPosition.BOTTOM -> size.height
            XAxisPosition.CENTER -> size.height.div(2f)
        }

        val yAxisPositionXValue = if (drawYAxis) {
            when (yAxisPosition) {
                YAxisPosition.START -> 0f
                YAxisPosition.CENTER -> size.width.div(2f)
                YAxisPosition.END -> size.width
            }
        } else null

        if (config.showGuidelines) {
            if (drawYAxis && yAxisPositionXValue != x) {
                drawXGuideline(
                    guidelineConfig = config.guidelines,
                    x = x,
                    xAxisPosition = xAxisPosition
                )
            } else {
                drawXGuideline(
                    guidelineConfig = config.guidelines,
                    x = x,
                    xAxisPosition = xAxisPosition
                )
            }
        }

        if (!drawZero && label == 0f) return@forEachIndexed

        if (config.showLabels) {
            drawXFloatLabel(
                y = y,
                x = x,
                label = label,
                xAxisPosition = xAxisPosition,
                textMeasurer = textMeasurer,
                labelConfig = config.labels
            )
        }

        if (config.showAxisLine && config.axisLine.ticks) {
            drawXTick(
                axisLineConfig = config.axisLine,
                x = x,
                xAxisPosition = xAxisPosition,
                axisOffset = config.labels.axisOffset.toPx()
            )
        }
    }

    if (config.showAxisLine) drawXAxis(axisLineConfig = config.axisLine, xAxisPosition = xAxisPosition)
}

fun <T> DrawScope.drawXAxis(
    config: ContinuousAxisConfig,
    labels: List<T>,
    xRangeValues: Range<T>,
    xAxisPosition: XAxisPosition,
    yAxisPosition: YAxisPosition,
    drawYAxis: Boolean,
    drawZero: Boolean = true,
    range: T? = null,
    textMeasurer: TextMeasurer
) {

}

fun getX(
    width: Float,
    xValues: Range<Float>,
    label: Float,
    range: Float,
    rangeAdj: Multiplier,
    index: Int,
    labelsSize: Int
): Float {
    val rangeDiff = calculateOffset(maxValue = xValues.max, offsetValue = label, range = range)
    val rangeAdjustment = if (xValues.min == 0f || xValues.max == 0f) 0f else rangeAdj.factor
    return if (xValues.max <= 0) {
        width
            .times(1f.minus(if (xValues.max == 0f) 0f else rangeAdjustment))
            .div(labelsSize.minus(1))
            .times(index)
    } else rangeDiff.div(range).times(width)
}