package com.joshrose.plotsforcompose.linegraph.model

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

sealed class LineData<T: Comparable<T>>(open val x: T, open val y: Float): Parcelable {
    @Parcelize
    data class FloatData(override val x: Float, override val y: Float): LineData<Float>(x, y)

    @Parcelize
    data class IntData(override val x: Int, override val y: Float): LineData<Int>(x, y)

    @Parcelize
    data class StringData(override val x: String, override val y: Float): LineData<String>(x, y)

    companion object {
        fun List<LineData<*>>.lastXValue() = last().x
        fun List<LineData<*>>.maxYValue() = maxOf { it.y }
        fun List<LineData<*>>.firstXValue() = first().x
        fun List<LineData<*>>.minYValue() = minOf { it.y }
    }
}
