package com.joshrose.plotsforcompose.internals.markers

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.joshrose.plotsforcompose.internals.plots.lines.MarkerProperties

internal fun DrawScope.markerPlus(markerProperties: MarkerProperties) {
    val sizeLessNull = markerProperties.size ?: 5f

    drawLine(
        color = markerProperties.color ?: Color.White,
        start = Offset(x = markerProperties.coordinates.first.minus(sizeLessNull), y = markerProperties.coordinates.second),
        end = Offset(x = markerProperties.coordinates.first.plus(sizeLessNull), y = markerProperties.coordinates.second),
        strokeWidth = sizeLessNull.div(2f)
    )

    drawLine(
        color = markerProperties.color ?: Color.White,
        start = Offset(x = markerProperties.coordinates.first, y = markerProperties.coordinates.second.minus(sizeLessNull)),
        end = Offset(x = markerProperties.coordinates.first, y = markerProperties.coordinates.second.plus(sizeLessNull)),
        strokeWidth = sizeLessNull.div(2f)
    )
}