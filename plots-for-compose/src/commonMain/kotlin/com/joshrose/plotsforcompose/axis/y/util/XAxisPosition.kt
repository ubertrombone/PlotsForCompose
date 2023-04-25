package com.joshrose.plotsforcompose.axis.y.util

import com.joshrose.plotsforcompose.axis.util.AxisPosition
import com.joshrose.plotsforcompose.axis.util.AxisPosition.*

fun xAxisPosition(
    height: Float,
    yMax: Float,
    yMin: Float,
    yOffset: Float
): XAxisPosition {
    val yForAxis = when {
        yMax <= 0 -> 0f
        yMin < 0 -> height.div(2f)
        else -> height
    }

    val yForLabels = if (yMax <= 0) yForAxis.minus(yOffset) else yForAxis.plus(yOffset)

    return XAxisPosition(labels = yForLabels, axis = yForAxis)
}

fun xAxisPosition(
    height: Float,
    yOffset: Float,
    axisPos: AxisPosition
): XAxisPosition {
    val axisLine = when (axisPos) {
        TOP_START -> 0f
        BOTTOM_END -> height
        CENTER -> height.div(2f)
    }

    val labelPosition = if (axisPos == TOP_START) axisLine.minus(yOffset) else axisLine.plus(yOffset)

    return XAxisPosition(labels = labelPosition, axis = axisLine)
}

data class XAxisPosition(val labels: Float, val axis: Float)