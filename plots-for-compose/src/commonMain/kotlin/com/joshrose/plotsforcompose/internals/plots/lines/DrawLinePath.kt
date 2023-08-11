package com.joshrose.plotsforcompose.internals.plots.lines

import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import com.joshrose.plotsforcompose.internals.markers.markerCircle
import com.joshrose.plotsforcompose.internals.markers.markerSquare
import com.joshrose.plotsforcompose.linegraph.config.LineGraphConfiguration
import com.joshrose.plotsforcompose.util.Markers

fun DrawScope.drawLinePath(
    coordinates: List<Pair<Float, Float>>,
    path: Path,
    configs: LineGraphConfiguration
) {
    drawPath(
        path = path,
        color = configs.lineColor,
        style = Stroke(
            width = configs.strokeWidth.toPx(),
            pathEffect = configs.pathEffect,
            join = configs.strokeJoin
        )
    )

    // TODO: Add config for kind of marker
    // TODO: make sure label marker matches marker shape
    coordinates.forEach {
        if (configs.markers) {
            when (configs.markerShape) {
                Markers.SQUARE -> markerSquare(
                    color = configs.markerColor,
                    size = configs.markerSize?.toPx(),
                    coordinates = it
                )
                else -> markerCircle(
                    color = configs.markerColor,
                    radius = configs.markerSize?.toPx(),
                    coordinates = it
                )
            }
        }
    }
}