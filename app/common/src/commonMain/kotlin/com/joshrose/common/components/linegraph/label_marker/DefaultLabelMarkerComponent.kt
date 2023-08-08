package com.joshrose.common.components.linegraph.label_marker

import com.arkivanov.decompose.ComponentContext
import com.joshrose.plotsforcompose.linegraph.util.Radius
import com.joshrose.plotsforcompose.linegraph.util.Style

class DefaultLabelMarkerComponent(
    componentContext: ComponentContext,
    private val labelMarkerValues: LabelMarkerModelImpl
) : LabelMarkerComponent, ComponentContext by componentContext {
    override val labelMarkerStates = labelMarkerValues.labelMarkerStates

    override fun updateRadius(radius: Radius) = labelMarkerValues.updateRadius(radius)

    override fun updateStyle(style: Style) = labelMarkerValues.updateStyle(style)
}