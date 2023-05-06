package com.joshrose.common.components.axes.models

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

@Parcelize
data class DataValueStates(
    val data: List<List<Float>> = listOf(listOf(0f, 3000f), listOf(100f, 3000f)),
    val maxXValue: Float? = null,
    val minXValue: Float? = null,
    val maxYValue: Float? = null,
    val minYValue: Float? = null,
    val xRange: Float? = null,
    val yRange: Float? = null
): Parcelable
