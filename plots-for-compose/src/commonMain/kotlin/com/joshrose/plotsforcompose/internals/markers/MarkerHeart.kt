package com.joshrose.plotsforcompose.internals.markers

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import com.joshrose.plotsforcompose.internals.plots.lines.MarkerProperties
import com.joshrose.plotsforcompose.internals.util.toRadians
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.sin
import kotlin.math.tan

@Suppress("DuplicatedCode")
internal fun DrawScope.markerHeart(markerProperties: MarkerProperties) {
    val sizeLessNull = markerProperties.size ?: 10f
    val radius = sizeLessNull.div(1.5f)
    val coordinates = Offset(x = markerProperties.coordinates.first, y = markerProperties.coordinates.second)

    val bottomPoint = Offset(x = coordinates.x, y = coordinates.y.plus(sizeLessNull))

    val topPointXOffset = sizeLessNull.plus(radius).times(tan(37f.toRadians()))
    val leftPoint = Offset(x = coordinates.x.minus(topPointXOffset), y = coordinates.y.minus(radius))

    val circleYCenterOffset = radius.times(sin(30f.toRadians()))
    val circleCenterY = leftPoint.y.minus(circleYCenterOffset)
    val leftCircleCenterX = coordinates.x.minus(radius)
    val rightCircleCenterX = coordinates.x.plus(radius)

    val startAngle = atan2(
        y = circleCenterY.minus(circleCenterY),
        x = coordinates.x.minus(leftCircleCenterX)
    )
    val bottomLeftAngle = atan2(
        y = leftPoint.y.minus(circleCenterY),
        x = leftPoint.x.minus(leftCircleCenterX)
    )
    val leftAngleBetween = Math.toDegrees(startAngle.minus(bottomLeftAngle).toDouble())
    val rightAngleBetween = 180f.minus(abs(leftAngleBetween)).plus(180f)

    val path = Path().apply {
        moveTo(x = bottomPoint.x, y = bottomPoint.y)
        lineTo(x = leftPoint.x, y = leftPoint.y)
        arcTo(
            rect = Rect(
                center = Offset(x = leftCircleCenterX, y = circleCenterY),
                radius = radius
            ),
            startAngleDegrees = abs(leftAngleBetween).toFloat(),
            sweepAngleDegrees = 360f.minus(abs(leftAngleBetween).toFloat()),
            forceMoveTo = false
        )
        arcTo(
            rect = Rect(
                center = Offset(x = rightCircleCenterX, y = circleCenterY),
                radius = radius
            ),
            startAngleDegrees = 180f,
            sweepAngleDegrees = rightAngleBetween.toFloat(),
            forceMoveTo = false
        )
        lineTo(x = bottomPoint.x, y = bottomPoint.y)
    }
    drawPath(path = path, color = markerProperties.color ?: Color.White, style = Fill)
}