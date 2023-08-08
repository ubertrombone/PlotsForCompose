package com.joshrose.plotsforcompose.linegraph.util

import androidx.compose.runtime.Stable
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

sealed interface Style : Parcelable {

    // Auto will determine if the graph should make the labels Fill or Stroke.
    // If Stroke, the graph will determine the strokeWidth based on the size of the graph's markers.
    @Stable
    @Parcelize
    data object Auto : Style {
        private fun readResolve(): Any = Auto
        override fun toString() = "Style#Auto"
    }

    // Fill should be used when you want the marker to be a solid shape.
    @Stable
    @Parcelize
    data object Fill : Style {
        private fun readResolve(): Any = Fill
        override fun toString() = "Style#Fill"
    }

    // Stroke should be used when you want the label marker to be a hollow circle or ring around markers.
    // strokeWidth is the thickness of the ring.
    @Stable
    @Parcelize
    class Stroke(val strokeWidth: Float = 5f) : Style {
        override fun toString() = "Style#Stroke#$strokeWidth"
    }
}