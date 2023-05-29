package com.joshrose.plotsforcompose

import androidx.compose.ui.unit.Dp
import com.joshrose.plotsforcompose.internals.ConfigsMap
import com.joshrose.plotsforcompose.internals.Specifications

fun plotSize(width: Dp?, height: Dp?): ConfigsMap {
    return ConfigsMap(
        "size",
        Specifications.Size(width, height)
    )
}