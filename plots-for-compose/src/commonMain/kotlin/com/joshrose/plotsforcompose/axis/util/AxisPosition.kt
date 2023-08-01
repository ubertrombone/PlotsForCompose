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
    object Start : YAxis {
        private fun readResolve(): Any = Start
        override fun toString() = "AxisPosition#Start"
    }

    @Parcelize
    @Stable
    object End : YAxis {
        private fun readResolve(): Any = End
        override fun toString() = "AxisPosition#End"
    }

    @Parcelize
    @Stable
    object Top : XAxis {
        private fun readResolve(): Any = Top
        override fun toString() = "AxisPosition#Top"
    }

    @Parcelize
    @Stable
    object Bottom : XAxis {
        private fun readResolve(): Any = Bottom
        override fun toString() = "AxisPosition#Bottom"
    }

    @Parcelize
    @Stable
    object Center : XOrYAxis {
        private fun readResolve(): Any = Center
        override fun toString() = "AxisPosition#Center"
    }

    @Parcelize
    @Stable
    object Both: XOrYAxis {
        private fun readResolve(): Any = Both
        override fun toString() = "AxisPosition#Both"
    }

    @Stable
    enum class Orientation {
        X,
        Y
    }
}