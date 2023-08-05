package com.joshrose.common.components.linegraph.label_marker

import com.arkivanov.decompose.value.MutableValue
import com.joshrose.common.components.linegraph.models.LabelMarkerStates
import com.joshrose.plotsforcompose.linegraph.util.Radius
import com.joshrose.plotsforcompose.linegraph.util.Style
import kotlinx.coroutines.CoroutineScope

interface LabelMarkerModel {
    val scope: CoroutineScope
    val labelMarkerStates: MutableValue<LabelMarkerStates>

    fun updateRadius(radius: Radius)
    fun updateStyle(style: Style)
    fun resetLabelMarkers()
}