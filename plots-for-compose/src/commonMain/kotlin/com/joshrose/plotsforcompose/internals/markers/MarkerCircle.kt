package com.joshrose.plotsforcompose.internals.markers

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope

fun DrawScope.markerCircle(
    color: Color?,
    radius: Float?,
    coordinates: Pair<Float, Float>
) {
    drawCircle(
        color = color ?: Color.White,
        radius = radius ?: 5f,
        center = Offset(coordinates.first, coordinates.second)
    )
}