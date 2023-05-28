package com.joshrose.plotsforcompose.internals

enum class PosKind {
    IDENTITY,
    STACK,
    DODGE;

    open fun optionName() = name.lowercase()
}