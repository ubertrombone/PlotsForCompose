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
import com.joshrose.plotsforcompose.util.calculateOffset

@ExperimentalTextApi
internal fun DrawScope.unboundYAxis(
    scale: Scale,
    labelConfigs: LabelsConfiguration,
    guidelinesConfigs: GuidelinesConfiguration,
    axisLineConfigs: AxisLineConfiguration.YConfiguration,
    labels: List<Number>?,
    labelIndices: List<Int>?,
    guidelines: List<Number>?,
    xAxisPosition: AxisPosition.XAxis,
    yAxisPosition: AxisPosition.YAxis,
    drawXAxis: Boolean,
    drawZero: Boolean = true,
    textMeasurer: TextMeasurer
) {
    val x = getX(yAxisPosition = yAxisPosition, width = size.width)
    val xAxisPositionYValue = getXAxisXPosition(drawXAxis = drawXAxis, xAxisPosition = xAxisPosition, height = size.height)
    val secondXAxisPositionYValue = if (xAxisPosition == AxisPosition.Both) 0f else null

    when {
        guidelines != null -> {
            drawYAxisUnbounded(
                x = x,
                xAxisPositionYValue = xAxisPositionYValue,
                secondXAxisPositionYValue = secondXAxisPositionYValue,
                guidelines = guidelines,
                labelIndices = labelIndices,
                drawXAxis = drawXAxis,
                drawZero = drawZero,
                yAxisPosition = yAxisPosition,
                guidelinesConfigs = guidelinesConfigs,
                labelConfigs = labelConfigs,
                axisLineConfigs = axisLineConfigs,
                textMeasurer = textMeasurer,
                scale = scale
            )
        }
        labels != null -> {
            drawYAxisUnbounded(
                x = x,
                labels = labels,
                drawZero = drawZero,
                yAxisPosition = yAxisPosition,
                labelConfigs = labelConfigs,
                axisLineConfigs = axisLineConfigs,
                textMeasurer = textMeasurer,
                scale = scale
            )
        }
    }

    if (scale.showAxisLine) drawYAxis(axisLineConfig = axisLineConfigs, yAxisPosition = yAxisPosition)
}

@ExperimentalTextApi
internal fun DrawScope.boundYAxis(
    scale: Scale,
    labelConfigs: LabelsConfiguration,
    guidelinesConfigs: GuidelinesConfiguration,
    axisLineConfigs: AxisLineConfiguration.YConfiguration,
    labelFactor: Float,
    guidelinesFactor: Float,
    labels: List<Any?>?,
    labelIndices: List<Int>?,
    guidelines: List<Any?>?,
    xAxisPosition: AxisPosition.XAxis,
    yAxisPosition: AxisPosition.YAxis,
    drawXAxis: Boolean,
    axisAlignment: AxisAlignment.YAxis,
    textMeasurer: TextMeasurer
) {
    val x = getX(yAxisPosition = yAxisPosition, width = size.width)
    val xAxisPositionYValue = getXAxisXPosition(drawXAxis = drawXAxis, xAxisPosition = xAxisPosition, height = size.height)
    val secondXAxisPositionYValue = if (xAxisPosition == AxisPosition.Both) 0f else null

    when {
        guidelines != null -> {
            drawYAxisBounded(
                x = x,
                xAxisPositionYValue = xAxisPositionYValue,
                secondXAxisPositionYValue = secondXAxisPositionYValue,
                guidelines = guidelines,
                labelIndices = labelIndices,
                guidelinesFactor = guidelinesFactor,
                drawXAxis = drawXAxis,
                axisAlignment = axisAlignment,
                yAxisPosition =yAxisPosition,
                guidelinesConfigs = guidelinesConfigs,
                labelConfigs = labelConfigs,
                axisLineConfigs = axisLineConfigs,
                textMeasurer = textMeasurer,
                scale = scale
            )
        }
        labels != null -> {
            drawYAxisBounded(
                x = x,
                labels = labels,
                labelFactor = labelFactor,
                axisAlignment = axisAlignment,
                yAxisPosition = yAxisPosition,
                labelConfigs = labelConfigs,
                axisLineConfigs = axisLineConfigs,
                textMeasurer = textMeasurer,
                scale = scale
            )
        }
    }

    if (scale.showAxisLine) drawYAxis(axisLineConfig = axisLineConfigs, yAxisPosition = yAxisPosition)
}

