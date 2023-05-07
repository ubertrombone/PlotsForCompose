package com.joshrose.common.components.axes.guidelines

import com.arkivanov.decompose.value.Value
import com.joshrose.common.components.axes.models.GuidelinesStates

interface GuidelinesComponent {

    val xGuidelinesStates: Value<GuidelinesStates>
    fun incGuidelinesStrokeWidthX()
    fun decGuidelinesStrokeWidthX()
    fun incGuidelinesAlphaX()
    fun decGuidelinesAlphaX()
    fun incGuidelinesPaddingX()
    fun decGuidelinesPaddingX()

    val yGuidelinesState: Value<GuidelinesStates>

    fun incGuidelinesStrokeWidthY()
    fun decGuidelinesStrokeWidthY()
    fun incGuidelinesAlphaY()
    fun decGuidelinesAlphaY()
    fun incGuidelinesPaddingY()
    fun decGuidelinesPaddingY()
}