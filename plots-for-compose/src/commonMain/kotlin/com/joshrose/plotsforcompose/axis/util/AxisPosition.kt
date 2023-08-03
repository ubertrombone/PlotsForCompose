package com.joshrose.plotsforcompose.axis.util

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

@Immutable
sealed interface AxisPosition {
    @Stable
    interface XAxis : AxisPosition, Parcelable
    @Stable
    interface YAxis: AxisPosition, Parcelable
    @Stable
    interface XOrYAxis: XAxis, YAxis, Parcelable

    @Parcelize
    @Stable
    data object Start : YAxis {
        private fun readResolve(): Any = Start
        override fun toString() = "AxisPosition#Start"
    }

    @Parcelize
    @Stable
    data object End : YAxis {
        private fun readResolve(): Any = End
        override fun toString() = "AxisPosition#End"
    }

    @Parcelize
    @Stable
    data object Top : XAxis {
        private fun readResolve(): Any = Top
        override fun toString() = "AxisPosition#Top"
    }

    @Parcelize
    @Stable
    data object Bottom : XAxis {
        private fun readResolve(): Any = Bottom
        override fun toString() = "AxisPosition#Bottom"
    }

    @Parcelize
    @Stable
    data object Center : XOrYAxis {
        private fun readResolve(): Any = Center
        override fun toString() = "AxisPosition#Center"
    }

    @Parcelize
    @Stable
    data object Both: XOrYAxis {
        private fun readResolve(): Any = Both
        override fun toString() = "AxisPosition#Both"
    }

    @Stable
    enum class Orientation {
        X,
        Y
    }
}