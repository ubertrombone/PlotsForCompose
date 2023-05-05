package com.joshrose.common.components.axes.showaxes

import com.arkivanov.decompose.value.MutableValue
import com.joshrose.common.components.axes.models.AxesShowStates
import kotlinx.coroutines.CoroutineScope

interface ShowAxesModel {
    val scope: CoroutineScope
    val showAxesState: MutableValue<AxesShowStates>

    fun showAxis()
    fun showAxisLine()
    fun showGuidelines()
    fun showLabels()
}