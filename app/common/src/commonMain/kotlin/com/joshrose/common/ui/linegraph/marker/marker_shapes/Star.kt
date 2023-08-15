package com.joshrose.common.ui.linegraph.marker.marker_shapes

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import com.joshrose.plotsforcompose.internals.util.toRadians
import com.joshrose.plotsforcompose.util.Markers.STAR
import kotlin.math.cos
import kotlin.math.sin

@OptIn(ExperimentalFoundationApi::class)
val star = DrawShape {
    Canvas(
        modifier = it.modifier
            .size(it.size)
            .clip(it.shape)
            .clickable { it.action(STAR) },
        contentDescription = "Star Marker Shape"
    ) {
        drawPath(path = drawStar(size), color = it.color)
    }
}

val StarShape: Shape = object : Shape {
    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline =
        Outline.Generic(path = drawStar(size))
}

@Suppress("DuplicatedCode")
private fun drawStar(size: Size): Path {
    val topPoint = Offset(x = size.center.x, y = 0f)
    val bottomRightPoint = Offset(
        x = size.width
            .times(sin(18f.toRadians()))
            .plus(topPoint.x),
        y = size.width
            .times(cos(18f.toRadians()))
            .plus(topPoint.y)
    )
    val bottomLeftPoint = Offset(
        x = topPoint.x.minus(size.width.times(sin(18f.toRadians()))),
        y = size.width
            .times(cos(18f.toRadians()))
            .plus(topPoint.y)
    )
    val topRightPoint = Offset(
        x = size.width
            .times(cos(36f.toRadians()))
            .plus(bottomLeftPoint.x),
        y = bottomLeftPoint.y.minus(size.width.times(sin(36f.toRadians())))
    )
    val topLeftPoint = Offset(
        x = topRightPoint.x.minus(size.width),
        y = topRightPoint.y
    )

    return Path().apply {
        moveTo(x = topPoint.x, y = topPoint.y)
        lineTo(x = bottomRightPoint.x, y = bottomRightPoint.y)
        lineTo(x = topLeftPoint.x, y = topLeftPoint.y)
        lineTo(x = topRightPoint.x, y = topRightPoint.y)
        lineTo(x = bottomLeftPoint.x, y = bottomLeftPoint.y)
        close()
    }
}