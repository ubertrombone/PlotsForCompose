package com.joshrose.common.components.linegraph.line

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.joshrose.common.components.linegraph.models.LineStates
import com.joshrose.plotsforcompose.linegraph.util.LineType

// TODO: Does LineModel instead of LineModelImpl work?
class DefaultLineComponent(
    componentContext: ComponentContext,
    private val lineValues: LineModel
) : LineComponent, ComponentContext by componentContext {
    override val lineStates: Value<LineStates> = lineValues.lineStates

    override fun updateType(change: LineType) = lineValues.updateType(change)

    override fun incStrokeWidth() = lineValues.incStrokeWidth()

    override fun decStrokeWidth() = lineValues.decStrokeWidth()
}