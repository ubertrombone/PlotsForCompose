package com.joshrose.common.components.linegraph.label_marker

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.joshrose.common.components.linegraph.models.LabelMarkerStates
import com.joshrose.plotsforcompose.linegraph.util.Radius
import com.joshrose.plotsforcompose.linegraph.util.Style
import kotlinx.coroutines.*

class LabelMarkerModelImpl(initialState: LabelMarkerStates) : InstanceKeeper.Instance, LabelMarkerModel {
    override val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    override val labelMarkerStates = MutableValue(initialState)

    override fun updateRadius(radius: Radius) {
        scope.launch {
            labelMarkerStates.update { it.copy(radius = radius) }
        }
    }

    override fun updateStyle(style: Style) {
        scope.launch {
            labelMarkerStates.update { it.copy(style = style) }
        }
    }

    override fun resetLabelMarkers() {
        scope.launch {
            labelMarkerStates.update { LabelMarkerStates() }
        }
    }

    override fun onDestroy() {
        scope.cancel()
    }
}