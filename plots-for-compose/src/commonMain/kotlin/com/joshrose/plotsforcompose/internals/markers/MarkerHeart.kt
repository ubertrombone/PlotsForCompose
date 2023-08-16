package com.joshrose.plotsforcompose.internals.markers

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import com.joshrose.plotsforcompose.internals.plots.lines.MarkerProperties
import com.joshrose.plotsforcompose.internals.util.toRadians
import kotlin.math.sin
import kotlin.math.tan

@Suppress("DuplicatedCode")
internal fun DrawScope.markerHeart(markerProperties: MarkerProperties) {
    val sizeLessNull = markerProperties.size ?: 10f
    val radius = sizeLessNull.div(1.5f)

    val bottomPoint = Offset(x = markerProperties.coordinates.first, y = markerProperties.coordinates.second.plus(sizeLessNull))

    val topPointXOffset = sizeLessNull.plus(radius).times(tan(37f.toRadians()))
    val leftPoint = Offset(x = markerProperties.coordinates.first.minus(topPointXOffset), y = markerProperties.coordinates.second.minus(radius))
    val rightPoint = Offset(x = markerProperties.coordinates.first.plus(topPointXOffset), y = markerProperties.coordinates.second.minus(radius))

    val circleYCenterOffset = radius.times(sin(30f.toRadians()))
    val circleCenterY = leftPoint.y.minus(circleYCenterOffset)

    val path = Path().apply {

//        addArc(
//            oval = Rect(
//                center = Offset(x = markerProperties.coordinates.first.minus(radius), y = circleCenterY),
//                radius = radius
//            ),
//            startAngleDegrees = 0f,
//            sweepAngleDegrees = 360f
//        )

        moveTo(x = markerProperties.coordinates.first, y = circleCenterY)
        cubicTo(
            x1 = markerProperties.coordinates.first, y1 = circleCenterY.minus(radius.times(1.5f)),
            x2 = markerProperties.coordinates.first.minus(radius.times(2f)), y2 = circleCenterY.minus(radius.times(1.5f)),
            x3 = markerProperties.coordinates.first.minus(radius.times(2f)), y3 = circleCenterY
        )
        cubicTo(
            x1 = markerProperties.coordinates.first.minus(radius.times(2f)), y1 = circleCenterY.plus(radius),
            x2 = markerProperties.coordinates.first, y2 = circleCenterY.plus(radius),
            x3 = markerProperties.coordinates.first, y3 = circleCenterY
        )
        cubicTo(
            x1 = markerProperties.coordinates.first, y1 = circleCenterY.minus(radius.times(1.5f)),
            x2 = markerProperties.coordinates.first.plus(radius.times(2f)), y2 = circleCenterY.minus(radius.times(1.5f)),
            x3 = markerProperties.coordinates.first.plus(radius.times(2f)), y3 = circleCenterY
        )
        cubicTo(
            x1 = markerProperties.coordinates.first.plus(radius.times(2f)), y1 = circleCenterY.plus(radius),
            x2 = markerProperties.coordinates.first, y2 = circleCenterY.plus(radius),
            x3 = markerProperties.coordinates.first, y3 = circleCenterY
        )

        moveTo(x = bottomPoint.x, y = bottomPoint.y)
        lineTo(x = leftPoint.x, y = leftPoint.y)
        lineTo(x = rightPoint.x, y = rightPoint.y)
        close()

        moveTo(x = markerProperties.coordinates.first.minus(topPointXOffset), y = circleCenterY)
        lineTo(x = markerProperties.coordinates.first.plus(topPointXOffset), y = circleCenterY)
        lineTo(x = markerProperties.coordinates.first.plus(topPointXOffset), y = circleCenterY.plus(circleYCenterOffset))
        lineTo(x = markerProperties.coordinates.first.minus(topPointXOffset), y = circleCenterY.plus(circleYCenterOffset))
        close()

//        addArc(
//            oval = Rect(
//                center = Offset(x = markerProperties.coordinates.first.plus(radius), y = circleCenterY),
//                radius = radius
//            ),
//            startAngleDegrees = 0f,
//            sweepAngleDegrees = 360f
//        )

    }
    drawPath(path = path, color = markerProperties.color ?: Color.White, style = Fill)
}