package com.joshrose.common.components.axes.labels

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.joshrose.common.components.axes.models.LabelsStates

class DefaultLabelsComponent(
    componentContext: ComponentContext,
    private val xLabelsValues: LabelsModelImpl,
    private val yLabelsValues: LabelsModelImpl,
): LabelsComponent, ComponentContext by componentContext {

    override val xLabelsState: Value<LabelsStates> = xLabelsValues.labelsState

    override fun incRotationX() = xLabelsValues.incRotation()
    override fun decRotationX() = xLabelsValues.decRotation()
    override fun incOffsetX() = xLabelsValues.incOffset()
    override fun decOffsetX() = xLabelsValues.decOffset()
    override fun incRangeAdjustmentX() = xLabelsValues.incRangeAdjustment()
    override fun decRangeAdjustmentX() = xLabelsValues.decRangeAdjustment()
    override fun incMaxAdjustmentX() = xLabelsValues.incMaxAdjustment()
    override fun decMaxAdjustmentX() = xLabelsValues.decMaxAdjustment()
    override fun incMinAdjustmentX() = xLabelsValues.incMinAdjustment()
    override fun decMinAdjustmentX() = xLabelsValues.decMinAdjustment()

    override val yLabelsState: Value<LabelsStates> = yLabelsValues.labelsState

    override fun incRotationY() = yLabelsValues.incRotation()
    override fun decRotationY() = yLabelsValues.decRotation()
    override fun incOffsetY() = yLabelsValues.incOffset()
    override fun decOffsetY() = yLabelsValues.decOffset()
    override fun incRangeAdjustmentY() = yLabelsValues.incRangeAdjustment()
    override fun decRangeAdjustmentY() = yLabelsValues.decRangeAdjustment()
    override fun incMaxAdjustmentY() = yLabelsValues.incMaxAdjustment()
    override fun decMaxAdjustmentY() = yLabelsValues.decMaxAdjustment()
    override fun incMinAdjustmentY() = yLabelsValues.incMinAdjustment()
    override fun decMinAdjustmentY() = yLabelsValues.decMinAdjustment()
}