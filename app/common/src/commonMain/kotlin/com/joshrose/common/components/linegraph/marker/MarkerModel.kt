package com.joshrose.common.components.linegraph.marker

import com.arkivanov.decompose.value.MutableValue
import com.joshrose.common.components.linegraph.models.MarkerStates
import kotlinx.coroutines.CoroutineScope

interface MarkerModel {
    val scope: CoroutineScope
    val markerStates: MutableValue<MarkerStates>

    fun updateShowMarkers(update: Boolean)
    fun incMarkerSize()
    fun decMarkerSize()
    fun resetMarker()
}