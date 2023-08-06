package com.joshrose.common.components.linegraph

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.joshrose.common.components.linegraph.label.LabelComponent
import com.joshrose.common.components.linegraph.label_line.LabelLineComponent
import com.joshrose.common.components.linegraph.label_marker.LabelMarkerComponent
import com.joshrose.common.components.linegraph.line.LineComponent
import com.joshrose.common.components.linegraph.marker.MarkerComponent
import com.joshrose.common.components.linegraph.models.*
import com.joshrose.common.util.ScreenNames

interface LineGraphComponent {
    val screenProperties: ScreenNames

    val dataValues: Value<DataValues>
    val lineStates: Value<LineStates>
    val markerStates: Value<MarkerStates>
    val labelStates: Value<LabelStates>
    val labelLineStates: Value<LabelLineStates>
    val labelMarkerStates: Value<LabelMarkerStates>
    val childStack: Value<ChildStack<*, Child>>

    fun onLineTabClicked()
    fun onMarkerTabClicked()
    fun onLabelTabClicked()
    fun onLabelLineTabClicked()
    fun onLabelMarkerTabClicked()
    fun resetGraph()

    sealed class Child {
        class LineChild(val component: LineComponent): Child()
        class MarkerChild(val component: MarkerComponent): Child()
        class LabelChild(val component: LabelComponent): Child()
        class LabelLineChild(val component: LabelLineComponent): Child()
        class LabelMarkerChild(val component: LabelMarkerComponent): Child()
    }
}