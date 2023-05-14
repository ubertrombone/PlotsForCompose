package com.joshrose.plotsforcompose.util

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

@Parcelize
data class Coordinates(val x: Float, val y: Float): Parcelable

fun List<Coordinates>.maxXValue() = maxOf { it.x }
fun List<Coordinates>.maxYValue() = maxOf { it.y }
fun List<Coordinates>.minXValue() = minOf { it.x }
fun List<Coordinates>.minYValue() = minOf { it.y }
