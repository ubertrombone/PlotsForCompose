package com.joshrose.common.ui.linegraph.marker.marker_shapes

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Triangle(color: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier, contentDescription = "Triangle Shape Marker") {
        val topPoint = Offset(x = this.size.width.div(2f), y = 0f)
        val bottomRight = Offset(x = this.size.width, y = this.size.height)
        val bottomLeft = Offset(x = 0f, y = this.size.height)
        val path = Path().apply {
            moveTo(x = topPoint.x, y = topPoint.y)
            lineTo(x = bottomRight.x, y = bottomRight.y)
            lineTo(x = bottomLeft.x, y = bottomLeft.y)
            close()
        }

        drawPath(path = path, color = color)
    }
}

//@Suppress("FunctionName")
//fun TriangleShape(size: Dp): Shape = object : Shape {
//    val topPoint = Offset(x = size.div(2.dp), y = 0f)
//    val bottomRight = Offset(x = size.value, y = size.value)
//    val bottomLeft = Offset(x = 0f, y = size.value)
//    val path = Path().apply {
//        moveTo(x = topPoint.x, y = topPoint.y)
//        lineTo(x = bottomRight.x, y = bottomRight.y)
//        lineTo(x = bottomLeft.x, y = bottomLeft.y)
//        close()
//    }
//    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline =
//        Outline.Generic(path = path)
//}

val TriangleShape: Shape = object : Shape {
    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {
        val topPoint = Offset(x = size.width.div(2f), y = 0f)
        val bottomRight = Offset(x = size.width, y = size.height)
        val bottomLeft = Offset(x = 0f, y = size.height)
        val path = Path().apply {
            moveTo(x = topPoint.x, y = topPoint.y)
            lineTo(x = bottomRight.x, y = bottomRight.y)
            lineTo(x = bottomLeft.x, y = bottomLeft.y)
            close()
        }

        return Outline.Generic(path = path)
    }
}