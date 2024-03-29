package com.joshrose.plotsforcompose.linegraph.model

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

// TODO: This isn't being used anywhere in the library -- remove or find a way to delete
@Parcelize
data class NumberData(val x: Number, val y: Float): Parcelable {
    companion object {
        fun List<NumberData>.maxXValue() = maxOf { it.x.toFloat() }
        fun List<NumberData>.maxYValue() = maxOf { it.y }
        fun List<NumberData>.minXValue() = minOf { it.x.toFloat() }
        fun List<NumberData>.minYValue() = minOf { it.y }
        fun List<NumberData>.toMap() = associate { it.x to it.y }
    }
}

@Parcelize
data class StringData(val x: String, val y: Float): Parcelable {
    companion object {
        fun List<StringData>.firstXValue() = first().x
        fun List<StringData>.lastXValue() = last().x
        fun List<StringData>.minYValue() = minOf { it.y }
        fun List<StringData>.maxYValue() = maxOf { it.y }
    }
}

