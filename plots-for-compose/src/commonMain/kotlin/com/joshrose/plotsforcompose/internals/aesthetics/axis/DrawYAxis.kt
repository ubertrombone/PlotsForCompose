@file:Suppress("DuplicatedCode")

package com.joshrose.plotsforcompose.internals.aesthetics.axis

import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import com.joshrose.plotsforcompose.axis.config.axisline.AxisLineConfiguration
import com.joshrose.plotsforcompose.axis.config.guidelines.GuidelinesConfiguration
import com.joshrose.plotsforcompose.axis.config.labels.LabelsConfiguration
import com.joshrose.plotsforcompose.axis.config.util.Multiplier
import com.joshrose.plotsforcompose.axis.util.AxisAlignment
import com.joshrose.plotsforcompose.axis.util.AxisPosition
import com.joshrose.plotsforcompose.internals.util.Range
import com.joshrose.plotsforcompose.internals.util.Range.Companion.mapToFloat
import com.joshrose.plotsforcompose.util.calculateOffset

@ExperimentalTextApi
internal fun DrawScope.unboundYAxis(
    labelConfigs: LabelsConfiguration,
    guidelinesConfigs: GuidelinesConfiguration,
    axisLineConfigs: AxisLineConfiguration.YConfiguration,
    labels: List<Number>?,
    guidelines: List<Number>?,
    yRangeValues: Range<Number>,
    yAxisPosition: AxisPosition.YAxis,
    xAxisPosition: AxisPosition.XAxis,
    drawXAxis: Boolean,
    drawZero: Boolean = true,
    range: Number,
    textMeasurer: TextMeasurer
) {
    val x = getX(yAxisPosition = yAxisPosition, width = size.width)
    val xAxisPositionYValue = getXAxisXPosition(drawXAxis = drawXAxis, xAxisPosition = xAxisPosition, height = size.height)
    val secondXAxisPositionYValue = if (xAxisPosition == AxisPosition.Both) 0f else null

    labels?.let {
        labels.reversed().forEachIndexed { index, label ->
            // y - calculates the proportion of the range that rangeDiff occupies and then scales that
            // difference to the DrawScope's height. For the y-axis, we then have to subtract that value from the height.
            val y = getY(
                height = size.height,
                yValues = yRangeValues.mapToFloat(),
                label = label.toFloat(),
                range = range.toFloat(),
                rangeAdj = labelConfigs.rangeAdjustment,
                index = index,
                labelsSize = labels.size
            )

            //TODO: How to get the Y's to draw these separately?
            guidelines?.let {
                if (drawXAxis) {
                    if (xAxisPositionYValue != y || secondXAxisPositionYValue != y) {
                        drawYGuideline(
                            guidelineConfig = guidelinesConfigs,
                            y = y,
                            yAxisPosition = yAxisPosition
                        )
                    }
                } else {
                    drawYGuideline(
                        guidelineConfig = guidelinesConfigs,
                        y = y,
                        yAxisPosition = yAxisPosition
                    )
                }
            }

            if (!drawZero && label == 0f) return@forEachIndexed

            drawYLabel(
                y = y,
                x = x,
                label = label,
                textMeasurer = textMeasurer,
                labelConfig = labelConfigs,
                yAxisPosition = yAxisPosition
            )

            if (axisLineConfigs.showAxisLine && axisLineConfigs.ticks) {
                drawYTick(
                    axisLineConfig = axisLineConfigs,
                    y = y,
                    yAxisPosition = yAxisPosition,
                    axisOffset = labelConfigs.axisOffset.toPx()
                )
            }
        }
    }

    if (axisLineConfigs.showAxisLine) drawYAxis(axisLineConfig = axisLineConfigs, yAxisPosition = yAxisPosition)
}

// TODO: Test how changing axis location affects "Reversed"
@ExperimentalTextApi
internal fun DrawScope.boundYAxis(
    labelConfigs: LabelsConfiguration,
    guidelinesConfigs: GuidelinesConfiguration,
    axisLineConfigs: AxisLineConfiguration.YConfiguration,
    labelFactor: Float,
    guidelinesFactor: Float,
    labels: List<Any?>?,
    guidelines: List<Any?>?,
    yAxisPosition: AxisPosition.YAxis,
    xAxisPosition: AxisPosition.XAxis,
    drawXAxis: Boolean,
    axisAlignment: AxisAlignment.YAxis,
    textMeasurer: TextMeasurer
) {
    val x = getX(yAxisPosition = yAxisPosition, width = size.width)
    val xAxisPositionYValue = getXAxisXPosition(drawXAxis = drawXAxis, xAxisPosition = xAxisPosition, height = size.height)
    val secondXAxisPositionYValue = if (xAxisPosition == AxisPosition.Both) 0f else null

    guidelines?.let {
        it.reversed().forEachIndexed { index, _ ->
            val y =
                if (axisAlignment == AxisAlignment.Top || axisAlignment == AxisAlignment.SpaceBetween) index.times(guidelinesFactor)
                else index.plus(1).times(guidelinesFactor)

            if (drawXAxis) {
                if (xAxisPositionYValue != y || secondXAxisPositionYValue != y) {
                    drawYGuideline(
                        guidelineConfig = guidelinesConfigs,
                        y = y,
                        yAxisPosition = yAxisPosition
                    )
                }
            } else {
                drawYGuideline(
                    guidelineConfig = guidelinesConfigs,
                    y = y,
                    yAxisPosition = yAxisPosition
                )
            }
        }
    }

    labels?.let {
        it.reversed().forEachIndexed { index, label ->
            val y =
                if (axisAlignment == AxisAlignment.Top || axisAlignment == AxisAlignment.SpaceBetween) index.times(labelFactor)
                else index.plus(1).times(labelFactor)

            drawYLabel(
                y = y,
                x = x,
                label = label,
                textMeasurer = textMeasurer,
                labelConfig = labelConfigs,
                yAxisPosition = yAxisPosition
            )

            if (axisLineConfigs.showAxisLine && axisLineConfigs.ticks) {
                drawYTick(
                    axisLineConfig = axisLineConfigs,
                    y = y,
                    yAxisPosition = yAxisPosition,
                    axisOffset = labelConfigs.axisOffset.toPx()
                )
            }
        }
    }

    if (axisLineConfigs.showAxisLine) drawYAxis(axisLineConfig = axisLineConfigs, yAxisPosition = yAxisPosition)
}

internal fun getY(
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

@Throws(IllegalStateException::class)
internal fun getX(yAxisPosition: AxisPosition.YAxis, width: Float) = when (yAxisPosition) {
    AxisPosition.Start -> 0f
    AxisPosition.Both -> 0f
    AxisPosition.End -> width
    AxisPosition.Center -> width.div(2f)
    else -> throw IllegalStateException("yAxisPosition must be of type AxisPosition.YAxis. Current state: $yAxisPosition")
}

@Throws(IllegalStateException::class)
internal fun getXAxisXPosition(
    drawXAxis: Boolean,
    xAxisPosition: AxisPosition.XAxis,
    height: Float
) = if (drawXAxis) {
    when (xAxisPosition) {
        AxisPosition.Bottom -> height
        AxisPosition.Both -> height
        AxisPosition.Center -> height.div(2f)
        AxisPosition.Top -> 0f
        else -> throw IllegalStateException("xAxisPosition must be of type AxisPosition.XAxis. Current state: $xAxisPosition")
    }
} else null