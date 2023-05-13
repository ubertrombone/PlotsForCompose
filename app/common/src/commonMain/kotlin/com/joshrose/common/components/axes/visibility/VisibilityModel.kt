package com.joshrose.common.components.axes.visibility

import com.arkivanov.decompose.value.MutableValue
import com.joshrose.common.components.axes.models.VisibilityStates
import kotlinx.coroutines.CoroutineScope

interface VisibilityModel {
    val scope: CoroutineScope
    val visibilityState: MutableValue<VisibilityStates>

    fun showAxis()
    fun showAxisLine()
    fun showGuidelines()
    fun showLabels()
    fun resetVisibility()
}