package com.joshrose.plotsforcompose.axis.util

@Throws(IllegalArgumentException::class)
fun floatLabels(
    breaks: Int,
    minValue: Float,
    maxValue: Float
): List<Float> {
    if (minValue == maxValue) throw IllegalArgumentException(
        """
        minValue and maxValue cannot be equal.
        - minValue: $minValue
        - maxValue: $maxValue
        """.trimIndent()
    )

    // if finalMinValue is less than 0 and an even number, increase breaks by 1 to ensure the center break is 0
    val centeredBreaks = if (breaks.mod(2) == 0) breaks.plus(1) else breaks

    val steps =
        if (minValue < 0 && maxValue > 0) maxValue.div(breaks.plus(if (breaks.mod(2) == 0) 1 else 0).div(2))
        else maxValue.minus(minValue).div(breaks.minus(1))

    return when {
        minValue < 0 && maxValue > 0 -> (0..centeredBreaks.minus(1)).map { minValue.plus(it.times(steps)) }
        else -> (0..breaks.minus(1)).map { minValue.plus(it.times(steps)) }
    }
}