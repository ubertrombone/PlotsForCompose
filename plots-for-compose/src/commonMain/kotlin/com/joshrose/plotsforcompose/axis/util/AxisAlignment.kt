package com.joshrose.plotsforcompose.axis.util

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

@Parcelize
enum class AxisAlignment(val offset: Int): Parcelable {
    Left(0),
    Right(0),
    SpaceBetween(-1),
    SpaceEvenly(1)
}