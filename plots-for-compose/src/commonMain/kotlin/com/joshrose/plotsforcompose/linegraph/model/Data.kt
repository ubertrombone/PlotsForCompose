package com.joshrose.plotsforcompose.linegraph.model

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

// TODO: Remove generics, have data class NumberData(val x: Number, val y: Float)... and data class StringData(val x: String, val y: Float)...
// TODO: Make overload LineGraph to deal with these two types.
// TODO: Remove generics for DrawScope functions, use Number or String instead.

sealed class Data<T>(open val x: T, open val y: Float): Parcelable {
    @Parcelize
    data class FloatData(override val x: Float, override val y: Float): Data<Float>(x, y)

    @Parcelize
    data class DoubleData(override val x: Double, override val y: Float): Data<Double>(x, y)

    @Parcelize
    data class IntData(override val x: Int, override val y: Float): Data<Int>(x, y)

    @Parcelize
    data class LongData(override val x: Long, override val y: Float): Data<Long>(x, y)

    @Parcelize
    data class BooleanData(override val x: Boolean, override val y: Float): Data<Boolean>(x, y)

    @Parcelize
    data class StringData(override val x: String, override val y: Float): Data<String>(x, y)

    @Parcelize
    data class CharData(override val x: Char, override val y: Float): Data<Char>(x, y)

    companion object {
        fun <T> List<Data<T>>.lastXValue() = last().x
        fun <T> List<Data<T>>.maxYValue() = maxOf { it.y }
        fun <T> List<Data<T>>.firstXValue() = first().x
        fun <T> List<Data<T>>.minYValue() = minOf { it.y }
    }
}
