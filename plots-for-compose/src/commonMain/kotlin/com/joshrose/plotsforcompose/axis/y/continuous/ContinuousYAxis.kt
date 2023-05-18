@file:Suppress("DuplicatedCode")
@file:OptIn(ExperimentalTextApi::class)

package com.joshrose.plotsforcompose.axis.y.continuous

import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import com.joshrose.plotsforcompose.axis.config.ContinuousAxisConfig
import com.joshrose.plotsforcompose.axis.config.util.Multiplier
import com.joshrose.plotsforcompose.axis.util.AxisPosition.XAxisPosition
import com.joshrose.plotsforcompose.axis.util.AxisPosition.YAxisPosition
import com.joshrose.plotsforcompose.axis.util.Range
import com.joshrose.plotsforcompose.axis.y.util.drawYAxis
import com.joshrose.plotsforcompose.axis.y.util.drawYFloatLabel
import com.joshrose.plotsforcompose.axis.y.util.drawYGuideline
import com.joshrose.plotsforcompose.axis.y.util.drawYTick
import com.joshrose.plotsforcompose.util.calculateOffset

@ExperimentalTextApi
fun DrawScope.continuousYAxis(
    config: ContinuousAxisConfig,
    labels: List<Float>,
    yRangeValues: Range<Float>,
    yAxisPosition: YAxisPosition,
    xAxisPosition: XAxisPosition,
    drawXAxis: Boolean,
    drawZero: Boolean = true,
    range: Float,
    textMeasurer: TextMeasurer
) {
    labels.reversed().forEachIndexed { index, label ->
        // y - calculates the proportion of the range that rangeDiff occupies and then scales that
        // difference to the DrawScope's height. For the y-axis, we then have to subtract that value from the height.
        val y = getY(
            height = size.height,
            yValues = yRangeValues,
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

        val xAxisPositionYValue = if (drawXAxis) {
            when (xAxisPosition) {
                XAxisPosition.BOTTOM -> size.height
                XAxisPosition.CENTER -> size.height.div(2f)
                XAxisPosition.TOP -> 0f
            }
        } else null

        if (config.showGuidelines) {
            if (drawXAxis && xAxisPositionYValue != y) {
                drawYGuideline(
                    guidelineConfig = config.guidelines,
                    y = y,
                    yAxisPosition = yAxisPosition
                )
            } else {
                drawYGuideline(
                    guidelineConfig = config.guidelines,
                    y = y,
                    yAxisPosition = yAxisPosition
                )
            }
        }

        if (!drawZero && label == 0f) return@forEachIndexed

        if (config.showLabels) {
            drawYFloatLabel(
                y = y,
                x = x,
                label = label,
                textMeasurer = textMeasurer,
                labelConfig = config.labels,
                yAxisPosition = yAxisPosition
            )
        }

        if (config.showAxisLine && config.axisLine.ticks) {
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
    yValues: Range<Float>,
    label: Float,
    range: Float,
    rangeAdj: Multiplier,
    index: Int,
    labelsSize: Int
): Float {
    val rangeDiff = calculateOffset(maxValue = yValues.max, offsetValue = label, range = range)
    val rangeAdjustment = if (yValues.min == 0f || yValues.max == 0f) 0f else rangeAdj.factor
    return if (yValues.max <= 0) {
        height
            .times(1f.minus(rangeAdjustment))
            .div(labelsSize.minus(1))
            .times(index)
            .plus(height.times(rangeAdjustment))
    } else height.minus(rangeDiff.div(range).times(height))
}