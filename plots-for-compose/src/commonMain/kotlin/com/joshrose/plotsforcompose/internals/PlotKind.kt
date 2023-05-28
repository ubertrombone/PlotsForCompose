package com.joshrose.plotsforcompose.internals

enum class PlotKind {
    BAR,
    LINE,
    SMOOTH;

    open fun optionName() = name.lowercase()
}