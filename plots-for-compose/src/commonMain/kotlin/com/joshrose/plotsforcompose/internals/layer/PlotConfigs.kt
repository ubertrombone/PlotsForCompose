package com.joshrose.plotsforcompose.internals.layer

import com.joshrose.plotsforcompose.internals.Configs
import com.joshrose.plotsforcompose.internals.PlotKind

open class PlotConfigs(
    val kind: PlotKind,
    val mapping: Configs = Configs.empty()
) {
    open val parameters: Configs = Configs.empty()
}