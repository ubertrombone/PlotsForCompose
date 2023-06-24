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
internal fun DrawScope.unboundXAxis(
    labelConfigs: LabelsConfiguration,
    guidelinesConfigs: GuidelinesConfiguration,
    axisLineConfigs: AxisLineConfiguration.XConfiguration,
    labels: List<Number>,
    xRangeValues: Range<Number>,
    xAxisPosition: AxisPosition.XAxis,
    yAxisPosition: AxisPosition.YAxis,
    drawYAxis: Boolean,
    drawZero: Boolean = true,
    range: Number,
    textMeasurer: TextMeasurer
) {
    val y = getY(xAxisPosition = xAxisPosition, height = size.height)
    val yAxisPositionXValue = getYAxisXPosition(drawYAxis = drawYAxis, yAxisPosition = yAxisPosition, width = size.width)
    val secondYAxisPositionXValue = if (yAxisPosition == AxisPosition.Both) size.width else null

    labels.forEachIndexed { index, label ->
        val x = getX(
            width = size.width,
            xValues = xRangeValues.mapToFloat(),
            label = label.toFloat(),
            range = range.toFloat(),
            rangeAdj = labelConfigs.rangeAdjustment,
            index = index,
            labelsSize = labels.size
        )

        if (guidelinesConfigs.showGuidelines) {
            if (drawYAxis) {
                if (yAxisPositionXValue != x || secondYAxisPositionXValue != x) {
                    drawXGuideline(
                        guidelineConfig = guidelinesConfigs,
                        x = x,
                        xAxisPosition = xAxisPosition
                    )
                } else {
                    drawXGuideline(
                        guidelineConfig = guidelinesConfigs,
                        x = x,
                        xAxisPosition = xAxisPosition
                    )
                }
            }
        }

        if (!drawZero && label == 0f) return@forEachIndexed

        if (labelConfigs.showLabels) {
            drawXLabel(
                y = y,
                x = x,
                label = label,
                xAxisPosition = xAxisPosition,
                textMeasurer = textMeasurer,
                labelConfig = labelConfigs
            )
        }

        if (axisLineConfigs.showAxisLine && axisLineConfigs.ticks) {
            drawXTick(
                axisLineConfig = axisLineConfigs,
                x = x,
                xAxisPosition = xAxisPosition,
                axisOffset = labelConfigs.axisOffset.toPx()
            )
        }
    }

    if (axisLineConfigs.showAxisLine) drawXAxis(axisLineConfig = axisLineConfigs, xAxisPosition = xAxisPosition)
}

// TODO: Consider how drawing all/some/none guidelines and all/some/none labels might go
// --> Bound XAxis doesn't need breaks. It needs a list of the labels to be shown.
@ExperimentalTextApi
internal fun DrawScope.boundXAxis(
    labelConfigs: LabelsConfiguration,
    guidelinesConfigs: GuidelinesConfiguration,
    axisLineConfigs: AxisLineConfiguration.XConfiguration,
    labelFactor: Float,
    guidelinesFactor: Float,
    labels: List<Any?>?,
    guidelines: List<Any?>?,
    xAxisPosition: AxisPosition.XAxis,
    yAxisPosition: AxisPosition.YAxis,
    drawYAxis: Boolean,
    axisAlignment: AxisAlignment.XAxis,
    textMeasurer: TextMeasurer
) {
    val y = getY(xAxisPosition = xAxisPosition, height = size.height)
    val yAxisPositionXValue = getYAxisXPosition(drawYAxis = drawYAxis, yAxisPosition = yAxisPosition, width = size.width)
    val secondYAxisPositionYValue = if (yAxisPosition == AxisPosition.Both) size.width else null

    guidelines?.let {
        it.forEachIndexed { index, _ ->
            val x =
                if (axisAlignment == AxisAlignment.Start || axisAlignment == AxisAlignment.SpaceBetween)
                    index.times(guidelinesFactor)
                else index.plus(1).times(guidelinesFactor)

            if (drawYAxis) {
                if (yAxisPositionXValue != x || secondYAxisPositionYValue != x) {
                    drawXGuideline(
                        guidelineConfig = guidelinesConfigs,
                        x = x,
                        xAxisPosition = xAxisPosition
                    )
                }
            } else {
                drawXGuideline(
                    guidelineConfig = guidelinesConfigs,
                    x = x,
                    xAxisPosition = xAxisPosition
                )
            }
        }
    }

    labels?.let {
        it.forEachIndexed { index, label ->
            val x =
                if (axisAlignment == AxisAlignment.Start || axisAlignment == AxisAlignment.SpaceBetween)
                    index.times(labelFactor)
                else index.plus(1).times(labelFactor)

            if (labelConfigs.showLabels) {
                drawXLabel(
                    y = y,
                    x = x,
                    label = label,
                    xAxisPosition = xAxisPosition,
                    textMeasurer = textMeasurer,
                    labelConfig = labelConfigs
                )
            }

            if (axisLineConfigs.showAxisLine && axisLineConfigs.ticks) {
                drawXTick(
                    axisLineConfig = axisLineConfigs,
                    x = x,
                    xAxisPosition = xAxisPosition,
                    axisOffset = labelConfigs.axisOffset.toPx()
                )
            }
        }
    }

    if (axisLineConfigs.showAxisLine) drawXAxis(axisLineConfig = axisLineConfigs, xAxisPosition = xAxisPosition)
}

internal fun getX(
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

@Throws(IllegalStateException::class)
internal fun getY(xAxisPosition: AxisPosition.XAxis, height: Float) = when (xAxisPosition) {
    AxisPosition.Top -> 0f
    AxisPosition.Bottom -> height
    AxisPosition.Both -> height
    AxisPosition.Center -> height.div(2f)
    else -> throw IllegalStateException("xAxisPosition must be of type AxisPosition.XAxis. Current state: $xAxisPosition")
}

@Throws(IllegalStateException::class)
internal fun getYAxisXPosition(
    drawYAxis: Boolean,
    yAxisPosition: AxisPosition.YAxis,
    width: Float
) = if (drawYAxis) {
    when (yAxisPosition) {
        AxisPosition.Start -> 0f
        AxisPosition.Both -> 0f
        AxisPosition.Center -> width.div(2f)
        AxisPosition.End -> width
        else -> throw IllegalStateException("yAxisPosition must be of type AxisPosition.YAxis. Current state: $yAxisPosition")
    }
} else null