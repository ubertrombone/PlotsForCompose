package com.joshrose.plotsforcompose.linegraph.util

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

@Immutable
sealed interface Radius : Parcelable {

    @Stable
    @Parcelize
    data object Auto: Radius {
        private fun readResolve(): Any = Auto
        override fun toString() = "Radius#Auto"
    }

    // Stroke should be used when graph has markers. Radius is applied to the label marker.
    // In general, the radius should be greater than the size of the graph's markers.
    @Stable
    @Parcelize
    class Stroke(val radius: Float = 5f) : Radius {
        override fun toString() = "Radius#WithMarker#$radius"
    }

    // Fill should be used when graph doesn't have markers. The radius then determines the size of the label marker.
    @Stable
    @Parcelize
    class Fill(val radius: Float = 5f) : Radius {
        override fun toString() = "Radius#WithoutMarkers#$radius"
    }
}