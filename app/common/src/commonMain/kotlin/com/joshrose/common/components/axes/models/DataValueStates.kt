package com.joshrose.common.components.axes.models

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.joshrose.plotsforcompose.linegraph.model.NumberData
import com.joshrose.plotsforcompose.linegraph.model.NumberData.Companion.maxXValue
import com.joshrose.plotsforcompose.linegraph.model.NumberData.Companion.maxYValue
import com.joshrose.plotsforcompose.linegraph.model.NumberData.Companion.minXValue
import com.joshrose.plotsforcompose.linegraph.model.NumberData.Companion.minYValue

// TODO: Make this some normal list!!!
@Parcelize
data class DataValueStates(
    val data: List<NumberData> = listOf(NumberData(100f, 0f), NumberData(150f, 100f), NumberData(400f, 200f), NumberData(500f, 2500f), NumberData(1500f, 2999f), NumberData(3000f, 3000f)),
    val maxXValue: Float? = data.maxXValue(),
    val minXValue: Float? = data.minXValue(),
    val maxYValue: Float? = data.maxYValue(),
    val minYValue: Float? = data.minYValue(),
    val xRange: Float? = maxXValue?.minus(minXValue ?: 0f),
    val yRange: Float? = maxYValue?.minus(minYValue ?: 0f)
): Parcelable
