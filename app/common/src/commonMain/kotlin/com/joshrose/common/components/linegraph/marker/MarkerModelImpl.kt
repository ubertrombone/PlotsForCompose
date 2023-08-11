package com.joshrose.common.components.linegraph.marker

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.joshrose.common.components.linegraph.models.MarkerStates
import com.joshrose.plotsforcompose.util.Markers
import kotlinx.coroutines.*

class MarkerModelImpl(initialState: MarkerStates) : InstanceKeeper.Instance, MarkerModel {
    override val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    override val markerStates = MutableValue(initialState)

    override fun updateShowMarkers(update: Boolean) {
        scope.launch {
            markerStates.update { it.copy(markers = update) }
        }
    }

    override fun updateMarkerShape(update: Markers) {
        scope.launch {
            markerStates.update { it.copy(markerShape = update) }
        }
    }

    override fun incMarkerSize() {
        scope.launch {
            markerStates.update { it.copy(markerSize = it.markerSize.inc()) }
        }
    }

    override fun decMarkerSize() {
        scope.launch {
            markerStates.update { it.copy(markerSize = it.markerSize.dec()) }
        }
    }

    override fun resetMarker() {
        scope.launch {
            markerStates.update { MarkerStates() }
        }
    }

    override fun onDestroy() {
        scope.cancel()
    }
}