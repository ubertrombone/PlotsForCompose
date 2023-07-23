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
import com.joshrose.plotsforcompose.internals.Scale
import com.joshrose.plotsforcompose.internals.util.Range
import com.joshrose.plotsforcompose.internals.util.range
import com.joshrose.plotsforcompose.util.calculateOffset

@ExperimentalTextApi
internal fun DrawScope.unboundXAxis(
    scale: Scale,
    labelConfigs: LabelsConfiguration,
    guidelinesConfigs: GuidelinesConfiguration,
    axisLineConfigs: AxisLineConfiguration.XConfiguration,
    labels: List<Number>?,
    labelIndices: List<Int>?,
    guidelines: List<Number>?,
    xAxisPosition: AxisPosition.XAxis,
    yAxisPosition: AxisPosition.YAxis,
    drawYAxis: Boolean,
    drawZero: Boolean = true,
    textMeasurer: TextMeasurer
) {
    val y = getY(xAxisPosition = xAxisPosition, height = size.height)
    val yAxisPositionXValue = getYAxisXPosition(drawYAxis = drawYAxis, yAxisPosition = yAxisPosition, width = size.width)
    val secondYAxisPositionXValue = if (yAxisPosition == AxisPosition.Both) size.width else null

    when {
        guidelines != null -> {
            drawXAxisUnbounded(
                y = y,
                yAxisPositionXValue = yAxisPositionXValue,
                secondYAxisPositionXValue = secondYAxisPositionXValue,
                guidelines = guidelines,
                labelIndices = labelIndices,
                drawYAxis = drawYAxis,
                drawZero = drawZero,
                xAxisPosition = xAxisPosition,
                guidelinesConfigs = guidelinesConfigs,
                labelConfigs = labelConfigs,
                axisLineConfigs = axisLineConfigs,
                textMeasurer = textMeasurer,
                scale = scale
            )
        }
        labels != null -> {
            drawXAxisUnbounded(
                y = y,
                labels = labels,
                drawZero = drawZero,
                xAxisPosition = xAxisPosition,
                labelConfigs = labelConfigs,
                axisLineConfigs = axisLineConfigs,
                textMeasurer = textMeasurer,
                scale = scale
            )
        }
    }

    if (scale.showAxisLine) drawXAxisLine(axisLineConfig = axisLineConfigs, xAxisPosition = xAxisPosition)
}

@ExperimentalTextApi
internal fun DrawScope.boundXAxis(
    scale: Scale,
    labelConfigs: LabelsConfiguration,
    guidelinesConfigs: GuidelinesConfiguration,
    axisLineConfigs: AxisLineConfiguration.XConfiguration,
    labelFactor: Float,
    guidelinesFactor: Float,
    labels: List<Any?>?,
    labelIndices: List<Int>?,
    guidelines: List<Any?>?,
    xAxisPosition: AxisPosition.XAxis,
    yAxisPosition: AxisPosition.YAxis,
    drawYAxis: Boolean,
    axisAlignment: AxisAlignment.XAxis,
    textMeasurer: TextMeasurer
) {
    val y = getY(xAxisPosition = xAxisPosition, height = size.height)
    val yAxisPositionXValue = getYAxisXPosition(drawYAxis = drawYAxis, yAxisPosition = yAxisPosition, width = size.width)
    val secondYAxisPositionXValue = if (yAxisPosition == AxisPosition.Both) size.width else null

    when {
        guidelines != null -> {
            drawXAxisBounded(
                y = y,
                yAxisPositionXValue = yAxisPositionXValue,
                secondYAxisPositionXValue = secondYAxisPositionXValue,
                guidelines = guidelines,
                labelIndices = labelIndices,
                guidelinesFactor = guidelinesFactor,
                drawYAxis = drawYAxis,
                axisAlignment = axisAlignment,
                xAxisPosition = xAxisPosition,
                guidelinesConfigs = guidelinesConfigs,
                labelConfigs = labelConfigs,
                axisLineConfigs = axisLineConfigs,
                textMeasurer = textMeasurer,
                scale = scale
            )
        }
        labels != null -> {
            drawXAxisBounded(
                y = y,
                labels = labels,
                labelFactor = labelFactor,
                axisAlignment = axisAlignment,
                xAxisPosition = xAxisPosition,
                labelConfigs = labelConfigs,
                axisLineConfigs = axisLineConfigs,
                textMeasurer = textMeasurer,
                scale = scale
            )
        }
    }

    if (scale.showAxisLine) drawXAxisLine(axisLineConfig = axisLineConfigs, xAxisPosition = xAxisPosition)
}

