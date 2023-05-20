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
    object Start : XAxis {
        override fun toString() = "AxisAlignment#Start"
    }

    @Parcelize
    @Stable
    object End : XAxis {
        override fun toString() = "AxisAlignment#End"
    }

    @Parcelize
    @Stable
    object Top : YAxis {
        override fun toString() = "AxisAlignment#Top"
    }

    @Parcelize
    @Stable
    object Bottom : YAxis {
        override fun toString() = "AxisAlignment#Bottom"
    }

    @Parcelize
    @Stable
    object SpaceEvenly : XOrYAxis {
        override val offset = 1
        override fun toString() = "AxisAlignment#SpaceEvenly"
    }

    @Parcelize
    @Stable
    object SpaceBetween : XOrYAxis {
        override val offset = -1
        override fun toString() = "AxisAlignment#SpaceBetween"
    }
}