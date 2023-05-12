package com.joshrose.common.components.axes.labels

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.joshrose.common.components.axes.models.LabelsStates
import kotlinx.coroutines.*

class LabelsModelImpl(initialState: LabelsStates): InstanceKeeper.Instance, LabelsModel {
    override val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    override val labelsState: MutableValue<LabelsStates> = MutableValue(initialState)

    override fun incRotation() {
        scope.launch {
            labelsState.update { it.copy(rotation = it.rotation.plus(5f)) }
        }
    }

    override fun decRotation() {
        scope.launch {
            labelsState.update { it.copy(rotation = it.rotation.minus(5f)) }
        }
    }

    override fun incOffset() {
        scope.launch {
            labelsState.update { it.copy(axisOffset = it.axisOffset.plus(5f)) }
        }
    }

    override fun decOffset() {
        scope.launch {
            labelsState.update { it.copy(axisOffset = it.axisOffset.minus(5f)) }
        }
    }

    override fun incRangeAdjustment() {
        scope.launch {
            labelsState.update { it.copy(rangeAdjustment = it.rangeAdjustment.plus(.1f)) }
        }
    }

    override fun decRangeAdjustment() {
        scope.launch {
            labelsState.update { it.copy(rangeAdjustment = it.rangeAdjustment.minus(.1f)) }
        }
    }

    override fun incMaxAdjustment() {
        scope.launch {
            labelsState.update { it.copy(maxValueAdjustment = it.maxValueAdjustment.plus(.1f)) }
        }
    }

    override fun decMaxAdjustment() {
        scope.launch {
            labelsState.update { it.copy(maxValueAdjustment = it.maxValueAdjustment.minus(.1f)) }
        }
    }

    override fun incMinAdjustment() {
        scope.launch {
            labelsState.update { it.copy(minValueAdjustment = it.minValueAdjustment.plus(.1f)) }
        }
    }

    override fun decMinAdjustment() {
        scope.launch {
            labelsState.update { it.copy(minValueAdjustment = it.minValueAdjustment.minus(.1f)) }
        }
    }

    override fun incBreaks() {
        scope.launch {
            labelsState.update { it.copy(breaks = it.breaks.inc()) }
        }
    }

    override fun decBreaks() {
        scope.launch {
            labelsState.update { it.copy(breaks = it.breaks.dec()) }
        }
    }

    override fun onDestroy() {
        scope.cancel()
    }
}