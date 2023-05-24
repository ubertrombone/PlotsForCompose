package com.joshrose.plotsforcompose.internals.layer

import com.joshrose.plotsforcompose.internals.Configs
import com.joshrose.plotsforcompose.internals.StatKind

open class StatConfigs(
    val kind: StatKind,
    val mapping: Configs = Configs.empty()
) {
    open val parameters: Configs = Configs.empty()
}