package com.joshrose.plotsforcompose.linegraph.util

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

@Immutable
sealed interface Radius : Parcelable {

    @Stable
    @Parcelize
    data object Auto : Radius {
        private fun readResolve(): Any = Auto
        override fun toString() = "Radius#Auto"
    }

    @Stable
    @Parcelize
    data class WithMarkers(val size: Float) : Radius {
        override fun toString() = "Radius#WithMarker#$size"
    }

    @Stable
    @Parcelize
    data class WithoutMarkers(val size: Float) : Radius {
        override fun toString() = "Radius#WithoutMarkers#$size"
    }
}