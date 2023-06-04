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
        override fun toString() = "AxisPosition#Start"
    }

    @Parcelize
    @Stable
    object End : YAxis {
        override fun toString() = "AxisPosition#End"
    }

    @Parcelize
    @Stable
    object Top : XAxis {
        override fun toString() = "AxisPosition#Top"
    }

    @Parcelize
    @Stable
    object Bottom : XAxis {
        override fun toString() = "AxisPosition#Bottom"
    }

    @Parcelize
    @Stable
    object Center : XOrYAxis {
        override fun toString() = "AxisPosition#Center"
    }

    @Parcelize
    @Stable
    object Both: XOrYAxis {
        override fun toString() = "AxisPosition#Both"
    }

    @Stable
    enum class Orientation {
        X,
        Y
    }
}
