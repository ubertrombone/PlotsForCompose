package com.joshrose.common.components.axes.models

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.joshrose.plotsforcompose.util.Proportional

@Parcelize
data class BreakStates(
    val breaks: Proportional? = null,
    val labels: Proportional? = null
) : Parcelable
