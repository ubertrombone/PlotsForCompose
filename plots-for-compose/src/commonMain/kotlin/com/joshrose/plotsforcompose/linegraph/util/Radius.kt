package com.joshrose.plotsforcompose.linegraph.util

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

@Immutable
sealed interface Radius : Parcelable {

    val size: Float

    @Stable
    @Parcelize
    // Size does nothing
    class Auto(override val size: Float = 0f): Radius {
        override fun toString() = "Radius#Auto"
    }

    @Stable
    @Parcelize
    class WithMarkers(override val size: Float) : Radius {
        override fun toString() = "Radius#WithMarker#$size"
    }

    @Stable
    @Parcelize
    class WithoutMarkers(override val size: Float) : Radius {
        override fun toString() = "Radius#WithoutMarkers#$size"
    }
}