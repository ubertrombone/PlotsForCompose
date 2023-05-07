package com.joshrose.common.components.axes.visibility

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.joshrose.common.components.axes.models.VisibilityStates
import kotlinx.coroutines.*

class VisibilityModelImpl(initialState: VisibilityStates): InstanceKeeper.Instance, VisibilityModel {
    override val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    override val visibilityState: MutableValue<VisibilityStates> = MutableValue(initialState)

    override fun showAxis() {
        scope.launch {
            visibilityState.update { it.copy(showAxis = !it.showAxis) }
        }
    }

    override fun showAxisLine() {
        scope.launch {
            visibilityState.update { it.copy(showAxisLine = !it.showAxisLine) }
        }
    }

    override fun showGuidelines() {
        scope.launch {
            visibilityState.update { it.copy(showGuidelines = !it.showGuidelines) }
        }
    }

    override fun showLabels() {
        scope.launch {
            visibilityState.update { it.copy(showLabels = !it.showLabels) }
        }
    }

    override fun onDestroy() {
        scope.cancel()
    }
}