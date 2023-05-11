package com.joshrose.common.components.axes.labels

import com.arkivanov.decompose.value.MutableValue
import com.joshrose.common.components.axes.models.LabelsStates
import kotlinx.coroutines.CoroutineScope

interface LabelsModel {
    val scope: CoroutineScope
    val labelsState: MutableValue<LabelsStates>

    fun incRotation()
    fun decRotation()
    fun incOffset()
    fun decOffset()
    fun incRangeAdjustment()
    fun decRangeAdjustment()
    fun incMaxAdjustment()
    fun decMaxAdjustment()
    fun incMinAdjustment()
    fun decMinAdjustment()
    fun incBreaks()
    fun decBreaks()
}