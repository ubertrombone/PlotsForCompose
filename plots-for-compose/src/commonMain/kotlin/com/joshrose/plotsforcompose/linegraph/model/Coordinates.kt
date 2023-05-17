package com.joshrose.plotsforcompose.linegraph.model

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

sealed class Coordinates<T: Comparable<T>>(open val x: T, open val y: Float): Parcelable {
    @Parcelize
    data class FloatData(override val x: Float, override val y: Float): Coordinates<Float>(x, y)

    @Parcelize
    data class IntData(override val x: Int, override val y: Float): Coordinates<Int>(x, y)

    @Parcelize
    data class StringData(override val x: String, override val y: Float): Coordinates<String>(x, y)

    companion object {
        fun List<Coordinates<*>>.lastXValue() = last().x
        fun List<Coordinates<*>>.maxYValue() = maxOf { it.y }
        fun List<Coordinates<*>>.firstXValue() = first().x
        fun List<Coordinates<*>>.minYValue() = minOf { it.y }
    }
}
