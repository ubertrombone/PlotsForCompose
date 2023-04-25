package com.joshrose.plotsforcompose.axis.x.util

import com.joshrose.plotsforcompose.axis.util.AxisPosition
import com.joshrose.plotsforcompose.axis.util.AxisPosition.*

fun yAxisPosition(
    width: Float,
    xMax: Float,
    xMin: Float,
    xOffset: Float
): YAxisPosition {
    val xForAxis = when {
        xMax <= 0 -> width
        xMin < 0 -> width.div(2f)
        else -> 0f
    }

    val xForLabels = if (xMax <= 0) xForAxis.plus(xOffset) else xForAxis.minus(xOffset)

    return YAxisPosition(labels = xForLabels, axis = xForAxis)
}

fun yAxisPosition(
    width: Float,
    xOffset: Float,
    axisPos: AxisPosition
): YAxisPosition {
    val axisLine = when (axisPos) {
        TOP_START -> width
        BOTTOM_END -> 0f
        CENTER -> width.div(2f)
    }

    val labelPosition = if (axisPos == TOP_START) axisLine.plus(xOffset) else axisLine.minus(xOffset)

    return YAxisPosition(labels = labelPosition, axis = axisLine)
}

data class YAxisPosition(val labels: Float, val axis: Float)
