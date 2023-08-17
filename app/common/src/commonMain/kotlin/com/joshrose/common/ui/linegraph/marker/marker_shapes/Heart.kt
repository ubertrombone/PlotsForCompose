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
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.sin
import kotlin.math.tan

@OptIn(ExperimentalFoundationApi::class)
val heart = DrawShape {
    Canvas(
        modifier = it.modifier
            .size(it.size)
            .clip(HeartShape)
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

    val circleYCenterOffset = radius.times(sin(30f.toRadians()))
    val circleCenterY = leftPoint.y.minus(circleYCenterOffset)
    val leftCircleCenterX = size.center.x.minus(radius)
    val rightCircleCenterX = size.center.x.plus(radius)

    val startAngle = atan2(
        y = circleCenterY.minus(circleCenterY),
        x = size.center.x.minus(leftCircleCenterX)
    )
    val bottomLeftAngle = atan2(
        y = leftPoint.y.minus(circleCenterY),
        x = leftPoint.x.minus(leftCircleCenterX)
    )
    val leftAngleBetween = Math.toDegrees(startAngle.minus(bottomLeftAngle).toDouble())
    val rightAngleBetween = 180f.minus(abs(leftAngleBetween)).plus(180f)

    return Path().apply {
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
}