package com.joshrose.common.components.linegraph.marker

import com.arkivanov.decompose.value.Value
import com.joshrose.common.components.linegraph.models.MarkerStates
import com.joshrose.plotsforcompose.util.Markers

interface MarkerComponent {
    val markerStates: Value<MarkerStates>

    fun updateShowMarkers(update: Boolean)
    fun updateMarkerShape(update: Markers)
    fun incMarkerSize()
    fun decMarkerSize()
}