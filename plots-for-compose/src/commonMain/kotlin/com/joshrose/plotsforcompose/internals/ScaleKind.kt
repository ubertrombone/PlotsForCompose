package com.joshrose.plotsforcompose.internals

enum class ScaleKind {
    X,
    Y,
    Z;

    open fun optionName() = name.lowercase()
}