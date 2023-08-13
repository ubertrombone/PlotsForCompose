package com.joshrose.common.ui.linegraph.marker.marker_shapes

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Square(color: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier, contentDescription = "Square Marker Shape") {
        drawRect(color = color, size = size)
    }
}