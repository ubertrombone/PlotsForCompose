package com.joshrose.common.components.axes.models

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.joshrose.plotsforcompose.axis.config.util.Multiplier

data class GuidelinesStates(
    val strokeWidth: Dp = 1.dp,
    val alpha: Multiplier = Multiplier(0.5f),
    val padding: Dp = 0.dp
)
