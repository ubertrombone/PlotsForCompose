package com.joshrose.plotsforcompose.internals.markers

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.joshrose.plotsforcompose.internals.plots.lines.MarkerProperties

@Suppress("DuplicatedCode")
internal fun DrawScope.markerDiamond(markerProperties: MarkerProperties) {
    val sizeLessNull = markerProperties.size ?: 5f
    val threeQuartersX = sizeLessNull.times(.75f)
    val topPoint = Offset(x = markerProperties.coordinates.first, y = markerProperties.coordinates.second.minus(sizeLessNull))
    val rightPoint = Offset(x = markerProperties.coordinates.first.plus(threeQuartersX), y = markerProperties.coordinates.second)
    val leftPoint = Offset(x = markerProperties.coordinates.first.minus(threeQuartersX), y = markerProperties.coordinates.second)
    val bottomPoint = Offset(x = markerProperties.coordinates.first, y = markerProperties.coordinates.second.plus(sizeLessNull))
    val path = Path().apply {
        moveTo(x = topPoint.x, y = topPoint.y)
        lineTo(x = rightPoint.x, y = rightPoint.y)
        lineTo(x = bottomPoint.x, y = bottomPoint.y)
        lineTo(x = leftPoint.x, y = leftPoint.y)
        close()
    }

    drawPath(path = path, color = markerProperties.color ?: Color.White)
}