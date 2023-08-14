package com.joshrose.plotsforcompose.internals.markers

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke

internal fun DrawScope.markerButton(
    color: Color?,
    radius: Float?,
    coordinates: Pair<Float, Float>
) {
    val sizeLessNull = radius ?: 5f

    markerPlus(
        color = color ?: Color.White,
        length = sizeLessNull,
        coordinates = coordinates
    )

    drawCircle(
        color = color ?: Color.White,
        radius = sizeLessNull,
        center = Offset(coordinates.first, coordinates.second),
        style = Stroke(width = sizeLessNull.div(2f))
    )
}