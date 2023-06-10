package com.joshrose.plotsforcompose.figures

import com.joshrose.plotsforcompose.internals.layer.StatConfigs

sealed interface Figure {
    val stat: StatConfigs
    val showLegend: Boolean
}