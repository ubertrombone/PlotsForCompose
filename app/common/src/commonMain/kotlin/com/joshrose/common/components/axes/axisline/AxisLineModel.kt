package com.joshrose.common.components.axes.axisline

import com.arkivanov.decompose.value.MutableValue
import com.joshrose.common.components.axes.models.AxisLineStates
import kotlinx.coroutines.CoroutineScope

interface AxisLineModel {
    val scope: CoroutineScope
    val axisLineState: MutableValue<AxisLineStates>

    fun incAlpha()
    fun decAlpha()
    fun incStrokeWidth()
    fun decStrokeWidth()
    fun updateShowTicks(checked: Boolean)
    //fun updateAxisPosition(position: AxisPosition?)
}