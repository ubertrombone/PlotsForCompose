package com.joshrose.plotsforcompose.axis.util

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

@Parcelize
sealed interface AxisPosition : Parcelable {

    @Parcelize
    enum class XAxisPosition: AxisPosition {
        BOTTOM,
        CENTER,
        TOP
    }

    @Parcelize
    enum class YAxisPosition: AxisPosition {
        START,
        CENTER,
        END
    }

    companion object {
        fun AxisPosition.toXAxisPosition(): XAxisPosition {
            return when (this) {
                XAxisPosition.BOTTOM -> XAxisPosition.BOTTOM
                XAxisPosition.CENTER -> XAxisPosition.CENTER
                XAxisPosition.TOP -> XAxisPosition.TOP
                else -> throw NoSuchElementException("Element must be of type XAxisPosition.")
            }
        }

        fun AxisPosition.toYAxisPosition(): YAxisPosition {
            return when (this) {
                YAxisPosition.START -> YAxisPosition.START
                YAxisPosition.CENTER -> YAxisPosition.CENTER
                YAxisPosition.END -> YAxisPosition.END
                else -> throw NoSuchElementException("Element must be of type YAxisPosition.")
            }
        }

        enum class Orientation {
            X,
            Y
        }
    }
}