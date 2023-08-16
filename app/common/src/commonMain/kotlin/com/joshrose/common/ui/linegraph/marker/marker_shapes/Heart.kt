package com.joshrose.common.ui.linegraph.marker.marker_shapes

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import com.joshrose.plotsforcompose.internals.util.toRadians
import com.joshrose.plotsforcompose.util.Markers.HEART
import kotlin.math.sin
import kotlin.math.tan

@OptIn(ExperimentalFoundationApi::class)
val heart = DrawShape {
    Canvas(
        modifier = it.modifier
            .size(it.size)
            .clip(it.shape)
            .clickable { it.action(HEART) },
        contentDescription = "Heart Marker Shape"
    ) {
        drawPath(path = drawHeart(size), color = it.color)
    }
}

val HeartShape: Shape = object : Shape {
    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline =
        Outline.Generic(drawHeart(size))
}

@Suppress("DuplicatedCode")
private fun drawHeart(size: Size): Path {
    val halfSize = size.width.div(2f)
    val radius = halfSize.div(1.5f)

    val bottomPoint = Offset(x = size.center.x, y = size.center.y.plus(halfSize))

    val topPointXOffset = halfSize.plus(radius).times(tan(37f.toRadians()))
    val leftPoint = Offset(x = size.center.x.minus(topPointXOffset), y = size.center.y.minus(radius))
    val rightPoint = Offset(x = size.center.x.plus(topPointXOffset), y = size.center.y.minus(radius))

    val circleYCenterOffset = radius.times(sin(30f.toRadians()))
    val circleCenterY = leftPoint.y.minus(circleYCenterOffset)

    return Path().apply {
        moveTo(x = bottomPoint.x, y = bottomPoint.y)
        lineTo(x = leftPoint.x, y = leftPoint.y)
        lineTo(x = rightPoint.x, y = rightPoint.y)
        close()

        addArc(
            oval = Rect(
                center = Offset(x = size.center.x.minus(radius), y = circleCenterY),
                radius = radius
            ),
            startAngleDegrees = 0f,
            sweepAngleDegrees = 360f
        )

        addArc(
            oval = Rect(
                center = Offset(x = size.center.x.plus(radius), y = circleCenterY),
                radius = radius
            ),
            startAngleDegrees = 0f,
            sweepAngleDegrees = 360f
        )

        moveTo(x = size.center.x.minus(topPointXOffset), y = circleCenterY)
        lineTo(x = size.center.x.plus(topPointXOffset), y = circleCenterY)
        lineTo(x = size.center.x.plus(topPointXOffset), y = circleCenterY.plus(circleYCenterOffset))
        lineTo(x = size.center.x.minus(topPointXOffset), y = circleCenterY.plus(circleYCenterOffset))
        close()
    }
}