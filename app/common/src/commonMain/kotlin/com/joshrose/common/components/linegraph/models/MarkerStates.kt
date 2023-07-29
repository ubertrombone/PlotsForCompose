package com.joshrose.common.components.linegraph.models

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

@Parcelize
data class MarkerStates(
    var markers: Boolean = false,
    var markerSize: Float = 4f
) : Parcelable
