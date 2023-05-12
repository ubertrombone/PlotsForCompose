package com.joshrose.common.util

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

actual fun Modifier.paddingBottomBar(
    paddingValues: PaddingValues,
    top: Dp,
    start: Dp,
    end: Dp,
    bottom: Dp
): Modifier = this.padding(
    start = start,
    end = end,
    top = top,
    bottom = paddingValues.calculateBottomPadding().div(2f).plus(bottom)
)