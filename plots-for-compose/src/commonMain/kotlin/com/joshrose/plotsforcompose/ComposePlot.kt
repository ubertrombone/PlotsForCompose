package com.joshrose.plotsforcompose

import com.joshrose.plotsforcompose.internals.GenericMapping
import com.joshrose.plotsforcompose.internals.Plot

fun composePlot(data: Map<*, *>? = null, mapping: GenericMapping.() -> Unit = {}): Plot {
    return Plot(
        data = data,
        mapping = GenericMapping().apply(mapping).seal()
    )
}