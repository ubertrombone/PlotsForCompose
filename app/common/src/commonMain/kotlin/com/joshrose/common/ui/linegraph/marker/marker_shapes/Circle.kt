package com.joshrose.common.ui.linegraph.marker.marker_shapes

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.ui.draw.clip
import com.joshrose.plotsforcompose.util.Markers.CIRCLE

@OptIn(ExperimentalFoundationApi::class)
val circle = DrawShape {
    Canvas(
        modifier = it.modifier
            .size(it.size)
            .clip(it.shape)
            .clickable { it.action(CIRCLE) },
        contentDescription = "Circle Marker Shape"
    ) {
        drawCircle(color = it.color, radius = size.width.div(2f))
    }
}