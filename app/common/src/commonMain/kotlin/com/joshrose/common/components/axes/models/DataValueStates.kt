package com.joshrose.common.components.axes.models

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.joshrose.plotsforcompose.util.*

@Parcelize
data class DataValueStates(
    val data: List<Coordinates> = listOf(Coordinates(0f, 0f), Coordinates(3000f, 3000f)),
    val maxXValue: Float? = data.maxXValue(),
    val minXValue: Float? = data.minXValue(),
    val maxYValue: Float? = data.maxYValue(),
    val minYValue: Float? = data.minYValue(),
    val xRange: Float? = maxXValue?.minus(minXValue ?: 0f),
    val yRange: Float? = maxYValue?.minus(minYValue ?: 0f)
): Parcelable
