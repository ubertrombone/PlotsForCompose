package com.joshrose.common.components.axes.models

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.joshrose.plotsforcompose.axis.config.util.Multiplier
import com.joshrose.plotsforcompose.axis.util.AxisPosition

sealed interface AxisLineStates : Parcelable {
    interface State : AxisLineStates {
        val alpha: Multiplier
        val strokeWidth: Float
        val ticks: Boolean
        val axisPosition: AxisPosition?
    }

    @Parcelize
    data class XState(
        override val alpha: Multiplier = Multiplier(1f),
        override val strokeWidth: Float = 2f,
        override val ticks: Boolean = true,
        override val axisPosition: AxisPosition.XAxis? = null
    ) : State

    @Parcelize
    data class YState(
        override val alpha: Multiplier = Multiplier(1f),
        override val strokeWidth: Float = 2f,
        override val ticks: Boolean = true,
        override val axisPosition: AxisPosition.YAxis? = null
    ) : State
}
//@Parcelize
//data class AxisLineStates(
//    val alpha: Multiplier = Multiplier(1f),
//    val strokeWidth: Float = 2f,
//    val ticks: Boolean = true,
//    val axisPosition: AxisPosition? = null
//) : Parcelable
