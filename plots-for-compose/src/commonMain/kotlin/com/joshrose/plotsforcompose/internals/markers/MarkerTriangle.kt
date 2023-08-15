package com.joshrose.plotsforcompose.internals.markers

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.joshrose.plotsforcompose.internals.plots.lines.MarkerProperties

@Suppress("DuplicatedCode")
internal fun DrawScope.markerTriangle(markerProperties: MarkerProperties) {
    val sizeLessNull = markerProperties.size ?: 5f
    val topPoint = Offset(x = markerProperties.coordinates.first, y = markerProperties.coordinates.second.minus(sizeLessNull))
    val bottomRight = Offset(x = markerProperties.coordinates.first.plus(sizeLessNull), y = markerProperties.coordinates.second.plus(sizeLessNull))
    val bottomLeft = Offset(x = markerProperties.coordinates.first.minus(sizeLessNull), y = markerProperties.coordinates.second.plus(sizeLessNull))
    val path = Path().apply {
        moveTo(x = topPoint.x, y = topPoint.y)
        lineTo(x = bottomRight.x, y = bottomRight.y)
        lineTo(x = bottomLeft.x, y = bottomLeft.y)
        close()
    }

    drawPath(path = path, color = markerProperties.color ?: Color.White)
}