package com.joshrose.common.ui.linegraph.marker.marker_shapes

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.joshrose.plotsforcompose.util.Markers

fun interface DrawShape {
    @Composable fun draw(properties: CanvasProperties)
}

data class CanvasProperties(
    val color: Color,
    val action: (Markers) -> Unit,
    val modifier: Modifier = Modifier,
    val size: Dp = 28.dp,
    val shape: Shape = CircleShape,
)