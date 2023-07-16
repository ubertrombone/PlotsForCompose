package com.joshrose.common.components.axes.breaks

import com.arkivanov.decompose.value.MutableValue
import com.joshrose.common.components.axes.models.BreakStates
import kotlinx.coroutines.CoroutineScope

interface BreaksModel {
    val scope: CoroutineScope
    val breaksState: MutableValue<BreakStates>

    fun incBreaks()
    fun decBreaks()
    fun incLabels()
    fun decLabels()
    fun resetLabels()
}