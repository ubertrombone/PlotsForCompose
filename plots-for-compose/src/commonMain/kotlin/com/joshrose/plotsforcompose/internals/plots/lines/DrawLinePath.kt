package com.joshrose.plotsforcompose.internals.plots.lines

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import com.joshrose.plotsforcompose.linegraph.config.LineGraphConfiguration

fun DrawScope.drawLinePath(
    coordinates: List<Pair<Float, Float>>,
    path: Path,
    configs: LineGraphConfiguration
) {
    // TODO: Consider adding join as a config option
    drawPath(
        path = path,
        color = configs.lineColor,
        style = Stroke(
            width = configs.strokeWidth.toPx(),
            pathEffect = configs.pathEffect,
            join = StrokeJoin.Bevel
        )
    )

    coordinates.forEach {
        if (configs.markers) {
            drawCircle(
                color = configs.markerColor ?: Color.White,
                radius = configs.markerSize?.toPx() ?: 5f,
                center = Offset(it.first, it.second)
            )
        }
    }
}