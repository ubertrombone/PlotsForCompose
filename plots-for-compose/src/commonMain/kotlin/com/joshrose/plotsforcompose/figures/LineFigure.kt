package com.joshrose.plotsforcompose.figures

import com.joshrose.plotsforcompose.Stats
import com.joshrose.plotsforcompose.internals.layer.StatConfigs
import com.joshrose.plotsforcompose.linegraph.config.LineGraphConfiguration
import com.joshrose.plotsforcompose.linegraph.config.lineGraphConfiguration

class LineFigure(
    val configs: LineGraphConfiguration = lineGraphConfiguration(),
    override val stat: StatConfigs = Stats.identity,
    override val showLegend: Boolean = false
) : Figure {
    override fun toString() = "Figure#Line"
}