package com.joshrose.common.util

import androidx.compose.ui.graphics.StrokeCap

enum class Cap(val strokeCap: StrokeCap) {
    BUTT(StrokeCap.Butt),
    ROUND(StrokeCap.Round),
    SQUARE(StrokeCap.Square);

    open fun capName() = name.lowercase().replaceFirstChar { it.uppercase() }
}