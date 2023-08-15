package com.joshrose.common.ui.linegraph.marker.marker_shapes

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import com.joshrose.plotsforcompose.util.Markers.SQUARE

@OptIn(ExperimentalFoundationApi::class)
val square = DrawShape {
    Canvas(
        modifier = it.modifier
            .size(it.size)
            .clickable { it.action(SQUARE) },
        contentDescription = "Square Marker Shape"
    ) {
        drawRect(color = it.color, size = size)
    }
}