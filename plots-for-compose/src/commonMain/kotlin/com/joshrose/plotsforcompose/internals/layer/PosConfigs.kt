package com.joshrose.plotsforcompose.internals.layer

import com.joshrose.plotsforcompose.internals.Configs
import com.joshrose.plotsforcompose.internals.PosKind

open class PosConfigs(
    val kind: PosKind,
    val mapping: Configs = Configs.empty()
) {
    open val parameters: Configs = Configs.empty()
}