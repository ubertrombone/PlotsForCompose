package com.joshrose.plotsforcompose.internals.markers

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

internal fun DrawScope.markerStar(
    color: Color?,
    size: Float?,
    coordinates: Pair<Float, Float>
) {
    val sizeLessNull = (size ?: 10f).times(2f)

    val topPoint = Offset(x = coordinates.first, y = coordinates.second.minus(sizeLessNull.div(2f)))
    val bottomRightPoint = Offset(
        x = sizeLessNull
            .times(sin(18f.toRadians()))
            .plus(topPoint.x),
        y = sizeLessNull
            .times(cos(18f.toRadians()))
            .plus(topPoint.y)
    )
    val bottomLeftPoint = Offset(
        x = topPoint.x.minus(sizeLessNull.times(sin(18f.toRadians()))),
        y = sizeLessNull
            .times(cos(18f.toRadians()))
            .plus(topPoint.y)
    )
    val topRightPoint = Offset(
        x = sizeLessNull
            .times(cos(36f.toRadians()))
            .plus(bottomLeftPoint.x),
        y = bottomLeftPoint.y.minus(sizeLessNull.times(sin(36f.toRadians())))
    )
    val topLeftPoint = Offset(
        x = topRightPoint.x.minus(sizeLessNull),
        y = topRightPoint.y
    )

    val path = Path().apply {
        moveTo(x = topPoint.x, y = topPoint.y)
        lineTo(x = bottomRightPoint.x, y = bottomRightPoint.y)
        lineTo(x = topLeftPoint.x, y = topLeftPoint.y)
        lineTo(x = topRightPoint.x, y = topRightPoint.y)
        lineTo(x = bottomLeftPoint.x, y = bottomLeftPoint.y)
        close()
    }

    drawPath(path = path, color = color ?: Color.White)
}

fun Float.toRadians() = this * (PI / 180f).toFloat()