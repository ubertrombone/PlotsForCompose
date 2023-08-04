package com.joshrose.common.components.linegraph.line

import com.arkivanov.decompose.value.MutableValue
import com.joshrose.common.components.linegraph.models.LineStates
import com.joshrose.plotsforcompose.linegraph.util.LineType
import kotlinx.coroutines.CoroutineScope

interface LineModel {
    val scope: CoroutineScope
    val lineStates: MutableValue<LineStates>

    fun updateType(change: LineType)
    fun incStrokeWidth()
    fun decStrokeWidth()
    fun resetLine()
}