package com.joshrose.plotsforcompose.linegraph.util

import androidx.compose.runtime.Stable
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

sealed interface Style : Parcelable {

    val size: Float

    @Stable
    @Parcelize
    class Auto(override val size: Float = 0f) : Style {
        override fun toString() = "Style#Auto"
    }

    @Stable
    @Parcelize
    class Fill(override val size: Float = 0f) : Style {
        override fun toString() = "Style#Fill"
    }

    @Stable
    @Parcelize
    class Stroke(override val size: Float) : Style {
        override fun toString() = "Style#Stroke#$size"
    }
}