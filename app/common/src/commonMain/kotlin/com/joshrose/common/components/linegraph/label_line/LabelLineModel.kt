package com.joshrose.common.components.linegraph.label_line

import com.arkivanov.decompose.value.MutableValue
import com.joshrose.common.components.linegraph.models.LabelLineStates
import com.joshrose.common.ui.linegraph.label_line.Cap
import kotlinx.coroutines.CoroutineScope

interface LabelLineModel {
    val scope: CoroutineScope
    val labelLineStates: MutableValue<LabelLineStates>

    fun incAlpha()
    fun decAlpha()
    fun incStrokeWidth()
    fun decStrokeWidth()
    fun updateStrokeCap(cap: Cap)
    fun resetLabelLine()
}