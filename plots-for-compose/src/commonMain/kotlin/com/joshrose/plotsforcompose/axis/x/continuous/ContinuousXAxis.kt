@file:Suppress("DuplicatedCode")
@file:OptIn(ExperimentalTextApi::class)

package com.joshrose.plotsforcompose.axis.x.continuous

import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import com.joshrose.plotsforcompose.axis.config.ContinuousAxisConfig
import com.joshrose.plotsforcompose.axis.config.util.Multiplier
import com.joshrose.plotsforcompose.axis.util.AxisAlignment
import com.joshrose.plotsforcompose.axis.util.AxisAlignment.SpaceBetween
import com.joshrose.plotsforcompose.axis.util.AxisAlignment.Start
import com.joshrose.plotsforcompose.axis.util.AxisPosition
import com.joshrose.plotsforcompose.axis.util.AxisPosition.Bottom
import com.joshrose.plotsforcompose.axis.util.AxisPosition.Center
import com.joshrose.plotsforcompose.axis.util.AxisPosition.End
import com.joshrose.plotsforcompose.axis.util.AxisPosition.Top
import com.joshrose.plotsforcompose.axis.util.Range
import com.joshrose.plotsforcompose.axis.x.util.drawXAxis
import com.joshrose.plotsforcompose.axis.x.util.drawXFloatLabel
import com.joshrose.plotsforcompose.axis.x.util.drawXGuideline
import com.joshrose.plotsforcompose.axis.x.util.drawXTick
import com.joshrose.plotsforcompose.linegraph.model.StringData
import com.joshrose.plotsforcompose.util.calculateOffset

fun DrawScope.continuousXAxis(
    config: ContinuousAxisConfig,
    labels: List<Float>,
    xRangeValues: Range<Float>,
    xAxisPosition: AxisPosition.XAxis,
    yAxisPosition: AxisPosition.YAxis,
    drawYAxis: Boolean,
    drawZero: Boolean = true,
    range: Float,
    textMeasurer: TextMeasurer
) {
    labels.forEachIndexed { index, label ->
        val x = getX(
            width = size.width,
            xValues = xRangeValues,
            label = label,
            range = range,
            rangeAdj = config.labels.rangeAdjustment,
            index = index,
            labelsSize = labels.size
        )

        val y = when (xAxisPosition) {
            Top -> 0f
            Bottom -> size.height
            Center -> size.height.div(2f)
            else -> throw IllegalStateException("xAxisPosition must be of type AxisPosition.XAxis. Current state: $xAxisPosition")
        }

        val yAxisPositionXValue = if (drawYAxis) {
            when (yAxisPosition) {
                AxisPosition.Start -> 0f
                Center -> size.width.div(2f)
                End -> size.width
                else -> throw IllegalStateException("yAxisPosition must be of type AxisPosition.YAxis. Current state: $yAxisPosition")
            }
        } else null

        if (config.showGuidelines) {
            if (drawYAxis && yAxisPositionXValue != x) {
                drawXGuideline(
                    guidelineConfig = config.guidelines,
                    x = x,
                    xAxisPosition = xAxisPosition
                )
            } else {
                drawXGuideline(
                    guidelineConfig = config.guidelines,
                    x = x,
                    xAxisPosition = xAxisPosition
                )
            }
        }

        if (!drawZero && label == 0f) return@forEachIndexed

        if (config.showLabels) {
            drawXFloatLabel(
                y = y,
                x = x,
                label = label,
                xAxisPosition = xAxisPosition,
                textMeasurer = textMeasurer,
                labelConfig = config.labels
            )
        }

        if (config.showAxisLine && config.axisLine.ticks) {
            drawXTick(
                axisLineConfig = config.axisLine,
                x = x,
                xAxisPosition = xAxisPosition,
                axisOffset = config.labels.axisOffset.toPx()
            )
        }
    }

    if (config.showAxisLine) drawXAxis(axisLineConfig = config.axisLine, xAxisPosition = xAxisPosition)
}

