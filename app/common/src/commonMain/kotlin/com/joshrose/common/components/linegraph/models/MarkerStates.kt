package com.joshrose.common.components.linegraph.models

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.joshrose.plotsforcompose.util.Markers
import com.joshrose.plotsforcompose.util.Markers.CIRCLE

@Parcelize
data class MarkerStates(
    var markers: Boolean = false,
    var markerSize: Float = 5f,
    var markerShape: Markers = CIRCLE
) : Parcelable
