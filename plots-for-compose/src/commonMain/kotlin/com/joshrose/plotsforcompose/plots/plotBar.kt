package com.joshrose.plotsforcompose.plots

import com.joshrose.plotsforcompose.Plots
import com.joshrose.plotsforcompose.Stats
import com.joshrose.plotsforcompose.axis.util.AxisPosition
import com.joshrose.plotsforcompose.internals.Layer
import com.joshrose.plotsforcompose.internals.layer.PosConfigs
import com.joshrose.plotsforcompose.internals.layer.StatConfigs
import com.joshrose.plotsforcompose.internals.layer.plot.BarAesthetics
import com.joshrose.plotsforcompose.internals.layer.plot.BarMapping
import com.joshrose.plotsforcompose.internals.layer.stat.CountStatAesthetics
import com.joshrose.plotsforcompose.pos.positionStack

@Suppress("ClassName")
class plotBar(
    data: Map<*, *>? = null,
    stat: StatConfigs = Stats.count(),
    position: PosConfigs = positionStack(),
    showLegend: Boolean = true,
    orientation: AxisPosition.Orientation? = null,
    override val x: Number? = null,
    override val y: Number? = null,
    override val z: Any? = null,
    override val alpha: Number? = null,
    override val color: Any? = null,
    override val width: Number? = null,
    override val weight: Number? = null,
    mapping: BarMapping.() -> Unit = {}
) : BarAesthetics,
    CountStatAesthetics,
    Layer(
        mapping = BarMapping().apply(mapping).seal(),
        data = data,
        plot = Plots.bar(),
        stat = stat,
        position = position,
        showLegend = showLegend,
        orientation = orientation
    ) {
    override fun seal() = super<BarAesthetics>.seal() + super<CountStatAesthetics>.seal()
}