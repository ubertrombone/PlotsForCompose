package com.joshrose.plotsforcompose.util

data class Coordinates(val x: Float, val y: Float)

fun List<Coordinates>.maxXValue() = maxOf { it.x }
fun List<Coordinates>.maxYValue() = maxOf { it.y }
fun List<Coordinates>.minXValue() = minOf { it.x }
fun List<Coordinates>.minYValue() = minOf { it.y }
