package com.joshrose.plotsforcompose.linegraph.config

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joshrose.plotsforcompose.linegraph.util.LineType
import com.joshrose.plotsforcompose.linegraph.util.LineType.STRAIGHT
import com.joshrose.plotsforcompose.linegraph.util.Radius
import com.joshrose.plotsforcompose.linegraph.util.Style
import com.joshrose.plotsforcompose.theme.md_theme_dark_onBackground
import com.joshrose.plotsforcompose.theme.md_theme_dark_primary
import com.joshrose.plotsforcompose.theme.md_theme_dark_secondary
import com.joshrose.plotsforcompose.util.Markers

data class LineGraphConfiguration(
    var lineType: LineType = STRAIGHT,
    var lineColor: Color = md_theme_dark_primary,
    var strokeWidth: Dp = 2.dp,
    var strokeJoin: StrokeJoin = StrokeJoin.Bevel,
    var markers: Boolean = false,
    var markerSize: Dp? = null,
    var markerColor: Color? = null,
    var markerShape: Markers? = null,
    var pathEffect: PathEffect? = null,
    var hasDynamicLabels: Boolean = true,
    var labelFontColor: Color = md_theme_dark_secondary,
    var labelFontSize: TextUnit = 12.sp,
    var boxColor: Color = md_theme_dark_onBackground,
    var boxAlpha: Float = .3f,
    var rectCornerRadius: CornerRadius = CornerRadius(x = 0f, y = 0f),
    var labelMarkerColor: Color = md_theme_dark_onBackground,
    var labelMarkerRadius: Radius = Radius.Auto,
    var labelMarkerStyle: Style = Style.Auto,
    var labelLineColor: Color = md_theme_dark_onBackground,
    var labelLineAlpha: Float = .5f,
    var labelLineStrokeWidth: Float = 3f,
    var labelLineStrokeCap: StrokeCap = StrokeCap.Round
) {
    // TODO: Make these config toString()s more informative
    override fun toString() = "LineGraphConfiguration"
}

fun lineGraphConfiguration(init: LineGraphConfiguration.() -> Unit = {}) = LineGraphConfiguration().apply(init)