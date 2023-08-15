package com.joshrose.common.ui.linegraph.marker.marker_shapes

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import com.joshrose.plotsforcompose.util.Markers.SNOWFLAKE
import kotlin.math.hypot

@OptIn(ExperimentalFoundationApi::class)
val snowflake = DrawShape {
    Canvas(
        modifier = it.modifier
            .size(it.size)
            .clip(it.shape)
            .clickable { it.action(SNOWFLAKE) },
        contentDescription = "Snowflake Marker Shape"
    ) {
        val sizeLessNull = size.center.x
        val diagonalLength = hypot(x = sizeLessNull, y = sizeLessNull)

        drawLine(
            color = it.color,
            start = Offset(x = 0f, y = 0f),
            end = Offset(x = size.width, y = size.height),
            strokeWidth = sizeLessNull.div(3f)
        )

        drawLine(
            color = it.color,
            start = Offset(x = 0f, y = size.height),
            end = Offset(x = size.width, y = 0f),
            strokeWidth = sizeLessNull.div(3f)
        )

        drawLine(
            color = it.color,
            start = Offset(x = size.center.x, y = size.center.y.minus(diagonalLength)),
            end = Offset(x = size.center.x, y = size.center.y.plus(diagonalLength)),
            strokeWidth = sizeLessNull.div(3f)
        )

        drawLine(
            color = it.color,
            start = Offset(x = size.center.x.minus(diagonalLength), y = size.center.y),
            end = Offset(x = size.center.x.plus(diagonalLength), y = size.center.y),
            strokeWidth = sizeLessNull.div(3f)
        )
    }
}