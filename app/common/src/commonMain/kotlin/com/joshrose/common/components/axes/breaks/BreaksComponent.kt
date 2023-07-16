package com.joshrose.common.components.axes.breaks

import com.arkivanov.decompose.value.Value
import com.joshrose.common.components.axes.models.BreakStates

interface BreaksComponent {
    val xBreaksState: Value<BreakStates>

    fun incBreaksX()
    fun decBreaksX()
    fun incLabelsX()
    fun decLabelsX()

    val yBreakState: Value<BreakStates>

    fun incBreaksY()
    fun decBreaksY()
    fun incLabelsY()
    fun decLabelsY()
}