package com.joshrose.common.components.linegraph.label_line

import androidx.compose.ui.graphics.StrokeCap
import com.arkivanov.decompose.value.MutableValue
import com.joshrose.common.components.linegraph.models.LabelLineStates
import kotlinx.coroutines.CoroutineScope

interface LabelLineModel {
    val scope: CoroutineScope
    val labelLineStates: MutableValue<LabelLineStates>

    fun incAlpha()
    fun decAlpha()
    fun incStrokeWidth()
    fun decStrokeWidth()
    fun updateStrokeCap(cap: StrokeCap)
    fun resetLabelLine()
}