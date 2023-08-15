package com.joshrose.common.ui.linegraph.marker.marker_shapes

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import com.joshrose.plotsforcompose.util.Markers.PLUS

@Suppress("DuplicatedCode")
@OptIn(ExperimentalFoundationApi::class)
val plus = DrawShape {
    Canvas(
        modifier = it.modifier
            .size(it.size)
            .clickable { it.action(PLUS) },
        contentDescription = "Plus Marker Shape"
    ) {
        val path = Path().apply {
            moveTo(x = 0f, y = size.center.y)
            lineTo(x = size.width, y = size.center.y)
            moveTo(x = size.center.x, y = 0f)
            lineTo(x = size.center.x, y = size.height)
        }
        drawPath(path = path, color = it.color, style = Stroke(width = size.width.div(4f)))
    }
}