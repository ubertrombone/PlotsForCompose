package com.joshrose.plotsforcompose.internals.markers

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import com.joshrose.plotsforcompose.internals.plots.lines.MarkerProperties

internal fun DrawScope.markerButton(markerProperties: MarkerProperties) {
    val sizeLessNull = markerProperties.size ?: 5f

    markerPlus(markerProperties)

    drawCircle(
        color = markerProperties.color ?: Color.White,
        radius = sizeLessNull,
        center = Offset(markerProperties.coordinates.first, markerProperties.coordinates.second),
        style = Stroke(width = sizeLessNull.div(2f))
    )
}