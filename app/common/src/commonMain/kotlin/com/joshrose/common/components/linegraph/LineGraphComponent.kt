package com.joshrose.common.components.linegraph

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.joshrose.common.components.linegraph.line.LineComponent
import com.joshrose.common.components.linegraph.marker.MarkerComponent
import com.joshrose.common.components.linegraph.models.DataValues
import com.joshrose.common.components.linegraph.models.LineStates
import com.joshrose.common.components.linegraph.models.MarkerStates
import com.joshrose.common.util.ScreenNames

interface LineGraphComponent {
    val screenProperties: ScreenNames

    val dataValues: Value<DataValues>
    val lineStates: Value<LineStates>
    val markerStates: Value<MarkerStates>
    val childStack: Value<ChildStack<*, Child>>

    fun onLineTabClicked()
    fun onMarkerTabClicked()
    fun resetGraph()

    sealed class Child {
        class LineChild(val component: LineComponent): Child()
        class MarkerChild(val component: MarkerComponent): Child()
    }
}