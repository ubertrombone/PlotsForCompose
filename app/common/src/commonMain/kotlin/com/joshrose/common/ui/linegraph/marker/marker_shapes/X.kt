package com.joshrose.common.ui.linegraph.marker.marker_shapes

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import com.joshrose.plotsforcompose.util.Markers.X

@OptIn(ExperimentalFoundationApi::class)
val x = DrawShape {
    Canvas(
        modifier = it.modifier
            .size(it.size)
            .clickable { it.action(X) },
        contentDescription = "X Marker Shape"
    ) {
        val path = Path().apply {
            moveTo(x = 0f, y = 0f)
            lineTo(x = size.width, y = size.height)
            moveTo(x = 0f, y = size.height)
            lineTo(x = size.width, y = 0f)
        }
        drawPath(path = path, color = it.color, style = Stroke(width = size.width.div(4f)))
    }
}