// TODO: Make totalXValues have some meaning
fun DrawScope.boundXAxis(
    config: ContinuousAxisConfig,
    totalXValues: Int,
    labels: List<Number>,
    xAxisPosition: AxisPosition.XAxis,
    yAxisPosition: AxisPosition.YAxis,
    drawYAxis: Boolean,
    drawZero: Boolean = true,
    axisAlignment: AxisAlignment.XAxis,
    textMeasurer: TextMeasurer
) {
    val guidelinesFactor = size.width.div(totalXValues.plus(axisAlignment.offset).toFloat())
    val labelsFactor = size.width.div(labels.size.plus(axisAlignment.offset).toFloat())
    val numberLabel = labels.map { it.toString().toFloat() }

    val y = getY(xAxisPosition = xAxisPosition, height = size.height)
    val yAxisPositionXValue = getYAxisXPosition(drawYAxis = drawYAxis, yAxisPosition = yAxisPosition, width = size.width)

    // TODO: merge forEach statements and draw label when defined break is reached.
    numberLabel.forEachIndexed { index, label ->
        val x =
            if (axisAlignment == Start || axisAlignment == SpaceBetween) index.times(labelsFactor)
            else index.plus(1).times(labelsFactor)

        if (!drawZero && label == 0f) return@forEachIndexed

        if (config.showLabels) {
            drawXFloatLabel(
                y = y,
                x = x,
                label = label,
                xAxisPosition = xAxisPosition,
                textMeasurer = textMeasurer,
                labelConfig = config.labels
            )
        }

        if (config.showAxisLine && config.axisLine.ticks) {
            drawXTick(
                axisLineConfig = config.axisLine,
                x = x,
                xAxisPosition = xAxisPosition,
                axisOffset = config.labels.axisOffset.toPx()
            )
        }

    }

    if (config.showGuidelines) {
        (0 until totalXValues).forEachIndexed { index, _ ->
            val x =
                if (axisAlignment == Start || axisAlignment == SpaceBetween) index.times(guidelinesFactor)
                else index.plus(1).times(guidelinesFactor)

            if (drawYAxis) {
                if (yAxisPositionXValue != x) {
                    drawXGuideline(
                        guidelineConfig = config.guidelines,
                        x = x,
                        xAxisPosition = xAxisPosition
                    )
                }
            } else {
                drawXGuideline(
                    guidelineConfig = config.guidelines,
                    x = x,
                    xAxisPosition = xAxisPosition
                )
            }
        }
    }

    if (config.showAxisLine) drawXAxis(axisLineConfig = config.axisLine, xAxisPosition = xAxisPosition)
}

// TODO: Maybe range isn't needed at all? Then labels can take List<T> where T: Parcelable?
fun DrawScope.boundXAxis(
    config: ContinuousAxisConfig,
    totalXValues: Int,
    labels: List<StringData>,
    xAxisPosition: AxisPosition.XAxis,
    yAxisPosition: AxisPosition.YAxis,
    drawYAxis: Boolean,
    drawZero: Boolean = true,
    range: Number,
    textMeasurer: TextMeasurer
) {

}

fun DrawScope.unboundXAxis(
    config: ContinuousAxisConfig,
    labels: List<Number>,
    xRangeValues: Range<Number>,
    xAxisPosition: AxisPosition.XAxis,
    yAxisPosition: AxisPosition.YAxis,
    drawYAxis: Boolean,
    drawZero: Boolean = true,
    range: Number,
    textMeasurer: TextMeasurer
) {

}

fun DrawScope.unboundXAxis(
    config: ContinuousAxisConfig,
    labels: List<StringData>,
    xAxisPosition: AxisPosition.XAxis,
    yAxisPosition: AxisPosition.YAxis,
    drawYAxis: Boolean,
    drawZero: Boolean = true,
    range: StringData,
    textMeasurer: TextMeasurer
) {

}

fun getX(
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

fun getY(
    xAxisPosition: AxisPosition.XAxis,
    height: Float
): Float {
    return when (xAxisPosition) {
        Top -> 0f
        Bottom -> height
        Center -> height.div(2f)
        else -> throw IllegalStateException("xAxisPosition must be of type AxisPosition.XAxis. Current state: $xAxisPosition")
    }
}

fun getYAxisXPosition(
    drawYAxis: Boolean,
    yAxisPosition: AxisPosition.YAxis,
    width: Float
): Float? {
    return if (drawYAxis) {
        when (yAxisPosition) {
            AxisPosition.Start -> 0f
            Center -> width.div(2f)
            End -> width
            else -> throw IllegalStateException("yAxisPosition must be of type AxisPosition.YAxis. Current state: $yAxisPosition")
        }
    } else null
}