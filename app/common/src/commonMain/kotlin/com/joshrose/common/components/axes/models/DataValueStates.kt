package com.joshrose.common.components.axes.models

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

@Parcelize
data class DataValueStates(
    val data: List<List<Float>> = listOf(listOf(0f, 3000f), listOf(0f, 3000f)),
    val maxXValue: Float? = data.first().max(),
    val minXValue: Float? = data.first().min(),
    val maxYValue: Float? = data.last().max(),
    val minYValue: Float? = data.last().min(),
    val xRange: Float? = maxXValue?.minus(minXValue ?: 0f),
    val yRange: Float? = maxYValue?.minus(minYValue ?: 0f)
): Parcelable
