package com.joshrose.common.components.linegraph.label_line

import androidx.compose.ui.graphics.StrokeCap
import com.arkivanov.decompose.ComponentContext

class DefaultLabelLineComponent(
    componentContext: ComponentContext,
    private val labelLineValues: LabelLineModelImpl
) : LabelLineComponent, ComponentContext by componentContext {
    override val labelLineStates = labelLineValues.labelLineStates

    override fun incAlpha() = labelLineValues.incAlpha()

    override fun decAlpha() = labelLineValues.decAlpha()

    override fun incStrokeWidth() = labelLineValues.incStrokeWidth()

    override fun decStrokeWidth() = labelLineValues.decStrokeWidth()

    override fun updateStrokeCap(cap: StrokeCap) = labelLineValues.updateStrokeCap(cap)
}