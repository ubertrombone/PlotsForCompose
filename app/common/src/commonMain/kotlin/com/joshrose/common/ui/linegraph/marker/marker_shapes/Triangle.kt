package com.joshrose.common.ui.linegraph.marker.marker_shapes

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Triangle(color: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier, contentDescription = "Triangle Marker Shape") {
        drawPath(path = trianglePath(size), color = color)
    }
}

val TriangleShape: Shape = object : Shape {
    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline =
        Outline.Generic(path = trianglePath(size))
}

private fun trianglePath(size: Size): Path {
    val topPoint = Offset(x = size.center.x, y = 0f)
    val bottomRight = Offset(x = size.width, y = size.height)
    val bottomLeft = Offset(x = 0f, y = size.height)
    return Path().apply {
        moveTo(x = topPoint.x, y = topPoint.y)
        lineTo(x = bottomRight.x, y = bottomRight.y)
        lineTo(x = bottomLeft.x, y = bottomLeft.y)
        close()
    }
}