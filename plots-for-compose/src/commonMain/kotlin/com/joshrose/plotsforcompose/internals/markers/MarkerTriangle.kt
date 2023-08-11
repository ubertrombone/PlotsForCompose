package com.joshrose.plotsforcompose.internals.markers

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import kotlin.math.hypot

fun DrawScope.markerTriangle(
    color: Color?,
    size: Float?,
    coordinates: Pair<Float, Float>
) {
    val sizeLessNull = size ?: 5f
    val sideLength = hypot(x = sizeLessNull.div(2f), y = sizeLessNull)
    val path = Path().moveTo(x = center.x, y = center.y.minus(sizeLessNull.div(2f)))
}