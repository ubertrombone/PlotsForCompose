package com.joshrose.common.ui.linegraph.label_line

import androidx.compose.ui.graphics.StrokeCap

enum class Cap(val strokeCap: StrokeCap) {
    BUTT(StrokeCap.Butt),
    ROUND(StrokeCap.Round),
    SQUARE(StrokeCap.Square);

    open fun capName() = name.lowercase().replaceFirstChar { it.uppercase() }
}