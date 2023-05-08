package com.joshrose.common.components.axes.models

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.joshrose.plotsforcompose.axis.config.util.Multiplier

@Parcelize
data class AxisLineStates(
    val alpha: Float = Multiplier(1f).factor,
    val strokeWidth: Float = 2f,
    val ticks: Boolean = true,
    //val axisPosition: AxisPosition? = null
) : Parcelable
