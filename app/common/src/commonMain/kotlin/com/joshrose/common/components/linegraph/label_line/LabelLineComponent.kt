package com.joshrose.common.components.linegraph.label_line

import com.arkivanov.decompose.value.Value
import com.joshrose.common.components.linegraph.models.LabelLineStates
import com.joshrose.common.ui.linegraph.label_line.Cap

interface LabelLineComponent {
    val labelLineStates: Value<LabelLineStates>

    fun incAlpha()
    fun decAlpha()
    fun incStrokeWidth()
    fun decStrokeWidth()
    fun updateStrokeCap(cap: Cap)
}