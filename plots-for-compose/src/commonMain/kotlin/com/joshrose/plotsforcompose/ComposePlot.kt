package com.joshrose.plotsforcompose

import com.joshrose.plotsforcompose.internals.GeneralAestheticMapping
import com.joshrose.plotsforcompose.internals.Plot

fun composePlot(data: Map<*, *>? = null, mapping: GeneralAestheticMapping.() -> Unit = {}): Plot {
    return Plot(
        data = data,
        mapping = GeneralAestheticMapping().apply(mapping).seal()
    )
}