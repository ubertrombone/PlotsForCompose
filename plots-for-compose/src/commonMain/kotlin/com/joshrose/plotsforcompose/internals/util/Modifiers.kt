package com.joshrose.plotsforcompose.internals.util

import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset

internal expect fun Modifier.showGraphLabels(
    interactionSource: InteractionSource,
    enabled: Boolean,
    coordinates: List<Pair<Float, Float>>,
    data: List<Pair<Any?, Number>>,
    updatePosition: (position: Offset?, values: Pair<Any?, Any?>?, index: Int?) -> Unit
): Modifier