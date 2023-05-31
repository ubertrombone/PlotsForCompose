package com.joshrose.plotsforcompose.util

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.unit.Dp

fun Modifier.width(width: Dp?): Modifier = composed {
    width?.let { this.width(width) } ?: this.fillMaxWidth()
}