package com.joshrose.plotsforcompose.axis.util

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

@Immutable
object AxisPosition {
    @Stable
    interface XAxis : Parcelable
    @Stable
    interface YAxis: Parcelable
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

    @Stable
    object Top : XAxis {
        override fun toString() = "AxisPosition#Top"
    }

    @Stable
    object Bottom : XAxis {
        override fun toString() = "AxisPosition#Bottom"
    }

    @Stable
    object Center : XOrYAxis {
        override fun toString() = "AxisPosition#Center"
    }
}
