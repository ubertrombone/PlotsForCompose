package com.joshrose.common.components.axes.showaxes

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.joshrose.common.components.axes.models.AxesShowStates
import kotlinx.coroutines.*

class ShowAxesModelImpl(initialState: AxesShowStates): InstanceKeeper.Instance, ShowAxesModel {
    override val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    override val showAxesState: MutableValue<AxesShowStates> = MutableValue(initialState)

    override fun showAxis() {
        scope.launch {
            showAxesState.update { it.copy(showAxis = !it.showAxis) }
        }
    }

    override fun showAxisLine() {
        scope.launch {
            showAxesState.update { it.copy(showAxisLine = !it.showAxisLine) }
        }
    }

    override fun showGuidelines() {
        scope.launch {
            showAxesState.update { it.copy(showGuidelines = !it.showGuidelines) }
        }
    }

    override fun showLabels() {
        scope.launch {
            showAxesState.update { it.copy(showLabels = !it.showLabels) }
        }
    }

    override fun onDestroy() {
        scope.cancel()
    }
}