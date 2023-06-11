@file:Suppress("DuplicatedCode")

package com.joshrose.plotsforcompose.axis.y.continuous

import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import com.joshrose.plotsforcompose.axis.config.axisline.AxisLineConfiguration.YConfiguration
import com.joshrose.plotsforcompose.axis.config.guidelines.GuidelinesConfiguration
import com.joshrose.plotsforcompose.axis.config.labels.LabelsConfiguration
import com.joshrose.plotsforcompose.axis.config.util.Multiplier
import com.joshrose.plotsforcompose.axis.util.AxisAlignment
import com.joshrose.plotsforcompose.axis.util.AxisPosition.*
import com.joshrose.plotsforcompose.axis.util.Range
import com.joshrose.plotsforcompose.axis.util.Range.Companion.mapToFloat
import com.joshrose.plotsforcompose.axis.y.util.*
import com.joshrose.plotsforcompose.internals.Scale
import com.joshrose.plotsforcompose.internals.ScaleKind
import com.joshrose.plotsforcompose.util.calculateOffset

fun unboundYAxis(
    labelConfigs: LabelsConfiguration = LabelsConfiguration(),
    guidelinesConfigs: GuidelinesConfiguration = GuidelinesConfiguration(),
    axisLineConfigs: YConfiguration = YConfiguration(),
    breaks: List<Number>? = null,
    labels: List<String>? = null,
    naValue: Number? = null,
    reverse: Boolean? = null
) = Scale(
    labelConfigs = labelConfigs,
    guidelinesConfigs = guidelinesConfigs,
    axisLineConfigs = axisLineConfigs,
    scale = ScaleKind.Y,
    breaks = breaks,
    labels = labels,
    naValue = naValue,
    reverse = reverse
)

@ExperimentalTextApi
fun DrawScope.unboundYAxis(
    labelConfigs: LabelsConfiguration,
    guidelinesConfigs: GuidelinesConfiguration,
    axisLineConfigs: YConfiguration,
    labels: List<Number>,
    yRangeValues: Range<Number>,
    yAxisPosition: YAxis,
    xAxisPosition: XAxis,
    drawXAxis: Boolean,
    drawZero: Boolean = true,
    range: Number,
    textMeasurer: TextMeasurer
) {
    val x = getX(yAxisPosition = yAxisPosition, width = size.width)
    val xAxisPositionYValue = getXAxisXPosition(drawXAxis = drawXAxis, xAxisPosition = xAxisPosition, height = size.height)
    val secondXAxisPositionYValue = if (xAxisPosition == Both) 0f else null

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

        if (guidelinesConfigs.showGuidelines) {
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

        if (labelConfigs.showLabels) {
            drawYLabel(
                y = y,
                x = x,
                label = label,
                textMeasurer = textMeasurer,
                labelConfig = labelConfigs,
                yAxisPosition = yAxisPosition
            )
        }

        if (axisLineConfigs.showAxisLine && axisLineConfigs.ticks) {
            drawYTick(
                axisLineConfig = axisLineConfigs,
                y = y,
                yAxisPosition = yAxisPosition,
                axisOffset = labelConfigs.axisOffset.toPx()
            )
        }
    }

    if (axisLineConfigs.showAxisLine) drawYAxis(axisLineConfig = axisLineConfigs, yAxisPosition = yAxisPosition)
}

