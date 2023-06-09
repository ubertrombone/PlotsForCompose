package com.joshrose.plotsforcompose.internals

enum class StatKind {
    IDENTITY,
    COUNT,
    DENSITY,
    DOTPLOT,
    BOXPLOT,
    REGRESSION;

    open fun optionName() = name.lowercase()
}