package com.joshrose.common.components.axes.axisline

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.joshrose.common.components.axes.models.AxisLineStates
import com.joshrose.plotsforcompose.axis.util.AxisPosition

class DefaultAxisLineComponent(
    componentContext: ComponentContext,
    private val xAxisLineValues: XAxisLineModelImpl,
    private val yAxisLineValues: YAxisLineModelImpl
): AxisLineComponent, ComponentContext by componentContext {

    override val xAxisLineState: Value<AxisLineStates.XState> = xAxisLineValues.axisLineState

    override fun incAlphaX()  = xAxisLineValues.incAlpha()
    override fun decAlphaX() = xAxisLineValues.decAlpha()
    override fun incStrokeWidthX() = xAxisLineValues.incStrokeWidth()
    override fun decStrokeWidthX() = xAxisLineValues.decStrokeWidth()
    override fun updateShowTicksX(checked: Boolean) = xAxisLineValues.updateShowTicks(checked)
    override fun updateAxisPositionX(position: AxisPosition.XAxis?) = xAxisLineValues.updateAxisPosition(position)

    override val yAxisLineState: Value<AxisLineStates.YState> = yAxisLineValues.axisLineState

    override fun incAlphaY() = yAxisLineValues.incAlpha()
    override fun decAlphaY() = yAxisLineValues.decAlpha()
    override fun incStrokeWidthY() = yAxisLineValues.incStrokeWidth()
    override fun decStrokeWidthY() = yAxisLineValues.decStrokeWidth()
    override fun updateShowTicksY(checked: Boolean) = yAxisLineValues.updateShowTicks(checked)
    override fun updateAxisPositionY(position: AxisPosition.YAxis?) = yAxisLineValues.updateAxisPosition(position)
}