package com.joshrose.common.components.linegraph.models

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.joshrose.plotsforcompose.linegraph.util.Radius
import com.joshrose.plotsforcompose.linegraph.util.Style

@Parcelize
data class LabelMarkerStates(
    var radius: Radius = Radius.Auto,
    var style: Style = Style.Auto
) : Parcelable
