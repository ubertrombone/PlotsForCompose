package com.joshrose.plotsforcompose.axis.config.util

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

/**
 * A wrapper for [Float].
 *
 * This class limits possible [Float] values to values in the range of 0f to 1f.
 *
 * @property factor a [Float] value that must be between 0f and 1f.
 */
@Parcelize
@JvmInline
value class Multiplier(val factor: Float): Parcelable {
    init {
        require(factor in 0f..1f) {
            "factor must be greater than or equal to 0f and less than or equal to 1f: $factor"
        }
    }

    operator fun plus(increment: Float): Multiplier = Multiplier(factor.plus(increment).coerceAtMost(1f))
    operator fun minus(decrement: Float): Multiplier = Multiplier(factor.minus(decrement).coerceAtLeast(0f))
//    operator fun times(value: Float): Multiplier = Multiplier(factor * value)
//    operator fun div(value: Float): Multiplier = Multiplier(factor / value)
}
