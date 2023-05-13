package com.joshrose.common.components.axes.guidelines

import com.arkivanov.decompose.value.MutableValue
import com.joshrose.common.components.axes.models.GuidelinesStates
import kotlinx.coroutines.CoroutineScope

interface GuidelinesModel {
    val scope: CoroutineScope
    val guidelinesState: MutableValue<GuidelinesStates>

    fun incStrokeWidth()
    fun decStrokeWidth()
    fun incAlpha()
    fun decAlpha()
    fun incPadding()
    fun decPadding()
    fun resetGuidelines()
}