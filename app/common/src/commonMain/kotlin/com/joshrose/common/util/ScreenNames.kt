package com.joshrose.common.util

enum class ScreenNames(val title: String, val isBackEnabled: Boolean) {
    HOME(title = "Plots for Compose", isBackEnabled = false),
    AXES(title = "Axes", isBackEnabled = true),
    LINE_GRAPH(title = "Line Graph", isBackEnabled = true)
}