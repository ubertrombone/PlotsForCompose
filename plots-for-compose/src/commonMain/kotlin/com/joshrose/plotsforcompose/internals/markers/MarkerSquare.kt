package com.joshrose.plotsforcompose.internals.markers

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.joshrose.plotsforcompose.internals.plots.lines.MarkerProperties

internal fun DrawScope.markerSquare(markerProperties: MarkerProperties) {
    val sizeLessNull = markerProperties.size?.times(2f) ?: 10f
    drawRect(
        color = markerProperties.color ?: Color.White,
        size = Size(width = sizeLessNull, height = sizeLessNull),
        topLeft = Offset(
            x = markerProperties.coordinates.first.minus(sizeLessNull.div(2f)),
            y = markerProperties.coordinates.second.minus(sizeLessNull.div(2f))
        )
    )
}