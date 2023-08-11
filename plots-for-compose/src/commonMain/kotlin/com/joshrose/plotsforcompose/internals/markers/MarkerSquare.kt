package com.joshrose.plotsforcompose.internals.markers

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope

fun DrawScope.markerSquare(
    color: Color?,
    size: Float?,
    coordinates: Pair<Float, Float>
) {
    val sizeLessNull = size ?: 5f
    drawRect(
        color = color ?: Color.White,
        size = Size(width = sizeLessNull, height = sizeLessNull),
        topLeft = Offset(
            x = coordinates.first.minus(sizeLessNull.div(2f)),
            y = coordinates.second.minus(sizeLessNull.div(2f))
        )
    )
}