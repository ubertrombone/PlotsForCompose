package com.joshrose.plotsforcompose.internals.markers

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope

@Suppress("DuplicatedCode")
fun DrawScope.markerTriangleDown(
    color: Color?,
    size: Float?,
    coordinates: Pair<Float, Float>
) {
    val sizeLessNull = size ?: 5f
    val bottomPoint = Offset(x = coordinates.first, y = coordinates.second.plus(sizeLessNull))
    val topRight = Offset(x = coordinates.first.plus(sizeLessNull), y = coordinates.second.minus(sizeLessNull))
    val topLeft = Offset(x = coordinates.first.minus(sizeLessNull), y = coordinates.second.minus(sizeLessNull))
    val path = Path().apply {
        moveTo(x = bottomPoint.x, y = bottomPoint.y)
        lineTo(x = topRight.x, y = topRight.y)
        lineTo(x = topLeft.x, y = topLeft.y)
        lineTo(x = bottomPoint.x, y = bottomPoint.y)
    }

    drawPath(path = path, color = color ?: Color.White)
}