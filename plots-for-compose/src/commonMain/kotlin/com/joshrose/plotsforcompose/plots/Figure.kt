package com.joshrose.plotsforcompose.plots

import com.joshrose.plotsforcompose.internals.layer.StatConfigs

sealed interface Figure {
    val stat: StatConfigs
    val showLegend: Boolean
}