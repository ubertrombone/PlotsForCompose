package com.joshrose.common.components.linegraph.marker

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.joshrose.common.components.linegraph.models.MarkerStates
import com.joshrose.plotsforcompose.util.Markers

class DefaultMarkerComponent(
    componentContext: ComponentContext,
    private val markerValues: MarkerModelImpl
): MarkerComponent, ComponentContext by componentContext {
    override val markerStates: Value<MarkerStates> = markerValues.markerStates

    override fun updateShowMarkers(update: Boolean) = markerValues.updateShowMarkers(update)

    override fun updateMarkerShape(update: Markers) = markerValues.updateMarkerShape(update)

    override fun incMarkerSize() = markerValues.incMarkerSize()

    override fun decMarkerSize() = markerValues.decMarkerSize()
}