package com.joshrose.plotsforcompose.internals.markers

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.joshrose.plotsforcompose.internals.plots.lines.MarkerProperties

internal fun DrawScope.markerCircle(markerProperties: MarkerProperties) {
    drawCircle(
        color = markerProperties.color ?: Color.White,
        radius = markerProperties.size ?: 5f,
        center = Offset(markerProperties.coordinates.first, markerProperties.coordinates.second)
    )
}