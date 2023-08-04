package com.joshrose.common.components.linegraph.label

import com.arkivanov.decompose.value.MutableValue
import com.joshrose.common.components.linegraph.models.LabelStates
import kotlinx.coroutines.CoroutineScope

interface LabelModel {
    val scope: CoroutineScope
    val labelStates: MutableValue<LabelStates>

    fun incFontSize()
    fun decFontSize()
    fun incBoxAlpha()
    fun decBoxAlpha()
    fun incXCornerRadius()
    fun decXCornerRadius()
    fun incYCornerRadius()
    fun decYCornerRadius()
    fun resetLabels()
}