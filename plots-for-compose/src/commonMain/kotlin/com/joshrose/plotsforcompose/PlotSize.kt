package com.joshrose.plotsforcompose

import androidx.compose.ui.unit.Dp
import com.joshrose.plotsforcompose.internals.ConfigsMap

fun plotSize(width: Dp? = null, height: Dp? = null) =
    ConfigsMap("size", mapOf("width" to width, "height" to height))