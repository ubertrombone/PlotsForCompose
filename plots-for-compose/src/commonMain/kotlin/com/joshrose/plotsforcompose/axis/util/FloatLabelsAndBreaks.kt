package com.joshrose.plotsforcompose.axis.util

@Throws(IllegalArgumentException::class)
fun floatLabelsAndBreaks(
    amount: Int,
    minValue: Float,
    maxValue: Float
): List<Float> {
    require(minValue < maxValue) {
        """
        minValue and maxValue cannot be equal.
        - minValue: $minValue
        - maxValue: $maxValue
        """.trimIndent()
    }

    // if minValue is less than 0 and an even number, increase amount by 1 to ensure the center value is 0
    val centeredBreaks = if (amount.mod(2) == 0) amount.plus(1) else amount

    val steps =
        if (minValue < 0 && maxValue > 0) maxValue.div(amount.plus(if (amount.mod(2) == 0) 1 else 0).div(2))
        else maxValue.minus(minValue).div(amount.minus(1))

    return if (minValue < 0 && maxValue > 0) (0..centeredBreaks.minus(1)).map { minValue.plus(it.times(steps)) }
    else (0..amount.minus(1)).map { minValue.plus(it.times(steps)) }
}