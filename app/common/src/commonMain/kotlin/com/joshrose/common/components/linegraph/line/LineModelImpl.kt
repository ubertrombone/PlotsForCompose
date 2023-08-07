package com.joshrose.common.components.linegraph.line

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.joshrose.common.components.linegraph.models.LineStates
import com.joshrose.common.ui.linegraph.line.Join
import com.joshrose.plotsforcompose.linegraph.util.LineType
import kotlinx.coroutines.*

class LineModelImpl(initialState: LineStates) : InstanceKeeper.Instance, LineModel {
    override val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    override val lineStates = MutableValue(initialState)

    override fun updateType(change: LineType) {
        scope.launch {
            lineStates.update { it.copy(lineType = change) }
        }
    }

    override fun updateJoin(change: Join) {
        scope.launch {
            lineStates.update { it.copy(strokeJoin = change) }
        }
    }

    override fun incStrokeWidth() {
        scope.launch {
            lineStates.update { it.copy(strokeWidth = it.strokeWidth.inc()) }
        }
    }

    override fun decStrokeWidth() {
        scope.launch {
            lineStates.update { it.copy(strokeWidth = it.strokeWidth.dec()) }
        }
    }

    override fun resetLine() {
        scope.launch {
            lineStates.update { LineStates() }
        }
    }

    override fun onDestroy() {
        scope.cancel()
    }
}