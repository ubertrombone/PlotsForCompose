package com.joshrose.common.components.linegraph.label

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.joshrose.common.components.linegraph.models.LabelStates
import kotlinx.coroutines.*

class LabelModelImpl(initialState: LabelStates) : InstanceKeeper.Instance, LabelModel {
    override val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    override val labelStates: MutableValue<LabelStates> = MutableValue(initialState)

    override fun incFontSize() {
        scope.launch {
            labelStates.update { it.copy(fontSize = it.fontSize.inc()) }
        }
    }

    override fun decFontSize() {
        scope.launch {
            labelStates.update { it.copy(fontSize = it.fontSize.dec()) }
        }
    }

    override fun incBoxAlpha() {
        scope.launch {
            labelStates.update { it.copy(boxAlpha = it.boxAlpha.plus(.1f).coerceAtMost(1f)) }
        }
    }

    override fun decBoxAlpha() {
        scope.launch {
            labelStates.update { it.copy(boxAlpha = it.boxAlpha.minus(.1f)) }
        }
    }

    override fun incXCornerRadius() {
        scope.launch {
            labelStates.update { it.copy(xCornerRadius = it.xCornerRadius.inc()) }
        }
    }

    override fun decXCornerRadius() {
        scope.launch {
            labelStates.update { it.copy(xCornerRadius = it.xCornerRadius.dec()) }
        }
    }

    override fun incYCornerRadius() {
        scope.launch {
            labelStates.update { it.copy(yCornerRadius = it.yCornerRadius.inc()) }
        }
    }

    override fun decYCornerRadius() {
        scope.launch {
            labelStates.update { it.copy(yCornerRadius = it.yCornerRadius.dec()) }
        }
    }

    override fun resetLabels() {
        scope.launch {
            labelStates.update { LabelStates() }
        }
    }

    override fun onDestroy() {
        scope.cancel()
    }
}