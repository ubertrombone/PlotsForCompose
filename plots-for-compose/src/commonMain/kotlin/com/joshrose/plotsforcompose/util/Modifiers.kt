package com.joshrose.plotsforcompose.util

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.unit.Dp


fun Modifier.height(height: Dp?): Modifier = composed {
    height?.let { this.height(height) } ?: this.fillMaxHeight()
}

fun Modifier.width(width: Dp?): Modifier = composed {
    width?.let { this.width(width) } ?: this.fillMaxWidth()
}