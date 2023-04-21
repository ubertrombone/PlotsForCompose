package com.joshrose.plotsforcompose.axis.util

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

data class YPositions(val labels: Float, val axis: Float)