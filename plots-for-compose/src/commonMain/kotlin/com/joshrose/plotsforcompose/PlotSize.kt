package com.joshrose.plotsforcompose

import com.joshrose.plotsforcompose.internals.ConfigsMap
import com.joshrose.plotsforcompose.internals.Specs

fun plotSize(width: Float, height: Float): ConfigsMap {
    return ConfigsMap(
        Specs.Plot.SIZE,
        mapOf(
            Specs.Plot.WIDTH to width,
            Specs.Plot.HEIGHT to height
        )
    )
}