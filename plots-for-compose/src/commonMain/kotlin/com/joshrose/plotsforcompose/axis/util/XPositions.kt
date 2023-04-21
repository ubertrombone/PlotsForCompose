package com.joshrose.plotsforcompose.axis.util

fun xPositions(
    width: Float,
    xMax: Float,
    xMin: Float,
    xOffset: Float
): XPositions {

    val xForAxis = when {
        xMax <= 0 -> width
        xMin < 0 -> width.div(2f)
        else -> 0f
    }

    val xForLabels =
        if (xMax <= 0) xForAxis.plus(xOffset)
        else xForAxis.minus(xOffset)

    return XPositions(labels = xForLabels, axis = xForAxis)
}

data class XPositions(val labels: Float, val axis: Float)
