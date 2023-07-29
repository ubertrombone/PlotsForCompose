package com.joshrose.common.components.linegraph.marker

import com.arkivanov.decompose.value.Value
import com.joshrose.common.components.linegraph.models.MarkerStates

interface MarkerComponent {
    val markerStates: Value<MarkerStates>

    fun updateShowMarkers(update: Boolean)
    fun incMarkerSize()
    fun decMarkerSize()
}