@OptIn(ExperimentalTextApi::class)
internal fun DrawScope.drawXAxisUnbounded(
    y: Float,
    yAxisPositionXValue: Float?,
    secondYAxisPositionXValue: Float?,
    guidelines: List<Number>,
    labelIndices: List<Int>?,
    drawYAxis: Boolean,
    drawZero: Boolean,
    xAxisPosition: AxisPosition.XAxis,
    guidelinesConfigs: GuidelinesConfiguration,
    labelConfigs: LabelsConfiguration,
    axisLineConfigs: AxisLineConfiguration.XConfiguration,
    textMeasurer: TextMeasurer,
    scale: Scale
) {
    val data = Range(guidelines.minOf { it.toFloat() }, guidelines.maxOf { it.toFloat() })
    val range = range(
        minValue = data.min,
        maxValue = data.max,
        rangeAdjustment = labelConfigs.rangeAdjustment
    )

    guidelines.forEachIndexed { index, value ->
        val x = getX(
            width = size.width,
            xValues = data,
            label = value.toFloat(),
            range = range,
            rangeAdj = labelConfigs.rangeAdjustment,
            index = index,
            labelsSize = guidelines.size
        )

        if (drawYAxis) {
            if (yAxisPositionXValue != x || secondYAxisPositionXValue != x) {
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

        if (!drawZero && value == 0f) return@forEachIndexed

        labelIndices?.let {
            if (it.contains(index)) {
                drawXLabel(
                    y = y,
                    x = x,
                    label = value,
                    xAxisPosition = xAxisPosition,
                    textMeasurer = textMeasurer,
                    labelConfig = labelConfigs
                )

                if (scale.showAxisLine && axisLineConfigs.ticks) {
                    drawXTick(
                        axisLineConfig = axisLineConfigs,
                        x = x,
                        xAxisPosition = xAxisPosition,
                        axisOffset = labelConfigs.axisOffset.toPx()
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalTextApi::class)
internal fun DrawScope.drawXAxisUnbounded(
    y: Float,
    labels: List<Number>,
    drawZero: Boolean,
    xAxisPosition: AxisPosition.XAxis,
    labelConfigs: LabelsConfiguration,
    axisLineConfigs: AxisLineConfiguration.XConfiguration,
    textMeasurer: TextMeasurer,
    scale: Scale
) {
    val data = Range(labels.minOf { it.toFloat() }, labels.maxOf { it.toFloat() })
    val range = range(
        minValue = data.min,
        maxValue = data.max,
        rangeAdjustment = labelConfigs.rangeAdjustment
    )

    labels.forEachIndexed { index, label ->
        val x = getX(
            width = size.width,
            xValues = data,
            label = label.toFloat(),
            range = range,
            rangeAdj = labelConfigs.rangeAdjustment,
            index = index,
            labelsSize = labels.size
        )

        if (!drawZero && label == 0f) return@forEachIndexed

        drawXLabel(
            y = y,
            x = x,
            label = label,
            xAxisPosition = xAxisPosition,
            textMeasurer = textMeasurer,
            labelConfig = labelConfigs
        )

        if (scale.showAxisLine && axisLineConfigs.ticks) {
            drawXTick(
                axisLineConfig = axisLineConfigs,
                x = x,
                xAxisPosition = xAxisPosition,
                axisOffset = labelConfigs.axisOffset.toPx()
            )
        }
    }
}

@OptIn(ExperimentalTextApi::class)
internal fun DrawScope.drawXAxisBounded(
    y: Float,
    yAxisPositionXValue: Float?,
    secondYAxisPositionXValue: Float?,
    guidelines: List<Any?>,
    labelIndices: List<Int>?,
    guidelinesFactor: Float,
    drawYAxis: Boolean,
    axisAlignment: AxisAlignment.XAxis,
    xAxisPosition: AxisPosition.XAxis,
    guidelinesConfigs: GuidelinesConfiguration,
    labelConfigs: LabelsConfiguration,
    axisLineConfigs: AxisLineConfiguration.XConfiguration,
    textMeasurer: TextMeasurer,
    scale: Scale
) {
    guidelines.forEachIndexed { index, value ->
        val x =
            if (axisAlignment == AxisAlignment.Start || axisAlignment == AxisAlignment.SpaceBetween)
                index.times(guidelinesFactor)
            else index.plus(1).times(guidelinesFactor)

        if (drawYAxis) {
            if (yAxisPositionXValue != x || secondYAxisPositionXValue != x) {
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

        labelIndices?.let {
            if (it.contains(index)) {
                drawXLabel(
                    y = y,
                    x = x,
                    label = value,
                    xAxisPosition = xAxisPosition,
                    textMeasurer = textMeasurer,
                    labelConfig = labelConfigs
                )

                if (scale.showAxisLine && axisLineConfigs.ticks) {
                    drawXTick(
                        axisLineConfig = axisLineConfigs,
                        x = x,
                        xAxisPosition = xAxisPosition,
                        axisOffset = labelConfigs.axisOffset.toPx()
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalTextApi::class)
internal fun DrawScope.drawXAxisBounded(
    y: Float,
    labels: List<Any?>,
    labelFactor: Float,
    axisAlignment: AxisAlignment.XAxis,
    xAxisPosition: AxisPosition.XAxis,
    labelConfigs: LabelsConfiguration,
    axisLineConfigs: AxisLineConfiguration.XConfiguration,
    textMeasurer: TextMeasurer,
    scale: Scale
) {
    labels.forEachIndexed { index, label ->
        val x =
            if (axisAlignment == AxisAlignment.Start || axisAlignment == AxisAlignment.SpaceBetween)
                index.times(labelFactor)
            else index.plus(1).times(labelFactor)

        drawXLabel(
            y = y,
            x = x,
            label = label,
            xAxisPosition = xAxisPosition,
            textMeasurer = textMeasurer,
            labelConfig = labelConfigs
        )

        if (scale.showAxisLine && axisLineConfigs.ticks) {
            drawXTick(
                axisLineConfig = axisLineConfigs,
                x = x,
                xAxisPosition = xAxisPosition,
                axisOffset = labelConfigs.axisOffset.toPx()
            )
        }
    }
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