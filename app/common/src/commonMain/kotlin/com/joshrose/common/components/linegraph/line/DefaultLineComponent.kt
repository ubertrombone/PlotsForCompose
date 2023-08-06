package com.joshrose.common.components.linegraph.line

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.joshrose.common.components.linegraph.models.LineStates
import com.joshrose.common.util.Join
import com.joshrose.plotsforcompose.linegraph.util.LineType

class DefaultLineComponent(
    componentContext: ComponentContext,
    private val lineValues: LineModelImpl
) : LineComponent, ComponentContext by componentContext {
    override val lineStates: Value<LineStates> = lineValues.lineStates

    override fun updateType(change: LineType) = lineValues.updateType(change)

    override fun updateJoin(change: Join) = lineValues.updateJoin(change)

    override fun incStrokeWidth() = lineValues.incStrokeWidth()

    override fun decStrokeWidth() = lineValues.decStrokeWidth()
}