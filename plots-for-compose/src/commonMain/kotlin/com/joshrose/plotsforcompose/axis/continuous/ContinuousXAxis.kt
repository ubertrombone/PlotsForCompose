package com.joshrose.plotsforcompose.axis.continuous

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import com.joshrose.plotsforcompose.axis.config.ContinuousAxisConfig
import com.joshrose.plotsforcompose.axis.util.Range
import com.joshrose.plotsforcompose.axis.util.XPositions
import com.joshrose.plotsforcompose.axis.util.YPositions
import com.joshrose.plotsforcompose.util.calculateOffset
import com.joshrose.plotsforcompose.util.drawXFloatLabel

@Suppress("DuplicatedCode")
@OptIn(ExperimentalTextApi::class)
fun DrawScope.continuousXAxis(
    config: ContinuousAxisConfig,
    labels: List<Float>,
    xRangeValues: Range,
    xPositions: XPositions,
    yRangeValues: Range,
    yPositions: YPositions,
    range: Float,
    textMeasurer: TextMeasurer,
) {
    if (!config.showAxisLine && !config.showGuidelines && !config.showLabels) return

    labels.forEachIndexed { index, label ->
        if (yRangeValues.min < 0 && yRangeValues.max > 0 && label == 0f) return@forEachIndexed

        // x - calculates the proportion of the range that rangeDiff occupies and then scales that
        // difference to the DrawScope's width.
        val rangeDiff = calculateOffset(maxValue = xRangeValues.max, offsetValue = label, range = range)
        val x =
            if (xRangeValues.max <= 0)
                size.width
                    .times(1f.minus(config.labels.rangeAdjustment.factor))
                    .div(labels.size.minus(1))
                    .times(index)
            else rangeDiff.div(range).times(size.width)

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
            val lineLength = size.height.minus(config.guidelines.padding.toPx())
            val startY = when (yPositions.axis) {
                size.height -> 0f
                size.height.div(2) -> 0f
                else -> config.guidelines.padding.toPx()
            }
            val endY =
                if (yPositions.axis == size.height.div(2)) size.height
                else startY.plus(lineLength)

            drawLine(
                start = Offset(x = x, y = startY),
                end = Offset(x = x, y = endY),
                color = config.guidelines.lineColor,
                alpha = config.guidelines.alpha.factor,
                pathEffect = config.guidelines.pathEffect,
                strokeWidth = config.guidelines.strokeWidth.toPx()
            )
        }

        if (config.showAxisLine && config.axisLine.ticks) {
            val yOffset = config.labels.yOffset.toPx()
            val tickStart = when (yPositions.axis) {
                size.height -> yPositions.axis
                size.width.div(2f) -> yPositions.axis.minus(yOffset.div(4f))
                else -> yPositions.axis.minus(yOffset.div(2f))
            }
            val tickEnd = tickStart.plus(yOffset.div(2f))

            drawLine(
                start = Offset(x = x, y = tickStart),
                end = Offset(x = x, y = tickEnd),
                color = config.axisLine.lineColor,
                alpha = config.axisLine.alpha.factor,
                strokeWidth = config.axisLine.strokeWidth.toPx()
            )
        }
    }

    if (config.showAxisLine) {
        drawLine(
            start = Offset(x = 0f, y = yPositions.axis),
            end = Offset(x = size.width, y = yPositions.axis),
            color = config.axisLine.lineColor,
            alpha = config.axisLine.alpha.factor,
            pathEffect = config.axisLine.pathEffect,
            strokeWidth = config.axisLine.strokeWidth.toPx()
        )
    }
}