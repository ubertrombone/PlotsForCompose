package com.joshrose.plotsforcompose.internals.layer.plot

import com.joshrose.plotsforcompose.internals.ConfigCapsule
import com.joshrose.plotsforcompose.internals.Configs

interface BarAesthetics : ConfigCapsule {
    val x: Any?
    val y: Any?
    val z: Any?
    val alpha: Any?
    val color: Any?
    val width: Any?

    override fun seal() = Configs.of(
        "x" to x,
        "y" to y,
        "z" to z,
        "alpha" to alpha,
        "color" to color,
        "width" to width
    )
}