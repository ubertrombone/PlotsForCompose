package com.joshrose.common.components.axes.models

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

@Parcelize
data class AxesShowStates(
    val showAxis: Boolean = true,
    val showAxisLine: Boolean = true,
    val showGuidelines: Boolean = true,
    val showLabels: Boolean = true
): Parcelable
