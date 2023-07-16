package com.joshrose.plotsforcompose.util

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

/**
 * A wrapper for [Float].
 *
 * This class limits possible [Float] values to values in the range of 0f to 0.5f.
 *
 * @property factor a [Float] value that must be between 0f and 1f.
 */
@Parcelize
@JvmInline
value class Proportional(val factor: Float): Parcelable {
    init {
        require(factor != 0f) { "factor must not be 0f." }
        require(factor in 0f..0.5f) { "factor must be greater than 0f and less than or equal to 0.5f: $factor" }
    }

    operator fun plus(increment: Float): Proportional = Proportional(factor.plus(increment).coerceAtMost(0.5f))
    operator fun minus(decrement: Float): Proportional? =
        try { Proportional(factor.minus(decrement)) } catch (e: IllegalArgumentException) { null }
}
