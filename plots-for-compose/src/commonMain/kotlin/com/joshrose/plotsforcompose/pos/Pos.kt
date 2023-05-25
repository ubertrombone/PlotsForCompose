package com.joshrose.plotsforcompose.pos

import com.joshrose.plotsforcompose.internals.Configs
import com.joshrose.plotsforcompose.internals.PosKind
import com.joshrose.plotsforcompose.internals.layer.PosConfigs

val positionIdentity = PosConfigs(PosKind.IDENTITY)

fun positionDodge(posWidth: Number? = null) =
    PosConfigs(
        PosKind.DODGE,
        Configs.of("posWidth" to posWidth)
    )

fun positionStack(vjust: Number? = null) =
    PosConfigs(
        PosKind.STACK,
        Configs.of("vjust" to vjust)
    )