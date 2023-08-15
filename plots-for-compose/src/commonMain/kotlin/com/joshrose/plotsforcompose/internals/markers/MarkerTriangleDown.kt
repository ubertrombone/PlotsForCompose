package com.joshrose.plotsforcompose.internals.markers

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.joshrose.plotsforcompose.internals.plots.lines.MarkerProperties

@Suppress("DuplicatedCode")
internal fun DrawScope.markerTriangleDown(markerProperties: MarkerProperties) {
    val sizeLessNull = markerProperties.size ?: 5f
    val bottomPoint = Offset(x = markerProperties.coordinates.first, y = markerProperties.coordinates.second.plus(sizeLessNull))
    val topRight = Offset(x = markerProperties.coordinates.first.plus(sizeLessNull), y = markerProperties.coordinates.second.minus(sizeLessNull))
    val topLeft = Offset(x = markerProperties.coordinates.first.minus(sizeLessNull), y = markerProperties.coordinates.second.minus(sizeLessNull))
    val path = Path().apply {
        moveTo(x = bottomPoint.x, y = bottomPoint.y)
        lineTo(x = topRight.x, y = topRight.y)
        lineTo(x = topLeft.x, y = topLeft.y)
        close()
    }

    drawPath(path = path, color = markerProperties.color ?: Color.White)
}