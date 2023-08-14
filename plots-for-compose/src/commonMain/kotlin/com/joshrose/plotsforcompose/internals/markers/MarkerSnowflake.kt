package com.joshrose.plotsforcompose.internals.markers

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import kotlin.math.hypot

@Suppress("DuplicatedCode")
internal fun DrawScope.markerSnowflake(
    color: Color?,
    length: Float?,
    coordinates: Pair<Float, Float>
) {
    val sizeLessNull = length ?: 5f
    val diagonalLength = hypot(x = sizeLessNull, y = sizeLessNull)

    drawLine(
        color = color ?: Color.White,
        start = Offset(x = coordinates.first.minus(sizeLessNull), y = coordinates.second.minus(sizeLessNull)),
        end = Offset(x = coordinates.first.plus(sizeLessNull), y = coordinates.second.plus(sizeLessNull)),
        strokeWidth = sizeLessNull.div(2f)
    )

    drawLine(
        color = color ?: Color.White,
        start = Offset(x = coordinates.first.minus(sizeLessNull), y = coordinates.second.plus(sizeLessNull)),
        end = Offset(x = coordinates.first.plus(sizeLessNull), y = coordinates.second.minus(sizeLessNull)),
        strokeWidth = sizeLessNull.div(2f)
    )

    drawLine(
        color = color ?: Color.White,
        start = Offset(x = coordinates.first, y = coordinates.second.minus(diagonalLength)),
        end = Offset(x = coordinates.first, y = coordinates.second.plus(diagonalLength)),
        strokeWidth = sizeLessNull.div(2f)
    )

    drawLine(
        color = color ?: Color.White,
        start = Offset(x = coordinates.first.minus(diagonalLength), y = coordinates.second),
        end = Offset(x = coordinates.first.plus(diagonalLength), y = coordinates.second),
        strokeWidth = sizeLessNull.div(2f)
    )
}