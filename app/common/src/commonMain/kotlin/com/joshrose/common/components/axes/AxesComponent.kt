package com.joshrose.common.components.axes

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.joshrose.common.components.axes.axisline.AxisLineComponent
import com.joshrose.common.components.axes.guidelines.GuidelinesComponent
import com.joshrose.common.components.axes.labels.LabelsComponent
import com.joshrose.common.components.axes.models.*
import com.joshrose.common.components.axes.visibility.VisibilityComponent
import com.joshrose.common.util.ScreenNames
import com.joshrose.plotsforcompose.axis.config.labels.LabelsConfiguration
import com.joshrose.plotsforcompose.util.Coordinates

interface AxesComponent {
    val screenProperties: ScreenNames

    val dataValueStates: Value<DataValueStates>
    val loadingState: Value<LoadingState>
    val xVisibilityState: Value<VisibilityStates>
    val yVisibilityState: Value<VisibilityStates>
    val xGuidelinesState: Value<GuidelinesStates>
    val yGuidelinesState: Value<GuidelinesStates>
    val xAxisLineState: Value<AxisLineStates.XState>
    val yAxisLineState: Value<AxisLineStates.YState>
    val xLabelsState: Value<LabelsStates>
    val yLabelsState: Value<LabelsStates>
    val childStack: Value<ChildStack<*, Child>>

    fun updateData(data: List<Coordinates>)
    fun calculateData(xConfig: LabelsConfiguration, yConfig: LabelsConfiguration)

    fun onVisibilityTabClicked()
    fun onGuidelinesTabClicked()
    fun onAxisLinesTabClicked()
    fun onLabelsTabClicked()
    fun resetAxis()

    sealed class Child {
        class VisibilityChild(val component: VisibilityComponent): Child()
        class GuidelinesChild(val component: GuidelinesComponent): Child()
        class AxisLinesChild(val component: AxisLineComponent): Child()
        class LabelsChild(val component: LabelsComponent): Child()
    }
}