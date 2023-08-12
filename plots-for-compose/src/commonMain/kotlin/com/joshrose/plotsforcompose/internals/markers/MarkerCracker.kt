package com.joshrose.plotsforcompose.internals.markers

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate

internal fun DrawScope.markerCracker(
    color: Color?,
    radius: Float?,
    coordinates: Pair<Float, Float>
) {
    rotate(degrees = 45f, pivot = Offset(x = coordinates.first, y = coordinates.second)) {
        markerButton(
            color = color,
            radius = radius,
            coordinates = coordinates
        )
    }
}