package com.joshrose.common.ui.linegraph.marker.marker_shapes

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import kotlin.math.hypot

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Snowflake(color: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier, contentDescription = "Snowflake Marker Shape") {
        val sizeLessNull = size.center.x
        val diagonalLength = hypot(x = sizeLessNull, y = sizeLessNull)

        drawLine(
            color = color,
            start = Offset(x = 0f, y = 0f),
            end = Offset(x = size.width, y = size.height),
            strokeWidth = sizeLessNull.div(3f)
        )

        drawLine(
            color = color,
            start = Offset(x = 0f, y = size.height),
            end = Offset(x = size.width, y = 0f),
            strokeWidth = sizeLessNull.div(3f)
        )

        drawLine(
            color = color,
            start = Offset(x = size.center.x, y = size.center.y.minus(diagonalLength)),
            end = Offset(x = size.center.x, y = size.center.y.plus(diagonalLength)),
            strokeWidth = sizeLessNull.div(3f)
        )

        drawLine(
            color = color,
            start = Offset(x = size.center.x.minus(diagonalLength), y = size.center.y),
            end = Offset(x = size.center.x.plus(diagonalLength), y = size.center.y),
            strokeWidth = sizeLessNull.div(3f)
        )
    }
}