package com.joshrose.common.components.axes.visibility

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.joshrose.common.components.axes.models.VisibilityStates

class DefaultVisibilityComponent(
    componentContext: ComponentContext,
    private val xVisibility: VisibilityModelImpl,
    private val yVisibility: VisibilityModelImpl
): VisibilityComponent, ComponentContext by componentContext {

    override val xVisibilityStates: Value<VisibilityStates> = xVisibility.visibilityState

    override fun updateShowXAxis() = xVisibility.showAxis()
    override fun updateShowXAxisLine() = xVisibility.showAxisLine()
    override fun updateShowXGuidelines() = xVisibility.showGuidelines()
    override fun updateShowXLabels() = xVisibility.showLabels()

    override val yVisibilityStates: Value<VisibilityStates> = yVisibility.visibilityState

    override fun updateShowYAxis() = yVisibility.showAxis()
    override fun updateShowYAxisLine() = yVisibility.showAxisLine()
    override fun updateShowYGuidelines() = yVisibility.showGuidelines()
    override fun updateShowYLabels() = yVisibility.showLabels()
}