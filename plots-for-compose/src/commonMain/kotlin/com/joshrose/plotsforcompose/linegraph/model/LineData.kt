package com.joshrose.plotsforcompose.linegraph.model

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

@Parcelize
data class LineData(val x: Any?, val y: Float): Parcelable

fun List<LineData>.lastXValue() = last().x
fun List<LineData>.maxYValue() = maxOf { it.y }
fun List<LineData>.firstXValue() = first().x
fun List<LineData>.minYValue() = minOf { it.y }
