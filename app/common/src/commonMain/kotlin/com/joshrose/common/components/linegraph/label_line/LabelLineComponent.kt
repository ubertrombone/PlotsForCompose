package com.joshrose.common.components.linegraph.label_line

import androidx.compose.ui.graphics.StrokeCap
import com.arkivanov.decompose.value.Value
import com.joshrose.common.components.linegraph.models.LabelLineStates

interface LabelLineComponent {
    val labelLineStates: Value<LabelLineStates>

    fun incAlpha()
    fun decAlpha()
    fun incStrokeWidth()
    fun decStrokeWidth()
    fun updateStrokeCap(cap: StrokeCap)
}