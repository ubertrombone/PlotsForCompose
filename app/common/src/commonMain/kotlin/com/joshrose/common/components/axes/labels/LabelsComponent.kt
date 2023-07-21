package com.joshrose.common.components.axes.labels

import com.arkivanov.decompose.value.Value
import com.joshrose.common.components.axes.models.LabelsStates

interface LabelsComponent {

    val xLabelsState: Value<LabelsStates>

    fun incRotationX()
    fun decRotationX()
    fun incOffsetX()
    fun decOffsetX()
    fun incRangeAdjustmentX()
    fun decRangeAdjustmentX()
    fun incMaxAdjustmentX()
    fun decMaxAdjustmentX()
    fun incMinAdjustmentX()
    fun decMinAdjustmentX()

    val yLabelsState: Value<LabelsStates>

    fun incRotationY()
    fun decRotationY()
    fun incOffsetY()
    fun decOffsetY()
    fun incRangeAdjustmentY()
    fun decRangeAdjustmentY()
    fun incMaxAdjustmentY()
    fun decMaxAdjustmentY()
    fun incMinAdjustmentY()
    fun decMinAdjustmentY()
}