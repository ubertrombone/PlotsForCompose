package com.joshrose.plotsforcompose.util

/**
 * Calculates the adjusted offset between the maxValue and the offsetValue.
 * currentDiff - gets the distance between maxValue and the current label.
 *
 * @param maxValue the maximum value along the relevant axis.
 * @param offsetValue the value to find the offset for.
 * @param range the adjusted range along the relevant axis.
 * @return gets the difference between the range and currentDiff.
 */
fun calculateOffset(
    maxValue: Float,
    offsetValue: Float,
    range: Float
): Float {
    val currentDiff = maxValue.minus(offsetValue)
    return range.minus(currentDiff)
}