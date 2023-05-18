package com.joshrose.plotsforcompose.linegraph.model

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

sealed class Data<T: Comparable<T>>(open val x: T, open val y: Float): Parcelable {
    @Parcelize
    data class FloatData(override val x: Float, override val y: Float): Data<Float>(x, y)

    @Parcelize
    data class IntData(override val x: Int, override val y: Float): Data<Int>(x, y)

    @Parcelize
    data class StringData(override val x: String, override val y: Float): Data<String>(x, y)

    companion object {
        fun List<Data<Float>>.lastXValue() = last().x
        fun List<Data<Int>>.lastXValue() = last().x
        fun List<Data<String>>.lastXValue() = last().x
        fun List<Data<*>>.maxYValue() = maxOf { it.y }
        fun List<Data<*>>.firstXValue() = first().x
        fun List<Data<*>>.minYValue() = minOf { it.y }
    }
}
