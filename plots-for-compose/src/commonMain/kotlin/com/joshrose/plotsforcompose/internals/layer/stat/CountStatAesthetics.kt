package com.joshrose.plotsforcompose.internals.layer.stat

import com.joshrose.plotsforcompose.internals.ConfigCapsule
import com.joshrose.plotsforcompose.internals.Configs

interface CountStatAesthetics : ConfigCapsule {
    val x: Any?
    val weight: Any?

    override fun seal(): Configs {
        return Configs.of(
            "x" to x,
            "weight" to weight
        )
    }
}