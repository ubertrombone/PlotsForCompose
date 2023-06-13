package com.joshrose.plotsforcompose.linegraph.config

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.joshrose.plotsforcompose.linegraph.util.LineType
import com.joshrose.plotsforcompose.linegraph.util.LineType.STRAIGHT
import com.joshrose.plotsforcompose.theme.md_theme_dark_primary

class LineGraphConfiguration(
    var lineType: LineType = STRAIGHT,
    var markers: Boolean = false,
    var strokeWidth: Dp = 2.dp,
    var markerSize: Dp? = null,
    var lineColor: Color = md_theme_dark_primary,
    var markerColor: Color? = null
) {
    override fun toString() = "LineGraphConfiguration"

    companion object {
        fun lineGraphConfiguration(init: LineGraphConfiguration.() -> Unit = {}) =
            LineGraphConfiguration().apply(init)
    }
}