@file:Suppress("DuplicatedCode")

package com.joshrose.plotsforcompose.axis.x.continuous

import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import com.joshrose.plotsforcompose.axis.config.ContinuousAxisConfig
import com.joshrose.plotsforcompose.axis.config.util.Multiplier
import com.joshrose.plotsforcompose.axis.util.AxisPosition
import com.joshrose.plotsforcompose.axis.util.AxisPosition.CENTER
import com.joshrose.plotsforcompose.axis.util.Range
import com.joshrose.plotsforcompose.axis.x.util.*
import com.joshrose.plotsforcompose.axis.y.util.XAxisPosition
import com.joshrose.plotsforcompose.util.calculateOffset

@Suppress("DuplicatedCode")
@OptIn(ExperimentalTextApi::class)
fun DrawScope.continuousXAxis(
    config: ContinuousAxisConfig,
    labels: List<Float>,
    xRangeValues: Range,
    xPositions: YAxisPosition,
    yRangeValues: Range,
    yPositions: XAxisPosition,
    range: Float,
    textMeasurer: TextMeasurer
) {
    if (!config.showAxisLine && !config.showGuidelines && !config.showLabels) return

    labels.forEachIndexed { index, label ->
        if (yRangeValues.min < 0 && yRangeValues.max > 0 && label == 0f) return@forEachIndexed
        if ((xRangeValues.min == 0f || xRangeValues.max ==0f) &&
            (yRangeValues.min == 0f || yRangeValues.max == 0f) &&
            label == 0f) {
            if (config.showLabels) {
                drawXFloatLabel(
                    y = yPositions.labels,
                    x = xPositions.labels,
                    label = label,
                    maxYValue = yRangeValues.max,
                    textMeasurer = textMeasurer,
                    labelConfig = config.labels.copy(rotation = 0f)
                )
            }
            return@forEachIndexed
        }

        // x - calculates the proportion of the range that rangeDiff occupies and then scales that
        // difference to the DrawScope's width.
        val x = getX(
            width = size.width,
            xMax = xRangeValues.max,
            label = label,
            range = range,
            rangeAdj = config.labels.rangeAdjustment,
            index = index,
            labelsSize = labels.size
        )

        if (config.showLabels) {
            drawXFloatLabel(
                y = yPositions.labels,
                x = x,
                label = label,
                maxYValue = yRangeValues.max,
                textMeasurer = textMeasurer,
                labelConfig = config.labels
            )
        }

        if (config.showGuidelines && xPositions.axis != x) {
            drawXGuideline(
                guidelineConfig = config.guidelines,
                x = x,
                yPosition = yPositions.axis
            )
        }

        if (config.showAxisLine && config.axisLine.ticks) {
            drawXTick(
                axisLineConfig = config.axisLine,
                x = x,
                yPosition = yPositions.axis,
                yOffset = config.labels.yOffset.toPx()
            )
        }
    }

    if (config.showAxisLine) drawXAxis(axisLineConfig = config.axisLine, yPosition = yPositions.axis)
}

@OptIn(ExperimentalTextApi::class)
fun DrawScope.continuousXAxis(
    config: ContinuousAxisConfig,
    labels: List<Float>,
    xRangeValues: Range,
    yAxisPosition: YAxisPosition,
    yRangeValues: Range,
    xAxisPosition: XAxisPosition,
    axisPosition: AxisPosition,
    range: Float,
    textMeasurer: TextMeasurer
) {
    if (!config.showAxisLine && !config.showGuidelines && !config.showLabels) return

    labels.forEachIndexed { index, label ->
        if (yAxisPosition.axis == size.width.div(2f) && axisPosition == CENTER && label == 0f) return@forEachIndexed
        if ((xRangeValues.min == 0f || xRangeValues.max ==0f) &&
            (yRangeValues.min == 0f || yRangeValues.max == 0f) &&
            label == 0f) {
            if (config.showLabels) {
                drawXFloatLabel(
                    y = xAxisPosition.labels,
                    x = yAxisPosition.labels,
                    label = label,
                    axisPos = axisPosition,
                    textMeasurer = textMeasurer,
                    labelConfig = config.labels.copy(rotation = 0f)
                )
            }
            return@forEachIndexed
        }

        val x = getX(
            width = size.width,
            xMax = xRangeValues.max,
            label = label,
            range = range,
            rangeAdj = config.labels.rangeAdjustment,
            index = index,
            labelsSize = labels.size
        )

        if (config.showLabels) {
            drawXFloatLabel(
                y = xAxisPosition.labels,
                x = x,
                label = label,
                axisPos = axisPosition,
                textMeasurer = textMeasurer,
                labelConfig = config.labels
            )
        }

        if (config.showGuidelines && yAxisPosition.axis != x) {
            drawXGuideline(
                guidelineConfig = config.guidelines,
                x = x,
                yPosition = xAxisPosition.axis
            )
        }

        if (config.showAxisLine && config.axisLine.ticks) {
            drawXTick(
                axisLineConfig = config.axisLine,
                x = x,
                yPosition = xAxisPosition.axis,
                yOffset = config.labels.yOffset.toPx()
            )
        }
    }

    if (config.showAxisLine) drawXAxis(axisLineConfig = config.axisLine, yPosition = xAxisPosition.axis)
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