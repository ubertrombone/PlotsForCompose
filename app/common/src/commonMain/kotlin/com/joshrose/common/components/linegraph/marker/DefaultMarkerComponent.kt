package com.joshrose.common.components.linegraph.marker

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.joshrose.common.components.linegraph.models.MarkerStates

class DefaultMarkerComponent(
    componentContext: ComponentContext,
    private val markerValues: MarkerModel
): MarkerComponent, ComponentContext by componentContext {
    override val markerStates: Value<MarkerStates> = markerValues.markerStates

    override fun updateShowMarkers(update: Boolean) = markerValues.updateShowMarkers(update)

    override fun incMarkerSize() = markerValues.incMarkerSize()

    override fun decMarkerSize() = markerValues.decMarkerSize()
}