package com.joshrose.plotsforcompose.internals.util

import com.joshrose.plotsforcompose.axis.config.util.Multiplier
import kotlin.math.abs

internal fun minValue(
    minValue: Float,
    maxValue: Float,
    minValueAdjustment: Multiplier?
): Float {
    val adjustedMinValue = minValue.minus(abs(minValue.times(minValueAdjustment?.factor ?: 0f)))
    return if (minValue < 0 && maxValue > 0) maxValue.times(-1) else adjustedMinValue
}

internal fun maxValue(maxValue: Float, maxValueAdjustment: Multiplier?): Float =
    maxValue.plus(maxValue.times(maxValueAdjustment?.factor ?: 0f))

internal fun range(
    minValue: Float,
    maxValue: Float,
    rangeAdjustment: Multiplier?
): Float {
    val range = maxValue.minus(minValue)
    return when {
        minValue <= 0f && maxValue >= 0f -> range
        minValue == 0f || maxValue == 0f -> range
        else -> range.plus(range.times(rangeAdjustment?.factor ?: 0f))
    }
}