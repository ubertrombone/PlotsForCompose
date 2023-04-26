@file:Suppress("DuplicatedCode")

package com.joshrose.plotsforcompose.axis.x.continuous

import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import com.joshrose.plotsforcompose.axis.config.ContinuousAxisConfig
import com.joshrose.plotsforcompose.axis.config.util.Multiplier
import com.joshrose.plotsforcompose.axis.util.AxisPosition
import com.joshrose.plotsforcompose.axis.util.AxisPosition.*
import com.joshrose.plotsforcompose.axis.util.Range
import com.joshrose.plotsforcompose.axis.x.util.drawXAxis
import com.joshrose.plotsforcompose.axis.x.util.drawXFloatLabel
import com.joshrose.plotsforcompose.axis.x.util.drawXGuideline
import com.joshrose.plotsforcompose.axis.x.util.drawXTick
import com.joshrose.plotsforcompose.util.calculateOffset

@OptIn(ExperimentalTextApi::class)
fun DrawScope.continuousXAxis(
    config: ContinuousAxisConfig,
    labels: List<Float>,
    xRangeValues: Range,
    xAxisPosition: AxisPosition,
    yRangeValues: Range,
    yAxisPosition: AxisPosition,
    range: Float,
    textMeasurer: TextMeasurer
) {
    if (!config.showAxisLine && !config.showGuidelines && !config.showLabels) return

    labels.forEachIndexed { index, label ->
        if (yAxisPosition == CENTER && xAxisPosition == CENTER && label == 0f) return@forEachIndexed

        val x = getX(
            width = size.width,
            xMax = xRangeValues.max,
            label = label,
            range = range,
            rangeAdj = config.labels.rangeAdjustment,
            index = index,
            labelsSize = labels.size
        )

        val y = when (xAxisPosition) {
            TOP_START -> 0f
            BOTTOM_END -> size.height
            CENTER -> size.height.div(2f)
        }

        if (config.showLabels) {
            if ((xRangeValues.min == 0f || xRangeValues.max ==0f) &&
                (yRangeValues.min == 0f || yRangeValues.max == 0f) &&
                label == 0f) {
                drawXFloatLabel(
                    y = y,
                    x = when (yAxisPosition) {
                        TOP_START -> 0f
                        BOTTOM_END -> size.width
                        CENTER -> size.width.div(2f)
                    },
                    label = label,
                    xAxisPosition = xAxisPosition,
                    textMeasurer = textMeasurer,
                    labelConfig = config.labels.copy(rotation = 0f)
                )
                return@forEachIndexed
            } else {
                drawXFloatLabel(
                    y = y,
                    x = x,
                    label = label,
                    xAxisPosition = xAxisPosition,
                    textMeasurer = textMeasurer,
                    labelConfig = config.labels
                )
            }
        }

        if (config.showGuidelines && y != x) {
            drawXGuideline(
                guidelineConfig = config.guidelines,
                x = x,
                xAxisPosition = xAxisPosition
            )
        }

        if (config.showAxisLine && config.axisLine.ticks) {
            drawXTick(
                axisLineConfig = config.axisLine,
                x = x,
                xAxisPosition = xAxisPosition,
                yOffset = config.labels.yOffset.toPx()
            )
        }
    }

    if (config.showAxisLine) drawXAxis(axisLineConfig = config.axisLine, xAxisPosition = xAxisPosition)
}

fun getX(
    width: Float,
    xMax: Float,
    label: Float,
    range: Float,
    rangeAdj: Multiplier,
    index: Int,
    labelsSize: Int
): Float {
    val rangeDiff = calculateOffset(maxValue = xMax, offsetValue = label, range = range)
    return if (xMax <= 0) {
        width
            .times(1f.minus(if (xMax == 0f) 0f else rangeAdj.factor))
            .div(labelsSize.minus(1))
            .times(index)
    } else rangeDiff.div(range).times(width)
}