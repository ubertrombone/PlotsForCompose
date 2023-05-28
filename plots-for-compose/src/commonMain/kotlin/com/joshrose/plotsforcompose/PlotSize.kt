package com.joshrose.plotsforcompose

import com.joshrose.plotsforcompose.internals.ConfigsMap
import com.joshrose.plotsforcompose.internals.Specs

fun plotSize(width: Number, height: Number): ConfigsMap {
    return ConfigsMap(
        Specs.Plot.SIZE,
        mapOf(
            "width" to width,
            "height" to height
        )
    )
}