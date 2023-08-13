package com.joshrose.plotsforcompose.internals.markers

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope

@Suppress("DuplicatedCode")
internal fun DrawScope.markerDiamond(
    color: Color?,
    size: Float?,
    coordinates: Pair<Float, Float>
) {
    val sizeLessNull = size ?: 5f
    val threeQuartersX = sizeLessNull.times(.75f)
    val topPoint = Offset(x = coordinates.first, y = coordinates.second.minus(sizeLessNull))
    val rightPoint = Offset(x = coordinates.first.plus(threeQuartersX), y = coordinates.second)
    val leftPoint = Offset(x = coordinates.first.minus(threeQuartersX), y = coordinates.second)
    val bottomPoint = Offset(x = coordinates.first, y = coordinates.second.plus(sizeLessNull))
    val path = Path().apply {
        moveTo(x = topPoint.x, y = topPoint.y)
        lineTo(x = rightPoint.x, y = rightPoint.y)
        lineTo(x = bottomPoint.x, y = bottomPoint.y)
        lineTo(x = leftPoint.x, y = leftPoint.y)
        close()
    }

    drawPath(path = path, color = color ?: Color.White)
}