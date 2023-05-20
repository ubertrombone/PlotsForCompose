package com.joshrose.common.components.axes.axisline

import com.arkivanov.decompose.value.MutableValue
import com.joshrose.common.components.axes.models.AxisLineStates
import com.joshrose.plotsforcompose.axis.util.AxisPosition
import kotlinx.coroutines.CoroutineScope

interface XAxisLineModel {
    val scope: CoroutineScope
    val axisLineState: MutableValue<AxisLineStates.XState>

    fun incAlpha()
    fun decAlpha()
    fun incStrokeWidth()
    fun decStrokeWidth()
    fun updateShowTicks(checked: Boolean)
    fun updateAxisPosition(position: AxisPosition.XAxis?)
    fun resetAxisLine()
}