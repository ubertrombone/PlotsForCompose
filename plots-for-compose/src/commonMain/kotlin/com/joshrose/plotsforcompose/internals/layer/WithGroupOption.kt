package com.joshrose.plotsforcompose.internals.layer

import com.joshrose.plotsforcompose.internals.Configs

interface WithGroupOption {
    val group: Any?

    fun groupOption() = Configs.of("group" to group)
}