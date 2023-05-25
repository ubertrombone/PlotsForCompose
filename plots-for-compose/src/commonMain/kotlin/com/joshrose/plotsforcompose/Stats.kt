package com.joshrose.plotsforcompose

import com.joshrose.plotsforcompose.internals.StatKind
import com.joshrose.plotsforcompose.internals.layer.StatConfigs
import com.joshrose.plotsforcompose.internals.layer.stat.CountStatMapping

object Stats {
    val identity = StatConfigs(StatKind.IDENTITY)

    @Suppress("ClassName")
    class count(
        mapping: CountStatMapping.() -> Unit = {}
    ) : StatConfigs(
        StatKind.COUNT,
        mapping = CountStatMapping().apply(mapping).seal()
    )
}