package com.joshrose.common.components.axes.guidelines

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.joshrose.common.components.axes.models.GuidelinesStates
import kotlinx.coroutines.*

class GuidelinesModelImpl(initialState: GuidelinesStates): InstanceKeeper.Instance, GuidelinesModel {
    override val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    override val guidelinesState: MutableValue<GuidelinesStates> = MutableValue(initialState)

    override fun incStrokeWidth() {
        scope.launch {
            guidelinesState.update { it.copy(strokeWidth = it.strokeWidth.inc()) }
        }
    }

    override fun decStrokeWidth() {
        scope.launch {
            guidelinesState.update { it.copy(strokeWidth = it.strokeWidth.dec()) }
        }
    }

    override fun incAlpha() {
        scope.launch {
            guidelinesState.update { it.copy(alpha = it.alpha.plus(.1f)) }
        }
    }

    override fun decAlpha() {
        scope.launch {
            guidelinesState.update { it.copy(alpha = it.alpha.minus(.1f)) }
        }
    }

    override fun incPadding() {
        scope.launch {
            guidelinesState.update { it.copy(padding = it.padding.plus(5f)) }
        }
    }

    override fun decPadding() {
        scope.launch {
            guidelinesState.update { it.copy(padding = it.padding.minus(5f)) }
        }
    }

    override fun onDestroy() {
        scope.cancel()
    }
}