//@ExperimentalTextApi
//fun DrawScope.unboundYAxis(
//    config: AxisConfiguration.YConfiguration,
//    labels: List<Number>,
//    yRangeValues: Range<Number>,
//    yAxisPosition: YAxis,
//    xAxisPosition: XAxis,
//    drawXAxis: Boolean,
//    drawZero: Boolean = true,
//    range: Number,
//    textMeasurer: TextMeasurer
//) {
//    val x = getX(yAxisPosition = yAxisPosition, width = size.width)
//    val xAxisPositionYValue = getXAxisXPosition(drawXAxis = drawXAxis, xAxisPosition = xAxisPosition, height = size.height)
//
//    labels.reversed().forEachIndexed { index, label ->
//        // y - calculates the proportion of the range that rangeDiff occupies and then scales that
//        // difference to the DrawScope's height. For the y-axis, we then have to subtract that value from the height.
//        val y = getY(
//            height = size.height,
//            yValues = yRangeValues.mapToFloat(),
//            label = label.toFloat(),
//            range = range.toFloat(),
//            rangeAdj = config.labels.rangeAdjustment,
//            index = index,
//            labelsSize = labels.size
//        )
//
//        if (config.showGuidelines) {
//            if (drawXAxis) {
//                if (xAxisPositionYValue != y) {
//                    drawYGuideline(
//                        guidelineConfig = config.guidelines,
//                        y = y,
//                        yAxisPosition = yAxisPosition
//                    )
//                }
//            } else {
//                drawYGuideline(
//                    guidelineConfig = config.guidelines,
//                    y = y,
//                    yAxisPosition = yAxisPosition
//                )
//            }
//        }
//
//        if (!drawZero && label == 0f) return@forEachIndexed
//
//        if (config.showLabels) {
//            drawYLabel(
//                y = y,
//                x = x,
//                label = label,
//                textMeasurer = textMeasurer,
//                labelConfig = config.labels,
//                yAxisPosition = yAxisPosition
//            )
//        }
//
//        if (config.showAxisLine && config.axisLine.ticks) {
//            drawYTick(
//                axisLineConfig = config.axisLine,
//                y = y,
//                yAxisPosition = yAxisPosition,
//                axisOffset = config.labels.axisOffset.toPx()
//            )
//        }
//    }
//
//    if (config.showAxisLine) drawYAxis(axisLineConfig = config.axisLine, yAxisPosition = yAxisPosition)
//}

@ExperimentalTextApi
fun DrawScope.boundYAxis(
    labelConfigs: LabelsConfiguration,
    guidelinesConfigs: GuidelinesConfiguration,
    axisLineConfigs: YConfiguration,
    labels: List<Any>,
    yAxisPosition: YAxis,
    xAxisPosition: XAxis,
    drawXAxis: Boolean,
    drawZero: Boolean = true,
    axisAlignment: AxisAlignment.YAxis,
    textMeasurer: TextMeasurer
) {
    val factor = size.height.div(labels.size.plus(axisAlignment.offset).toFloat())

    val x = getX(yAxisPosition = yAxisPosition, width = size.width)
    val xAxisPositionYValue = getXAxisXPosition(drawXAxis = drawXAxis, xAxisPosition = xAxisPosition, height = size.height)
    val secondXAxisPositionYValue = if (xAxisPosition == Both) 0f else null

    labels.forEachIndexed { index, label ->
        val y =
            if (axisAlignment == AxisAlignment.Top || axisAlignment == AxisAlignment.SpaceBetween) index.times(factor)
            else index.plus(1).times(factor)

        if (guidelinesConfigs.showGuidelines) {
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

        if (labelConfigs.showLabels) {
            drawYLabel(
                y = y,
                x = x,
                label = label,
                textMeasurer = textMeasurer,
                labelConfig = labelConfigs,
                yAxisPosition = yAxisPosition
            )
        }

        if (axisLineConfigs.showAxisLine && axisLineConfigs.ticks) {
            drawYTick(
                axisLineConfig = axisLineConfigs,
                y = y,
                yAxisPosition = yAxisPosition,
                axisOffset = labelConfigs.axisOffset.toPx()
            )
        }
    }

    if (axisLineConfigs.showAxisLine) drawYAxis(axisLineConfig = axisLineConfigs, yAxisPosition = yAxisPosition)
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

@Throws(IllegalStateException::class)
fun getX(yAxisPosition: YAxis, width: Float) = when (yAxisPosition) {
    Start -> 0f
    Both -> 0f
    End -> width
    Center -> width.div(2f)
    else -> throw IllegalStateException("yAxisPosition must be of type AxisPosition.YAxis. Current state: $yAxisPosition")
}

@Throws(IllegalStateException::class)
fun getXAxisXPosition(
    drawXAxis: Boolean,
    xAxisPosition: XAxis,
    height: Float
) = if (drawXAxis) {
    when (xAxisPosition) {
        Bottom -> height
        Both -> height
        Center -> height.div(2f)
        Top -> 0f
        else -> throw IllegalStateException("xAxisPosition must be of type AxisPosition.XAxis. Current state: $xAxisPosition")
    }
} else null
