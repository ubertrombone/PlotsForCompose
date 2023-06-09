package com.joshrose.common.components.axes.models

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.joshrose.plotsforcompose.axis.config.util.Multiplier

@Parcelize
data class GuidelinesStates(
    val strokeWidth: Float = 1f,
    val alpha: Multiplier = Multiplier(0.5f),
    val padding: Float = 0f
): Parcelable
