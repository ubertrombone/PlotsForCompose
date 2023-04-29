@file:Suppress("DuplicatedCode")
@file:OptIn(ExperimentalTextApi::class)

package com.joshrose.plotsforcompose.axis.y.continuous

import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import com.joshrose.plotsforcompose.axis.config.ContinuousAxisConfig
import com.joshrose.plotsforcompose.axis.config.util.Multiplier
import com.joshrose.plotsforcompose.axis.util.Range
import com.joshrose.plotsforcompose.axis.util.XAxisPosition
import com.joshrose.plotsforcompose.axis.util.YAxisPosition
import com.joshrose.plotsforcompose.axis.y.util.drawYAxis
import com.joshrose.plotsforcompose.axis.y.util.drawYFloatLabel
import com.joshrose.plotsforcompose.axis.y.util.drawYGuideline
import com.joshrose.plotsforcompose.axis.y.util.drawYTick
import com.joshrose.plotsforcompose.util.calculateOffset

@ExperimentalTextApi
fun DrawScope.continuousYAxis(
    config: ContinuousAxisConfig,
    labels: List<Float>,
    yRangeValues: Range,
    yAxisPosition: YAxisPosition,
    xRangeValues: Range,
    xAxisPosition: XAxisPosition,
    range: Float,
    textMeasurer: TextMeasurer
) {
    if (!config.showAxisLine && !config.showGuidelines && !config.showLabels) return

    labels.reversed().forEachIndexed { index, label ->
        if (xAxisPosition == XAxisPosition.CENTER && yAxisPosition == YAxisPosition.CENTER && label == 0f) return@forEachIndexed

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

        val x = when (yAxisPosition) {
            YAxisPosition.START -> 0f
            YAxisPosition.END -> size.width
            YAxisPosition.CENTER -> size.width.div(2f)
        }

        if (config.showLabels) {
            if ((xRangeValues.min == 0f || xRangeValues.max ==0f) && label == 0f) return@forEachIndexed

            drawYFloatLabel(
                y = y,
                x = x,
                label = label,
                textMeasurer = textMeasurer,
                labelConfig = config.labels,
                yAxisPosition = yAxisPosition
            )
        }

        if (config.showGuidelines && x != y) {
            drawYGuideline(
                guidelineConfig = config.guidelines,
                y = y,
                yAxisPosition = yAxisPosition
            )
        }

        if (config.showAxisLine) {
            drawYTick(
                axisLineConfig = config.axisLine,
                y = y,
                yAxisPosition = yAxisPosition,
                axisOffset = config.labels.axisOffset.toPx()
            )
        }
    }

    if (config.showAxisLine) drawYAxis(axisLineConfig = config.axisLine, yAxisPosition = yAxisPosition)
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