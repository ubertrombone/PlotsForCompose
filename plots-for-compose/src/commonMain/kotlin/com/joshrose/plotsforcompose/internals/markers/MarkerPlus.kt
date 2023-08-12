package com.joshrose.plotsforcompose.internals.markers

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope

internal fun DrawScope.markerPlus(
    color: Color?,
    length: Float?,
    coordinates: Pair<Float, Float>
) {
    val sizeLessNull = (length ?: 10f).div(2f)

    drawLine(
        color = color ?: Color.White,
        start = Offset(x = coordinates.first.minus(sizeLessNull), y = coordinates.second),
        end = Offset(x = coordinates.first.plus(sizeLessNull), y = coordinates.second),
        strokeWidth = sizeLessNull.div(2f)
    )

    drawLine(
        color = color ?: Color.White,
        start = Offset(x = coordinates.first, y = coordinates.second.minus(sizeLessNull)),
        end = Offset(x = coordinates.first, y = coordinates.second.plus(sizeLessNull)),
        strokeWidth = sizeLessNull.div(2f)
    )
}