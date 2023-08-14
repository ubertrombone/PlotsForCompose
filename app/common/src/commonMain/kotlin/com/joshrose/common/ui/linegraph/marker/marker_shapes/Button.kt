package com.joshrose.common.ui.linegraph.marker.marker_shapes

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke

@Suppress("DuplicatedCode")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Button(color: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier, contentDescription = "Button Marker Shape") {
        val stroke = Stroke(width = size.width.div(4f))
        val path = Path().apply {
            moveTo(x = 0f, y = size.center.y)
            lineTo(x = size.width, y = size.center.y)
            moveTo(x = size.center.x, y = 0f)
            lineTo(x = size.center.x, y = size.height)
        }
        drawPath(path = path, color = color, style = stroke)
        drawCircle(color = color, radius = size.width.div(2f), style = stroke)
    }
}