@OptIn(ExperimentalTextApi::class)
internal fun DrawScope.drawYAxisUnbounded(
    x: Float,
    xAxisPositionYValue: Float?,
    secondXAxisPositionYValue: Float?,
    guidelines: List<Number>,
    labelIndices: List<Int>?,
    drawXAxis: Boolean,
    drawZero: Boolean,
    yAxisPosition: AxisPosition.YAxis,
    guidelinesConfigs: GuidelinesConfiguration,
    labelConfigs: LabelsConfiguration,
    axisLineConfigs: AxisLineConfiguration.YConfiguration,
    textMeasurer: TextMeasurer,
    scale: Scale
) {
    val data = Range(guidelines.minOf { it.toFloat() }, guidelines.maxOf { it.toFloat() })
    val range = data.max.minus(data.min)

    guidelines.reversed().forEachIndexed { index, value ->
        val y = getY(
            height = size.height,
            yValues = data,
            label = value.toFloat(),
            range = range,
            rangeAdj = labelConfigs.rangeAdjustment,
            index = index,
            labelsSize = guidelines.size
        )

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

        if (!drawZero && value == 0f) return@forEachIndexed

        labelIndices?.let {
            if (it.contains(index)) {
                drawYLabel(
                    y = y,
                    x = x,
                    label = value,
                    yAxisPosition = yAxisPosition,
                    textMeasurer = textMeasurer,
                    labelConfig = labelConfigs
                )

                if (scale.showAxisLine && axisLineConfigs.ticks) {
                    drawYTick(
                        axisLineConfig = axisLineConfigs,
                        y = y,
                        yAxisPosition = yAxisPosition,
                        axisOffset = labelConfigs.axisOffset.toPx()
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalTextApi::class)
internal fun DrawScope.drawYAxisUnbounded(
    x: Float,
    labels: List<Number>,
    drawZero: Boolean,
    yAxisPosition: AxisPosition.YAxis,
    labelConfigs: LabelsConfiguration,
    axisLineConfigs: AxisLineConfiguration.YConfiguration,
    textMeasurer: TextMeasurer,
    scale: Scale
) {
    val data = Range(labels.minOf { it.toFloat() }, labels.maxOf { it.toFloat() })
    val range = data.max.minus(data.min)

    labels.reversed().forEachIndexed { index, label ->
        val y = getY(
            height = size.height,
            yValues = data,
            label = label.toFloat(),
            range = range,
            rangeAdj = labelConfigs.rangeAdjustment,
            index = index,
            labelsSize = labels.size
        )

        if (!drawZero && label == 0f) return@forEachIndexed

        drawYLabel(
            y = y,
            x = x,
            label = label,
            yAxisPosition = yAxisPosition,
            textMeasurer = textMeasurer,
            labelConfig = labelConfigs
        )

        if (scale.showAxisLine && axisLineConfigs.ticks) {
            drawYTick(
                axisLineConfig = axisLineConfigs,
                y = y,
                yAxisPosition = yAxisPosition,
                axisOffset = labelConfigs.axisOffset.toPx()
            )
        }
    }
}

@OptIn(ExperimentalTextApi::class)
internal fun DrawScope.drawYAxisBounded(
    x: Float,
    xAxisPositionYValue: Float?,
    secondXAxisPositionYValue: Float?,
    guidelines: List<Any?>,
    labelIndices: List<Int>?,
    guidelinesFactor: Float,
    drawXAxis: Boolean,
    axisAlignment: AxisAlignment.YAxis,
    yAxisPosition: AxisPosition.YAxis,
    guidelinesConfigs: GuidelinesConfiguration,
    labelConfigs: LabelsConfiguration,
    axisLineConfigs: AxisLineConfiguration.YConfiguration,
    textMeasurer: TextMeasurer,
    scale: Scale
) {
    guidelines.reversed().forEachIndexed { index, value ->
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

        labelIndices?.let {
            if (it.contains(index)) {
                drawYLabel(
                    y = y,
                    x = x,
                    label = value,
                    yAxisPosition = yAxisPosition,
                    textMeasurer = textMeasurer,
                    labelConfig = labelConfigs
                )

                if (scale.showAxisLine && axisLineConfigs.ticks) {
                    drawYTick(
                        axisLineConfig = axisLineConfigs,
                        y = y,
                        yAxisPosition = yAxisPosition,
                        axisOffset = labelConfigs.axisOffset.toPx()
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalTextApi::class)
internal fun DrawScope.drawYAxisBounded(
    x: Float,
    labels: List<Any?>,
    labelFactor: Float,
    axisAlignment: AxisAlignment.YAxis,
    yAxisPosition: AxisPosition.YAxis,
    labelConfigs: LabelsConfiguration,
    axisLineConfigs: AxisLineConfiguration.YConfiguration,
    textMeasurer: TextMeasurer,
    scale: Scale
) {
    labels.reversed().forEachIndexed { index, label ->
        val y =
            if (axisAlignment == AxisAlignment.Top || axisAlignment == AxisAlignment.SpaceBetween) index.times(labelFactor)
            else index.plus(1).times(labelFactor)

        drawYLabel(
            y = y,
            x = x,
            label = label,
            yAxisPosition = yAxisPosition,
            textMeasurer = textMeasurer,
            labelConfig = labelConfigs
        )

        if (scale.showAxisLine && axisLineConfigs.ticks) {
            drawYTick(
                axisLineConfig = axisLineConfigs,
                y = y,
                yAxisPosition = yAxisPosition,
                axisOffset = labelConfigs.axisOffset.toPx()
            )
        }
    }
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