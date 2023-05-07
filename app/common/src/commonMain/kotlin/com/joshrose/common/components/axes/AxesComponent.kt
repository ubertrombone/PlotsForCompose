package com.joshrose.common.components.axes

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.joshrose.common.components.axes.axisline.AxisLineComponent
import com.joshrose.common.components.axes.guidelines.GuidelinesComponent
import com.joshrose.common.components.axes.labels.LabelsComponent
import com.joshrose.common.components.axes.visibility.VisibilityComponent
import com.joshrose.common.util.ScreenNames

interface AxesComponent {
    val screenProperties: ScreenNames

    val childStack: Value<ChildStack<*, Child>>

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