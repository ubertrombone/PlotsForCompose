package com.joshrose.plotsforcompose.internals.plots.lines

import androidx.compose.ui.graphics.Color
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

    coordinates.forEach {
        val markerProperties = MarkerProperties(
            color = configs.markerColor,
            size = configs.markerSize?.toPx(),
            coordinates = it
        )
        if (configs.markers) {
            when (configs.markerShape) {
                Markers.SQUARE -> markerSquare(markerProperties)
                Markers.TRIANGLE -> markerTriangle(markerProperties)
                Markers.TRIANGLE_DOWN -> markerTriangleDown(markerProperties)
                Markers.X -> markerX(markerProperties)
                Markers.PLUS -> markerPlus(markerProperties)
                Markers.DIAMOND -> markerDiamond(markerProperties)
                Markers.SNOWFLAKE -> markerSnowflake(markerProperties)
                Markers.BUTTON -> markerButton(markerProperties)
                Markers.CRACKER -> markerCracker(markerProperties)
                Markers.STAR -> markerStar(markerProperties)
                Markers.HEART -> markerHeart(markerProperties)
                else -> markerCircle(markerProperties)
            }
        }
    }
}

data class MarkerProperties(
    val color: Color? = null,
    val size: Float? = null,
    val coordinates: Pair<Float, Float>
)