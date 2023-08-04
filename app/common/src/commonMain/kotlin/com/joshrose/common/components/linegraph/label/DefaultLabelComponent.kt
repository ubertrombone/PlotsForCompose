package com.joshrose.common.components.linegraph.label

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.joshrose.common.components.linegraph.models.LabelStates

class DefaultLabelComponent(
    componentContext: ComponentContext,
    private val labelValues: LabelModelImpl
) : LabelComponent, ComponentContext by componentContext {
    override val labelStates: Value<LabelStates> = labelValues.labelStates

    override fun incFontSize() = labelValues.incFontSize()

    override fun decFontSize() = labelValues.decFontSize()

    override fun incBoxAlpha() = labelValues.incBoxAlpha()

    override fun decBoxAlpha() = labelValues.decBoxAlpha()

    override fun incXCornerRadius() = labelValues.incXCornerRadius()

    override fun decXCornerRadius() = labelValues.decXCornerRadius()

    override fun incYCornerRadius() = labelValues.incYCornerRadius()

    override fun decYCornerRadius() = labelValues.decYCornerRadius()
}