package com.joshrose.common.components.linegraph.models

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.joshrose.common.util.Cap

@Parcelize
data class LabelLineStates(
    var alpha: Float = .5f,
    var strokeWidth: Float = 3f,
    var strokeCap: Cap = Cap.ROUND
) : Parcelable
