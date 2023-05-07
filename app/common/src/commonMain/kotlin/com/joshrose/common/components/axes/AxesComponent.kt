package com.joshrose.common.components.axes

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.joshrose.common.components.axes.axisline.AxisLineComponent
import com.joshrose.common.components.axes.guidelines.GuidelinesComponent
import com.joshrose.common.components.axes.labels.LabelsComponent
import com.joshrose.common.components.axes.models.DataValueStates
import com.joshrose.common.components.axes.models.GuidelinesStates
import com.joshrose.common.components.axes.models.LoadingState
import com.joshrose.common.components.axes.models.VisibilityStates
import com.joshrose.common.components.axes.visibility.VisibilityComponent
import com.joshrose.common.util.ScreenNames
import com.joshrose.plotsforcompose.axis.config.labels.ContinuousLabelsConfig

interface AxesComponent {
    val screenProperties: ScreenNames

    val dataValueStates: Value<DataValueStates>
    val loadingState: Value<LoadingState>
    val xVisibilityState: Value<VisibilityStates>
    val yVisibilityState: Value<VisibilityStates>
    val xGuidelinesState: Value<GuidelinesStates>
    val yGuidelinesState: Value<GuidelinesStates>
    val childStack: Value<ChildStack<*, Child>>

    fun updateData(xList: List<Float>, yList: List<Float>)
    fun calculateData(xConfig: ContinuousLabelsConfig, yConfig: ContinuousLabelsConfig)

    fun onVisibilityTabClicked()
    fun onGuidelinesTabClicked()
    fun onAxisLinesTabClicked()
    fun onLabelsTabClicked()

    sealed class Child {
        class VisibilityChild(val component: VisibilityComponent): Child()
        class GuidelinesChild(val component: GuidelinesComponent): Child()
        class AxisLinesChild(val component: AxisLineComponent): Child()
        class LabelsChild(val component: LabelsComponent): Child()
    }
}