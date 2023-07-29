package com.joshrose.common.components.linegraph.line

import com.arkivanov.decompose.value.Value
import com.joshrose.common.components.linegraph.models.LineStates
import com.joshrose.plotsforcompose.linegraph.util.LineType

interface LineComponent {
    val lineStates: Value<LineStates>

    fun updateType(change: LineType)
    fun incStrokeWidth()
    fun decStrokeWidth()
}