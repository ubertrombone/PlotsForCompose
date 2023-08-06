package com.joshrose.common.util

import androidx.compose.ui.graphics.StrokeJoin

enum class Join(val join: StrokeJoin) {
    BEVEL(StrokeJoin.Bevel),
    ROUND(StrokeJoin.Round),
    MITER(StrokeJoin.Miter);

    open fun joinName() = name.lowercase().replaceFirstChar { it.uppercase() }
}