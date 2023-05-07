package com.joshrose.common.components.axes.guidelines

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.joshrose.common.components.axes.models.GuidelinesStates

class DefaultGuidelinesComponent(
    componentContext: ComponentContext,
    private val xGuidelinesValues: GuidelinesModelImpl,
    private val yGuidelinesValues: GuidelinesModelImpl
): GuidelinesComponent, ComponentContext by componentContext {

    override val xGuidelinesStates: Value<GuidelinesStates> = xGuidelinesValues.guidelinesState
    override fun incGuidelinesStrokeWidthX() = xGuidelinesValues.incStrokeWidth()
    override fun decGuidelinesStrokeWidthX() = xGuidelinesValues.decStrokeWidth()
    override fun incGuidelinesAlphaX() = xGuidelinesValues.incAlpha()
    override fun decGuidelinesAlphaX() = xGuidelinesValues.decAlpha()
    override fun incGuidelinesPaddingX() = xGuidelinesValues.incPadding()
    override fun decGuidelinesPaddingX() = xGuidelinesValues.decPadding()

    override val yGuidelinesState: Value<GuidelinesStates> = yGuidelinesValues.guidelinesState

    override fun incGuidelinesStrokeWidthY() = yGuidelinesValues.incStrokeWidth()
    override fun decGuidelinesStrokeWidthY() = yGuidelinesValues.decStrokeWidth()
    override fun incGuidelinesAlphaY() = yGuidelinesValues.incAlpha()
    override fun decGuidelinesAlphaY() = yGuidelinesValues.decAlpha()
    override fun incGuidelinesPaddingY() = yGuidelinesValues.incPadding()
    override fun decGuidelinesPaddingY() = yGuidelinesValues.decPadding()
}