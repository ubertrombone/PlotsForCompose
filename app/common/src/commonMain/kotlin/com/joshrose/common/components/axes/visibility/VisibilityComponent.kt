package com.joshrose.common.components.axes.visibility

import com.arkivanov.decompose.value.Value
import com.joshrose.common.components.axes.models.VisibilityStates

interface VisibilityComponent {

    val xVisibilityStates: Value<VisibilityStates>

    fun updateShowXAxis()
    fun updateShowXAxisLine()
    fun updateShowXGuidelines()
    fun updateShowXLabels()

    val yVisibilityStates: Value<VisibilityStates>

    fun updateShowYAxis()
    fun updateShowYAxisLine()
    fun updateShowYGuidelines()
    fun updateShowYLabels()
}