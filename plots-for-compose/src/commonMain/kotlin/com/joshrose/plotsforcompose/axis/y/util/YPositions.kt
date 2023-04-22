package com.joshrose.plotsforcompose.axis.y.util

import com.joshrose.plotsforcompose.axis.x.util.XAxisPos
import com.joshrose.plotsforcompose.axis.x.util.XAxisPos.*

fun yPositions(
    height: Float,
    yMax: Float,
    yMin: Float,
    yOffset: Float
): YPositions {

    val yForAxis = when {
        yMax <= 0 -> 0f
        yMin < 0 -> height.div(2f)
        else -> height
    }

    val yForLabels =
        if (yMax <= 0) yForAxis.minus(yOffset)
        else yForAxis.plus(yOffset)

    return YPositions(labels = yForLabels, axis = yForAxis)
}

fun yPositions(
    height: Float,
    yOffset: Float,
    axisPos: XAxisPos
): YPositions {
    val axisLine = when (axisPos) {
        CENTER -> height.div(2f)
        TOP -> 0f
        BOTTOM -> height
    }
    val labelPosition = if (axisPos == TOP) axisLine.minus(yOffset) else axisLine.plus(yOffset)

    return YPositions(labels = labelPosition, axis = axisLine)
}

data class YPositions(val labels: Float, val axis: Float)