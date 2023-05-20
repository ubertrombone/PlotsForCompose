package com.joshrose.common.components.axes.axisline

import com.arkivanov.decompose.value.Value
import com.joshrose.common.components.axes.models.AxisLineStates
import com.joshrose.plotsforcompose.axis.util.AxisPosition

interface AxisLineComponent {

    val xAxisLineState: Value<AxisLineStates.XState>

    fun incAlphaX()
    fun decAlphaX()
    fun incStrokeWidthX()
    fun decStrokeWidthX()
    fun updateShowTicksX(checked: Boolean)
    fun updateAxisPositionX(position: AxisPosition.XAxis?)

    val yAxisLineState: Value<AxisLineStates.YState>

    fun incAlphaY()
    fun decAlphaY()
    fun incStrokeWidthY()
    fun decStrokeWidthY()
    fun updateShowTicksY(checked: Boolean)
    fun updateAxisPositionY(position: AxisPosition.YAxis?)
}