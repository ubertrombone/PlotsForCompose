package com.joshrose.common.components.linegraph.label_line

import com.arkivanov.decompose.ComponentContext
import com.joshrose.common.util.Cap

class DefaultLabelLineComponent(
    componentContext: ComponentContext,
    private val labelLineValues: LabelLineModelImpl
) : LabelLineComponent, ComponentContext by componentContext {
    override val labelLineStates = labelLineValues.labelLineStates

    override fun incAlpha() = labelLineValues.incAlpha()

    override fun decAlpha() = labelLineValues.decAlpha()

    override fun incStrokeWidth() = labelLineValues.incStrokeWidth()

    override fun decStrokeWidth() = labelLineValues.decStrokeWidth()

    override fun updateStrokeCap(cap: Cap) = labelLineValues.updateStrokeCap(cap)
}