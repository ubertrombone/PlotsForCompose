package com.joshrose.common.components.axes.breaks

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.joshrose.common.components.axes.models.BreakStates

class DefaultBreaksComponent(
    componentContext: ComponentContext,
    private val xBreaksValues: BreaksModelImpl,
    private val yBreaksValues: BreaksModelImpl,
) : BreaksComponent, ComponentContext by componentContext {

    override val xBreaksState: Value<BreakStates> = xBreaksValues.breaksState

    override fun incBreaksX() = xBreaksValues.incBreaks()

    override fun decBreaksX() = xBreaksValues.decBreaks()

    override fun incLabelsX() = xBreaksValues.incLabels()

    override fun decLabelsX() = xBreaksValues.decLabels()

    override val yBreakState: Value<BreakStates> = yBreaksValues.breaksState

    override fun incBreaksY() = yBreaksValues.incBreaks()

    override fun decBreaksY() = yBreaksValues.decBreaks()

    override fun incLabelsY() = yBreaksValues.incLabels()

    override fun decLabelsY() = yBreaksValues.decLabels()
}