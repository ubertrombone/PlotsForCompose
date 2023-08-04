package com.joshrose.common.components.linegraph.label

import com.arkivanov.decompose.value.Value
import com.joshrose.common.components.linegraph.models.LabelStates

interface LabelComponent {
    val labelStates: Value<LabelStates>

    fun incFontSize()
    fun decFontSize()
    fun incBoxAlpha()
    fun decBoxAlpha()
    fun incXCornerRadius()
    fun decXCornerRadius()
    fun incYCornerRadius()
    fun decYCornerRadius()
}