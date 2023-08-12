package com.joshrose.plotsforcompose.internals.markers

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope

@Suppress("DuplicatedCode")
internal fun DrawScope.markerTriangle(
    color: Color?,
    size: Float?,
    coordinates: Pair<Float, Float>
) {
    val sizeLessNull = size ?: 5f
    val topPoint = Offset(x = coordinates.first, y = coordinates.second.minus(sizeLessNull))
    val bottomRight = Offset(x = coordinates.first.plus(sizeLessNull), y = coordinates.second.plus(sizeLessNull))
    val bottomLeft = Offset(x = coordinates.first.minus(sizeLessNull), y = coordinates.second.plus(sizeLessNull))
    val path = Path().apply {
        moveTo(x = topPoint.x, y = topPoint.y)
        lineTo(x = bottomRight.x, y = bottomRight.y)
        lineTo(x = bottomLeft.x, y = bottomLeft.y)
        close()
    }

    drawPath(path = path, color = color ?: Color.White)
}