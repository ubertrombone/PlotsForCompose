package com.joshrose.plotsforcompose.axis.continuous

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import com.joshrose.plotsforcompose.axis.config.ContinuousAxisConfig
import com.joshrose.plotsforcompose.axis.util.Range
import com.joshrose.plotsforcompose.axis.util.XPositions
import com.joshrose.plotsforcompose.util.calculateOffset
import com.joshrose.plotsforcompose.util.drawYFloatLabel

@Suppress("DuplicatedCode")
@OptIn(ExperimentalTextApi::class)
fun DrawScope.continuousYAxis(
    config: ContinuousAxisConfig,
    labels: List<Float>,
    xPositions: XPositions,
    maxYValue: Float,
    xRangeValues: Range,
    range: Float,
    textMeasurer: TextMeasurer,
) {
    if (!config.showAxisLine && !config.showGuidelines && !config.showLabels) return

    labels.forEach { label ->
        if (xRangeValues.min < 0 && xRangeValues.max > 0 && label == 0f) return@forEach

        // y - calculates the proportion of the range that rangeDiff occupies and then scales that
        // difference to the DrawScope's height. For the y-axis, we then have to subtract that value from the height.
        val rangeDiff = calculateOffset(maxValue = maxYValue, offsetValue = label, range = range)
        val y = size.height.minus(rangeDiff.div(range).times(size.height))

        if (config.showLabels) {
            drawYFloatLabel(
                y = y,
                x = xPositions.labels,
                label = label,
                maxXValue = xRangeValues.max,
                textMeasurer = textMeasurer,
                labelConfig = config.labels
            )
        }

        if (config.showGuidelines) {
            val lineLength = size.width.minus(config.guidelines.padding.toPx())
            val startX = when (xPositions.axis) {
                size.width -> 0f
                size.width.div(2) -> 0f
                else -> config.guidelines.padding.toPx()
            }
            val endX =
                if (xPositions.axis == size.width.div(2)) size.width
                else startX.plus(lineLength)

            drawLine(
                start = Offset(x = startX, y = y),
                end = Offset(x = endX, y = y),
                color = config.guidelines.lineColor,
                alpha = config.guidelines.alpha.factor,
                pathEffect = config.guidelines.pathEffect,
                strokeWidth = config.guidelines.strokeWidth.toPx()
            )
        }

        if (config.showAxisLine && config.axisLine.ticks) {
            val xOffset = config.labels.xOffset.toPx()
            val tickStart = when (xPositions.axis) {
                size.width -> xPositions.axis
                size.width.div(2f) -> xPositions.axis.minus(xOffset.div(4f))
                else -> xPositions.axis.minus(xOffset.div(2f))
            }
            val tickEnd = tickStart.plus(xOffset.div(2f))

            drawLine(
                start = Offset(x = tickStart, y = y),
                end = Offset(x = tickEnd, y = y),
                color = config.axisLine.lineColor,
                alpha = config.axisLine.alpha.factor,
                strokeWidth = config.axisLine.strokeWidth.toPx()
            )
        }
    }

    if (config.showAxisLine) {
        drawLine(
            start = Offset(x = xPositions.axis, y = 0f),
            end = Offset(x = xPositions.axis, y = size.height),
            color = config.axisLine.lineColor,
            alpha = config.axisLine.alpha.factor,
            pathEffect = config.axisLine.pathEffect,
            strokeWidth = config.axisLine.strokeWidth.toPx()
        )
    }
}