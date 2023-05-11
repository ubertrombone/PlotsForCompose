package com.joshrose.common.components.axes.axisline

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.joshrose.common.components.axes.models.AxisLineStates
import com.joshrose.plotsforcompose.axis.util.AxisPosition
import kotlinx.coroutines.*

class AxisLineModelImpl(initialState: AxisLineStates): InstanceKeeper.Instance, AxisLineModel {
    override val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    override val axisLineState: MutableValue<AxisLineStates> = MutableValue(initialState)

    override fun incAlpha() {
        scope.launch {
            axisLineState.update { it.copy(alpha = it.alpha.plus(.1f)) }
        }
    }

    override fun decAlpha() {
        scope.launch {
            axisLineState.update { it.copy(alpha = it.alpha.minus(.1f)) }
        }
    }

    override fun incStrokeWidth() {
        scope.launch {
            axisLineState.update { it.copy(strokeWidth = it.strokeWidth.inc()) }
        }
    }

    override fun decStrokeWidth() {
        scope.launch {
            axisLineState.update { it.copy(strokeWidth = it.strokeWidth.dec()) }
        }
    }

    override fun updateShowTicks(checked: Boolean) {
        scope.launch {
            axisLineState.update { it.copy(ticks = checked) }
        }
    }

    override fun updateAxisPosition(position: AxisPosition?) {
        scope.launch {
            axisLineState.update { it.copy(axisPosition = position) }
        }
    }

    override fun onDestroy() {
        scope.cancel()
    }
}