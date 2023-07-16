package com.joshrose.common.components.axes.breaks

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.joshrose.common.components.axes.models.BreakStates
import com.joshrose.plotsforcompose.util.Proportional
import kotlinx.coroutines.*

class BreaksModelImpl(initialState: BreakStates): InstanceKeeper.Instance, BreaksModel {
    override val scope: CoroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    override val breaksState: MutableValue<BreakStates> = MutableValue(initialState)

    override fun incBreaks() {
        scope.launch {
            breaksState.update { it.copy(breaks = it.breaks?.plus(.1f) ?: Proportional(.1f)) }
        }
    }

    override fun decBreaks() {
        scope.launch {
            breaksState.update { it.copy(breaks = it.breaks?.minus(.1f)) }
        }
    }

    override fun incLabels() {
        scope.launch {
            breaksState.update { it.copy(labels = it.labels?.plus(.1f) ?: Proportional(.1f)) }
        }
    }

    override fun decLabels() {
        scope.launch {
            breaksState.update { it.copy(labels = it.labels?.minus(.1f)) }
        }
    }

    override fun resetLabels() {
        scope.launch {
            breaksState.update { BreakStates() }
        }
    }

    override fun onDestroy() {
        scope.cancel()
    }
}