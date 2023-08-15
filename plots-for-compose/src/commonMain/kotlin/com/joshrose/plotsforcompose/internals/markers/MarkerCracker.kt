package com.joshrose.plotsforcompose.internals.markers

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import com.joshrose.plotsforcompose.internals.plots.lines.MarkerProperties

internal fun DrawScope.markerCracker(markerProperties: MarkerProperties) {
    rotate(degrees = 45f, pivot = Offset(x = markerProperties.coordinates.first, y = markerProperties.coordinates.second)) {
        markerButton(markerProperties)
    }
}