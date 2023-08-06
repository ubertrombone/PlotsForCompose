package com.joshrose.common.components.linegraph.models

import androidx.compose.ui.unit.sp
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

@Parcelize
data class LabelStates(
    var fontSize: Float = 16.sp.value,
    var boxAlpha: Float = .3f,
    var xCornerRadius: Float = 0f,
    var yCornerRadius: Float = 0f
) : Parcelable
