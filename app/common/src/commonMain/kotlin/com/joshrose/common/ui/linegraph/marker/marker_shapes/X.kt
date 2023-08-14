package com.joshrose.common.ui.linegraph.marker.marker_shapes

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun X(color: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier, contentDescription = "X Marker Shape") {
        val path = Path().apply {
            moveTo(x = 0f, y = 0f)
            lineTo(x = size.width, y = size.height)
            moveTo(x = 0f, y = size.height)
            lineTo(x = size.width, y = 0f)
        }
        drawPath(path = path, color = color, style = Stroke(width = size.width.div(4f)))
    }
}