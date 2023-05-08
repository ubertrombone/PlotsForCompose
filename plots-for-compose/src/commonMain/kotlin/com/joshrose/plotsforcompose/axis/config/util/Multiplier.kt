package com.joshrose.plotsforcompose.axis.config.util

import com.arkivanov.essenty.parcelable.Parcelable

/**
 * A wrapper for [Float].
 *
 * This class limits possible [Float] values to values in the range of 0f to 1f.
 *
 * @property factor a [Float] value that must be between 0f and 1f.
 */
@JvmInline
value class Multiplier(val factor: Float): Parcelable {
    init {
        require(factor in 0f..1f) {
            "factor must be greater than equal to 0 and less than or equal to 1: $factor"
        }
    }

    operator fun plus(increment: Float): Multiplier = Multiplier(factor + increment)
    operator fun minus(decrement: Float): Multiplier = Multiplier(factor - decrement)
    operator fun times(value: Float): Multiplier = Multiplier(factor * value)
    operator fun div(value: Float): Multiplier = Multiplier(factor / value)
}

fun Float.toMultiplier() = Multiplier(this)
