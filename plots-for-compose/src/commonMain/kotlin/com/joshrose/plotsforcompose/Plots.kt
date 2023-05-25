package com.joshrose.plotsforcompose

import com.joshrose.plotsforcompose.internals.PlotKind
import com.joshrose.plotsforcompose.internals.layer.PlotConfigs
import com.joshrose.plotsforcompose.internals.layer.plot.BarAesthetics
import com.joshrose.plotsforcompose.internals.layer.plot.BarMapping

object Plots {

    @Suppress("ClassName")
    class bar(
        override val x: Number? = null,
        override val y: Number? = null,
        override val z: Any? = null,
        override val alpha: Number? = null,
        override val color: Any? = null,
        override val width: Number? = null,
        mapping: BarMapping.() -> Unit = {}
    ) : BarAesthetics,
        PlotConfigs(
            PlotKind.BAR,
            BarMapping().apply(mapping).seal()
        ) {
        override val parameters = this.seal()
        override fun seal() = super<BarAesthetics>.seal()
    }
}