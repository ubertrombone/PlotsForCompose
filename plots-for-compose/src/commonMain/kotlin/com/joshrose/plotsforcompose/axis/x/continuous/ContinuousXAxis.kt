@file:Suppress("DuplicatedCode")

package com.joshrose.plotsforcompose.axis.x.continuous

import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import com.joshrose.plotsforcompose.axis.config.AxisConfiguration
import com.joshrose.plotsforcompose.axis.config.axisline.AxisLineConfiguration
import com.joshrose.plotsforcompose.axis.config.guidelines.GuidelinesConfiguration
import com.joshrose.plotsforcompose.axis.config.labels.LabelsConfiguration
import com.joshrose.plotsforcompose.axis.config.util.Multiplier
import com.joshrose.plotsforcompose.axis.util.AxisAlignment
import com.joshrose.plotsforcompose.axis.util.AxisAlignment.SpaceBetween
import com.joshrose.plotsforcompose.axis.util.AxisAlignment.Start
import com.joshrose.plotsforcompose.axis.util.AxisPosition
import com.joshrose.plotsforcompose.axis.util.AxisPosition.*
import com.joshrose.plotsforcompose.axis.util.Range
import com.joshrose.plotsforcompose.axis.util.Range.Companion.mapToFloat
import com.joshrose.plotsforcompose.axis.x.util.drawXAxis
import com.joshrose.plotsforcompose.axis.x.util.drawXGuideline
import com.joshrose.plotsforcompose.axis.x.util.drawXLabel
import com.joshrose.plotsforcompose.axis.x.util.drawXTick
import com.joshrose.plotsforcompose.internals.Scale
import com.joshrose.plotsforcompose.internals.ScaleKind
import com.joshrose.plotsforcompose.util.calculateOffset

// TODO: Move this somewhere else
fun unboundXAxis(
    labelConfigs: LabelsConfiguration,
    guidelinesConfigs: GuidelinesConfiguration,
    axisLineConfigs: AxisLineConfiguration.XConfiguration,
    breaks: List<Number>? = null,
    labels: List<String>? = null,
    naValue: Number? = null,
    reverse: Boolean? = null
) = Scale(
    labelConfigs = labelConfigs,
    guidelinesConfigs = guidelinesConfigs,
    axisLineConfigs = axisLineConfigs,
    scale = ScaleKind.X,
    breaks = breaks,
    labels = labels,
    naValue = naValue,
    reverse = reverse
)

@ExperimentalTextApi
fun DrawScope.unboundXAxis(
    labelConfigs: LabelsConfiguration,
    guidelinesConfigs: GuidelinesConfiguration,
    axisLineConfigs: AxisLineConfiguration.XConfiguration,
    labels: List<Number>,
    xRangeValues: Range<Number>,
    xAxisPosition: XAxis,
    yAxisPosition: YAxis,
    drawYAxis: Boolean,
    drawZero: Boolean = true,
    range: Number,
    textMeasurer: TextMeasurer
) {
    val y = getY(xAxisPosition = xAxisPosition, height = size.height)
    val yAxisPositionXValue = getYAxisXPosition(drawYAxis = drawYAxis, yAxisPosition = yAxisPosition, width = size.width)

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
            if (drawYAxis && yAxisPositionXValue != x) {
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
fun DrawScope.boundXAxis(
    config: AxisConfiguration.XConfiguration,
    labels: List<Any>,
    xAxisPosition: XAxis,
    yAxisPosition: YAxis,
    drawYAxis: Boolean,
    drawZero: Boolean = true,
    axisAlignment: AxisAlignment.XAxis,
    textMeasurer: TextMeasurer
) {
    // TODO: This can be hoisted to the graph composable and used for both axis and data.
    val factor = size.width.div(labels.size.plus(axisAlignment.offset).toFloat())

    val y = getY(xAxisPosition = xAxisPosition, height = size.height)
    val yAxisPositionXValue = getYAxisXPosition(drawYAxis = drawYAxis, yAxisPosition = yAxisPosition, width = size.width)

    labels.forEachIndexed { index, label ->
        val x =
            if (axisAlignment == Start || axisAlignment == SpaceBetween) index.times(factor)
            else index.plus(1).times(factor)

        if (config.showGuidelines) {
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

        if (!drawZero && label == 0f) return@forEachIndexed

        if (config.showLabels) {
            drawXLabel(
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

@Throws(IllegalStateException::class)
fun getY(xAxisPosition: XAxis, height: Float) = when (xAxisPosition) {
    Top -> 0f
    Bottom -> height
    Center -> height.div(2f)
    else -> throw IllegalStateException("xAxisPosition must be of type AxisPosition.XAxis. Current state: $xAxisPosition")
}

@Throws(IllegalStateException::class)
fun getYAxisXPosition(
    drawYAxis: Boolean,
    yAxisPosition: YAxis,
    width: Float
) = if (drawYAxis) {
    when (yAxisPosition) {
        AxisPosition.Start -> 0f
        Center -> width.div(2f)
        End -> width
        else -> throw IllegalStateException("yAxisPosition must be of type AxisPosition.YAxis. Current state: $yAxisPosition")
    }
} else null
