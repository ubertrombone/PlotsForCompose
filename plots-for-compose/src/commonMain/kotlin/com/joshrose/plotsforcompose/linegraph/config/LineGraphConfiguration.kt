package com.joshrose.plotsforcompose.linegraph.config

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.joshrose.plotsforcompose.linegraph.util.LineType
import com.joshrose.plotsforcompose.linegraph.util.LineType.STRAIGHT
import com.joshrose.plotsforcompose.theme.md_theme_dark_primary

// TODO: Add path effect?
// TODO: Add shading option?
data class LineGraphConfiguration(
    var lineType: LineType = STRAIGHT,
    var lineColor: Color = md_theme_dark_primary,
    var strokeWidth: Dp = 2.dp,
    var markers: Boolean = false,
    var markerSize: Dp? = null,
    var markerColor: Color? = null
) {
    override fun toString() = "LineGraphConfiguration"
}

fun lineGraphConfiguration(init: LineGraphConfiguration.() -> Unit = {}) = LineGraphConfiguration().apply(init)