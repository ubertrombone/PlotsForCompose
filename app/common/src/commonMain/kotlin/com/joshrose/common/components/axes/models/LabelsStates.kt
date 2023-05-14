package com.joshrose.common.components.axes.models

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.joshrose.plotsforcompose.axis.config.util.Multiplier

@Parcelize
data class LabelsStates(
    val rotation: Float = 0f,
    val axisOffset: Float = 20f,
    val rangeAdjustment: Multiplier = Multiplier(0f),
    val minValueAdjustment: Multiplier = Multiplier(0f),
    val maxValueAdjustment: Multiplier = Multiplier(0f),
    val breaks: Int = 5
): Parcelable
