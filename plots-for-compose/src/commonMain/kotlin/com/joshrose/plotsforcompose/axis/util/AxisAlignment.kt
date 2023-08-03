package com.joshrose.plotsforcompose.axis.util

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

@Immutable
sealed interface AxisAlignment {
    @Stable
    interface XAxis : AxisAlignment, Parcelable {
        val offset get() = 0
    }

    @Stable
    interface YAxis : AxisAlignment, Parcelable {
        val offset get() = 0
    }

    @Stable
    interface XOrYAxis : XAxis, YAxis, Parcelable {
        override val offset get() = 0
    }

    @Parcelize
    @Stable
    data object Start : XAxis {
        private fun readResolve(): Any = Start
        override fun toString() = "AxisAlignment#Start"
    }

    @Parcelize
    @Stable
    data object End : XAxis {
        private fun readResolve(): Any = End
        override fun toString() = "AxisAlignment#End"
    }

    @Parcelize
    @Stable
    data object Top : YAxis {
        private fun readResolve(): Any = Top
        override fun toString() = "AxisAlignment#Top"
    }

    @Parcelize
    @Stable
    data object Bottom : YAxis {
        private fun readResolve(): Any = Bottom
        override fun toString() = "AxisAlignment#Bottom"
    }

    @Parcelize
    @Stable
    data object SpaceEvenly : XOrYAxis {
        private fun readResolve(): Any = SpaceEvenly
        override val offset = 1
        override fun toString() = "AxisAlignment#SpaceEvenly"
    }

    @Parcelize
    @Stable
    data object SpaceBetween : XOrYAxis {
        private fun readResolve(): Any = SpaceBetween
        override val offset = -1
        override fun toString() = "AxisAlignment#SpaceBetween"
    }
}