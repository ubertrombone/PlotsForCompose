package com.joshrose.plotsforcompose.internals.plots.lines

import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import com.joshrose.plotsforcompose.internals.markers.*
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

    // TODO: make sure label marker matches marker shape
    coordinates.forEach {
        if (configs.markers) {
            when (configs.markerShape) {
                Markers.SQUARE -> markerSquare(
                    color = configs.markerColor,
                    size = configs.markerSize?.toPx(),
                    coordinates = it
                )
                Markers.TRIANGLE -> markerTriangle(
                    color = configs.markerColor,
                    size = configs.markerSize?.toPx(),
                    coordinates = it
                )
                Markers.TRIANGLE_DOWN -> markerTriangleDown(
                    color = configs.markerColor,
                    size = configs.markerSize?.toPx(),
                    coordinates = it
                )
                Markers.X -> markerX(
                    color = configs.markerColor,
                    length = configs.markerSize?.toPx(),
                    coordinates = it
                )
                Markers.PLUS -> markerPlus(
                    color = configs.markerColor,
                    length = configs.markerSize?.toPx(),
                    coordinates = it
                )
                Markers.DIAMOND -> markerDiamond(
                    color = configs.markerColor,
                    size = configs.markerSize?.toPx(),
                    coordinates = it
                )
                Markers.SNOWFLAKE -> markerSnowflake(
                    color = configs.markerColor,
                    length = configs.markerSize?.toPx(),
                    coordinates = it
                )
                Markers.BUTTON -> markerButton(
                    color = configs.markerColor,
                    radius = configs.markerSize?.toPx(),
                    coordinates = it
                )
                Markers.CRACKER -> markerCracker(
                    color = configs.markerColor,
                    radius = configs.markerSize?.toPx(),
                    coordinates = it
                )
                Markers.STAR -> markerStar(
                    color = configs.markerColor,
                    size = configs.markerSize?.toPx(),
                    coordinates = it
                )
                Markers.HEART -> markerHeart(
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