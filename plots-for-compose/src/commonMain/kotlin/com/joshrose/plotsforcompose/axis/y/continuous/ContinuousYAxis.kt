@file:Suppress("DuplicatedCode")
@file:OptIn(ExperimentalTextApi::class)

package com.joshrose.plotsforcompose.axis.y.continuous

import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import com.joshrose.plotsforcompose.axis.config.ContinuousAxisConfig
import com.joshrose.plotsforcompose.axis.config.util.Multiplier
import com.joshrose.plotsforcompose.axis.util.AxisPosition
import com.joshrose.plotsforcompose.axis.util.AxisPosition.CENTER
import com.joshrose.plotsforcompose.axis.util.Range
import com.joshrose.plotsforcompose.axis.x.util.YAxisPosition
import com.joshrose.plotsforcompose.axis.y.util.*
import com.joshrose.plotsforcompose.util.calculateOffset

@Suppress("DuplicatedCode")
fun DrawScope.continuousYAxis(
    config: ContinuousAxisConfig,
    labels: List<Float>,
    yRangeValues: Range,
    xAxisPosition: XAxisPosition,
    xRangeValues: Range,
    yAxisPosition: YAxisPosition,
    range: Float,
    textMeasurer: TextMeasurer
) {
    if (!config.showAxisLine && !config.showGuidelines && !config.showLabels) return

    labels.reversed().forEachIndexed { index, label ->
        if (xRangeValues.min < 0 && xRangeValues.max > 0 && label == 0f) return@forEachIndexed
        if ((xRangeValues.min == 0f || xRangeValues.max ==0f) && label == 0f) return@forEachIndexed

        // y - calculates the proportion of the range that rangeDiff occupies and then scales that
        // difference to the DrawScope's height. For the y-axis, we then have to subtract that value from the height.
        val y = getY(
            height = size.height,
            yMax = yRangeValues.max,
            label = label,
            range = range,
            rangeAdj = config.labels.rangeAdjustment,
            index = index,
            labelsSize = labels.size
        )

        // TODO: Overloads for x and y to force min and max values
        if (config.showLabels) {
            drawYFloatLabel(
                y = y,
                x = yAxisPosition.labels,
                label = label,
                maxXValue = xRangeValues.max,
                textMeasurer = textMeasurer,
                labelConfig = config.labels
            )
        }

        if (config.showGuidelines && xAxisPosition.axis != y) {
            drawYGuideline(
                guidelineConfig = config.guidelines,
                y = y,
                xPosition = yAxisPosition.axis
            )
        }

        if (config.showAxisLine && config.axisLine.ticks) {
            drawYTick(
                axisLineConfig = config.axisLine,
                y = y,
                xPosition = yAxisPosition.axis,
                xOffset = config.labels.xOffset.toPx()
            )
        }
    }

    if (config.showAxisLine) drawYAxis(axisLineConfig = config.axisLine, xPosition = yAxisPosition.axis)
}

@ExperimentalTextApi
fun DrawScope.continuousYAxis(
    config: ContinuousAxisConfig,
    labels: List<Float>,
    yRangeValues: Range,
    xAxisPosition: XAxisPosition,
    xRangeValues: Range,
    yAxisPosition: YAxisPosition,
    axisPosition: AxisPosition,
    range: Float,
    textMeasurer: TextMeasurer
) {
    if (!config.showAxisLine && !config.showGuidelines && !config.showLabels) return

    labels.reversed().forEachIndexed { index, label ->
        if (xAxisPosition.axis == size.height.div(2f) && axisPosition == CENTER && label == 0f) return@forEachIndexed
        if ((xRangeValues.min == 0f || xRangeValues.max ==0f) && label == 0f) return@forEachIndexed

        // y - calculates the proportion of the range that rangeDiff occupies and then scales that
        // difference to the DrawScope's height. For the y-axis, we then have to subtract that value from the height.
        val y = getY(
            height = size.height,
            yMax = yRangeValues.max,
            label = label,
            range = range,
            rangeAdj = config.labels.rangeAdjustment,
            index = index,
            labelsSize = labels.size
        )

        if (config.showLabels) {
            drawYFloatLabel(
                y = y,
                x = yAxisPosition.labels,
                label = label,
                textMeasurer = textMeasurer,
                labelConfig = config.labels,
                axisPos = axisPosition
            )
        }

        if (config.showGuidelines && xAxisPosition.axis != y) {
            drawYGuideline(
                guidelineConfig = config.guidelines,
                y = y,
                xPosition = yAxisPosition.axis
            )
        }

        if (config.showAxisLine && config.axisLine.ticks) {
            drawYTick(
                axisLineConfig = config.axisLine,
                y = y,
                xPosition = yAxisPosition.axis,
                xOffset = config.labels.xOffset.toPx()
            )
        }
    }

    if (config.showAxisLine) drawYAxis(axisLineConfig = config.axisLine, xPosition = yAxisPosition.axis)
}

fun getY(
    height: Float,
    yMax: Float,
    label: Float,
    range: Float,
    rangeAdj: Multiplier,
    index: Int,
    labelsSize: Int
): Float {
    val rangeDiff = calculateOffset(maxValue = yMax, offsetValue = label, range = range)
    return if (yMax <= 0) {
        height
            .times(1f.minus(rangeAdj.factor))
            .div(labelsSize.minus(1))
            .times(index)
            .plus(height.times(rangeAdj.factor))
    } else height.minus(rangeDiff.div(range).times(height))
}