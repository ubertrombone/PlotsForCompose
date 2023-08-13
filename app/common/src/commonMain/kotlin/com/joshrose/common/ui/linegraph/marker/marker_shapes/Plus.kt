package com.joshrose.common.ui.linegraph.marker.marker_shapes

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Plus(color: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier, contentDescription = "Plus Marker Shape") {
        drawPath(path = plusPath(size), color = color)
    }
}

val PlusShape: Shape = object : Shape {
    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline =
        Outline.Generic(path = plusPath(size))
}

private fun plusPath(size: Size): Path = Path().apply {
    moveTo(x = 0f, y = size.center.y)
    lineTo(x = size.width, y = size.center.y)
    moveTo(x = size.center.x, y = 0f)
    lineTo(x = size.center.x, y = size.height)
    Stroke(width = size.width.div(4f))
}