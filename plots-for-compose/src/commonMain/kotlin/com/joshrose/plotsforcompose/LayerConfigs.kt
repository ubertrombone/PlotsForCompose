package com.joshrose.plotsforcompose

import androidx.compose.ui.graphics.Color

sealed interface LayerConfigs

data class BarConfigs(
    val barColors: List<Color>
) : LayerConfigs