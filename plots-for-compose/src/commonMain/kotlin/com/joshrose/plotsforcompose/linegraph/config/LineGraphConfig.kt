package com.joshrose.plotsforcompose.linegraph.config

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.joshrose.plotsforcompose.linegraph.util.LineType
import com.joshrose.plotsforcompose.linegraph.util.LineType.STRAIGHT
import com.joshrose.plotsforcompose.theme.md_theme_dark_primary

data class LineGraphConfig(
    val lineType: LineType,
    val markers: Boolean,
    val strokeWidth: Dp,
    val markerSize: Dp?,
    val lineColor: Color,
    val markerColor: Color?
)

object LineGraphConfigDefaults {
    fun lineGraphConfigDefaults(
        lineType: LineType = STRAIGHT,
        markers: Boolean = false,
        strokeWidth: Dp = 2.dp,
        markerSize: Dp? = null,
        lineColor: Color = md_theme_dark_primary,
        markerColor: Color? = null
    ): LineGraphConfig = LineGraphConfig(
        lineType = lineType,
        markers = markers,
        strokeWidth = strokeWidth,
        markerSize = markerSize,
        lineColor = lineColor,
        markerColor = markerColor
    )
}
