package com.joshrose.plotsforcompose.plots

import com.joshrose.plotsforcompose.Stats
import com.joshrose.plotsforcompose.axis.util.AxisPosition
import com.joshrose.plotsforcompose.internals.layer.PosConfigs
import com.joshrose.plotsforcompose.internals.layer.StatConfigs
import com.joshrose.plotsforcompose.pos.positionStack

class PlotBar(
    position: PosConfigs = positionStack(),
    orientation: AxisPosition.Orientation? = null,
    override val stat: StatConfigs = Stats.count(),
    override val showLegend: Boolean = true
) : Figure {
    override fun toString() = "Figure#PlotBar"
}