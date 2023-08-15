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
import com.joshrose.plotsforcompose.util.Markers.TRIANGLE_DOWN

@OptIn(ExperimentalFoundationApi::class)
val triangleDown = DrawShape {
    Canvas(
        modifier = it.modifier
            .size(it.size)
            .clip(it.shape)
            .clickable { it.action(TRIANGLE_DOWN) },
        contentDescription = "Upside Down Triangle Marker Shape"
    ) {
        drawPath(path = triangleDownPath(size), color = it.color)
    }
}

val TriangleDownShape: Shape = object : Shape {
    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline =
        Outline.Generic(path = triangleDownPath(size))
}

private fun triangleDownPath(size: Size): Path {
    val bottomPoint = Offset(x = size.center.x, y = size.height)
    val topRight = Offset(x = size.width, y = 0f)
    val topLeft = Offset(x = 0f, y = 0f)
    return Path().apply {
        moveTo(x = bottomPoint.x, y = bottomPoint.y)
        lineTo(x = topRight.x, y = topRight.y)
        lineTo(x = topLeft.x, y = topLeft.y)
        close()
    }
}