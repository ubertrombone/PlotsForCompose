package com.joshrose.common.components.linegraph.models

import androidx.compose.ui.graphics.StrokeCap
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

@Parcelize
data class LabelLineStates(
    var alpha: Float = .5f,
    var strokeWidth: Float = 3f,
    var strokeCap: StrokeCap = StrokeCap.Round
) : Parcelable
