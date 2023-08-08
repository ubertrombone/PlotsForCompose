package com.joshrose.common.components.linegraph.label_marker

import com.arkivanov.decompose.value.Value
import com.joshrose.common.components.linegraph.models.LabelMarkerStates
import com.joshrose.plotsforcompose.linegraph.util.Radius
import com.joshrose.plotsforcompose.linegraph.util.Style

interface LabelMarkerComponent {
    val labelMarkerStates: Value<LabelMarkerStates>

    fun updateRadius(radius: Radius)
    fun updateStyle(style: Style)
}