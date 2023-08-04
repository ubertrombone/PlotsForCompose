package com.joshrose.common.components.linegraph.label_line

import androidx.compose.ui.graphics.StrokeCap
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.joshrose.common.components.linegraph.models.LabelLineStates
import kotlinx.coroutines.*

class LabelLineModelImpl(initialState: LabelLineStates) : InstanceKeeper.Instance, LabelLineModel {
    override val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    override val labelLineStates = MutableValue(initialState)

    override fun incAlpha() {
        scope.launch {
            labelLineStates.update { it.copy(alpha = it.alpha.plus(.1f)) }
        }
    }

    override fun decAlpha() {
        scope.launch {
            labelLineStates.update { it.copy(alpha = it.alpha.minus(.1f)) }
        }
    }

    override fun incStrokeWidth() {
        scope.launch {
            labelLineStates.update { it.copy(strokeWidth = it.strokeWidth.inc()) }
        }
    }

    override fun decStrokeWidth() {
        scope.launch {
            labelLineStates.update { it.copy(strokeWidth = it.strokeWidth.dec()) }
        }
    }

    override fun updateStrokeCap(cap: StrokeCap) {
        scope.launch {
            labelLineStates.update { it.copy(strokeCap = cap) }
        }
    }

    override fun resetLabelLine() {
        scope.launch {
            labelLineStates.update { LabelLineStates() }
        }
    }

    override fun onDestroy() {
        scope.cancel()
    }
}