package com.joshrose.common.components.linegraph.models

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.joshrose.common.ui.linegraph.line.Join
import com.joshrose.plotsforcompose.linegraph.util.LineType

@Parcelize
data class LineStates(
    var lineType: LineType = LineType.STRAIGHT,
    var strokeWidth: Float = 5f,
    var strokeJoin: Join = Join.BEVEL
) : Parcelable
