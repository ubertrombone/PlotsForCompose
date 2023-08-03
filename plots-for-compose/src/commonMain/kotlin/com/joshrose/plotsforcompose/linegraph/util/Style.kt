package com.joshrose.plotsforcompose.linegraph.util

import androidx.compose.runtime.Stable
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

sealed interface Style : Parcelable {

    @Stable
    @Parcelize
    data object Auto : Style {
        private fun readResolve(): Any = Auto
        override fun toString() = "Style#Auto"
    }

    @Stable
    @Parcelize
    data object Fill : Style {
        private fun readResolve(): Any = Fill
        override fun toString() = "Style#Fill"
    }

    @Stable
    @Parcelize
    data class Stroke(val size: Float) : Style {
        override fun toString() = "Style#Stroke#$size"
    }
}