package com.joshrose.plotsforcompose.internals.markers

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.joshrose.plotsforcompose.internals.util.toRadians
import kotlin.math.sin
import kotlin.math.tan

@Suppress("DuplicatedCode")
internal fun DrawScope.markerHeart(
    color: Color?,
    size: Float?,
    coordinates: Pair<Float, Float>
) {
    val sizeLessNull = size ?: 10f
    val radius = sizeLessNull.div(1.5f)

    val bottomPoint = Offset(x = coordinates.first, y = coordinates.second.plus(sizeLessNull))

    val topPointXOffset = sizeLessNull.plus(radius).times(tan(37f.toRadians()))
    val leftPoint = Offset(x = coordinates.first.minus(topPointXOffset), y = coordinates.second.minus(radius))
    val rightPoint = Offset(x = coordinates.first.plus(topPointXOffset), y = coordinates.second.minus(radius))

    val path = Path().apply {
        moveTo(x = bottomPoint.x, y = bottomPoint.y)
        lineTo(x = leftPoint.x, y = leftPoint.y)
        lineTo(x = rightPoint.x, y = rightPoint.y)
        close()
    }
    drawPath(path = path, color = color ?: Color.White)

    val circleYCenterOffset = radius.times(sin(30f.toRadians()))
    val circleCenterY = leftPoint.y.minus(circleYCenterOffset)

    drawCircle(
        color = color ?: Color.White,
        radius = radius,
        center = Offset(x = coordinates.first.minus(radius), y = circleCenterY)
    )
    drawCircle(
        color = color ?: Color.White,
        radius = radius,
        center = Offset(x = coordinates.first.plus(radius), y = circleCenterY)
    )

    drawRect(
        color = color ?: Color.White,
        topLeft = Offset(x = coordinates.first.minus(topPointXOffset), y = circleCenterY),
        size = Size(width = topPointXOffset.times(2f), height = circleYCenterOffset)
    )
}