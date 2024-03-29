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
import com.joshrose.plotsforcompose.util.Markers.DIAMOND

@OptIn(ExperimentalFoundationApi::class)
val diamond = DrawShape {
    Canvas(
        modifier = it.modifier
            .size(it.size)
            .clip(it.shape)
            .clickable { it.action(DIAMOND) },
        contentDescription = "Diamond Marker Shape"
    ) {
        drawPath(path = diamondPath(size), color = it.color)
    }
}

val DiamondShape: Shape = object : Shape {
    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline =
        Outline.Generic(path = diamondPath(size))
}

@Suppress("DuplicatedCode")
private fun diamondPath(size: Size): Path {
    val threeQuartersX = size.center.x.times(.75f)
    val topPoint = Offset(x = size.center.x, y = 0f)
    val rightPoint = Offset(x = size.center.x.plus(threeQuartersX), y = size.center.y)
    val leftPoint = Offset(x = size.center.x.minus(threeQuartersX), y = size.center.y)
    val bottomPoint = Offset(x = size.center.x, y = size.height)
    return Path().apply {
        moveTo(x = topPoint.x, y = topPoint.y)
        lineTo(x = rightPoint.x, y = rightPoint.y)
        lineTo(x = bottomPoint.x, y = bottomPoint.y)
        lineTo(x = leftPoint.x, y = leftPoint.y)
        close()
    }
}