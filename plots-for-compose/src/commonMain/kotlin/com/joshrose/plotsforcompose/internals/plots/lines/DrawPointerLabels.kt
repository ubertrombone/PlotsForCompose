package com.joshrose.plotsforcompose.internals.plots.lines

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.joshrose.plotsforcompose.internals.StatKind
import com.joshrose.plotsforcompose.internals.StatKind.COUNT
import com.joshrose.plotsforcompose.linegraph.config.LineGraphConfiguration
import com.joshrose.plotsforcompose.linegraph.util.Radius
import com.joshrose.plotsforcompose.linegraph.util.Style

// TODO: How will end user supply their custom labels? - They won't?
// TODO: clean up label
// TODO: If I add Z Axis, labels will need to be drawn differently
// TODO: Draw bright line on Label's x coord (and y maybe?)
    // TODO: Line width and color
internal fun DrawScope.drawPointerLabels(
    label: Pair<Any?, Any?>,
    coordinates: List<Pair<Float, Float>>,
    coordinateIndex: Int,
    configs: LineGraphConfiguration,
    dataLabelMeasurer: TextMeasurer,
    statKind: StatKind
) {
    val labelString = buildAnnotatedString {
        withStyle(style = SpanStyle(color = configs.labelFontColor, fontSize = configs.labelFontSize)) {
            append(label.first.toString())
            append("\n")
            append(if (statKind == COUNT) label.second.toString().toInt().toString() else label.second.toString())
        }
    }

    val textLayoutResult = dataLabelMeasurer.measure(
        text = labelString,
        style = TextStyle(textAlign = TextAlign.Center),
        overflow = TextOverflow.Visible,
        softWrap = false
    )

    val labelHeight = textLayoutResult.size.height
    val labelWidth = textLayoutResult.size.width
    val boxSize = Size(width = labelWidth.plus(labelWidth.div(3f )), height = labelHeight.plus(labelHeight.div(3f)))
    val labelOffsetFromBox = Offset(x = labelWidth.div(3f).div(2f), y = labelHeight.div(3f).div(2f))
    val currentCoordinate = coordinates.elementAt(coordinateIndex)

    drawLabelRect(
        configs = configs,
        currentCoordinate = currentCoordinate,
        boxSize = boxSize,
        labelOffset = labelOffsetFromBox,
        textLayoutResult = textLayoutResult
    )

    val radiusWithMarkers = when (configs.labelMarkerRadius) {
        is Radius.Auto -> if (configs.markers) configs.markerSize?.toPx()?.plus(2f) ?: 5f else 10f
        is Radius.WithMarkers -> configs.labelMarkerRadius.size
        is Radius.WithoutMarkers -> configs.labelMarkerRadius.size
    }
    val strokeWidthWithMarkers = radiusWithMarkers.minus(configs.markerSize?.toPx() ?: 3f)
    val circleStyle = when (configs.labelMarkerStyle) {
        is Style.Auto -> if (!configs.markers) Fill else Stroke(width = strokeWidthWithMarkers)
        is Style.Fill -> Fill
        is Style.Stroke -> Stroke(width = radiusWithMarkers.minus(configs.labelMarkerStyle.size))
    }
    drawCircle(
        color = configs.labelMarkerColor,
        radius = radiusWithMarkers,
        center = Offset(x = currentCoordinate.first, y = currentCoordinate.second),
        style = circleStyle
    )
}

internal expect fun DrawScope.drawLabelRect(
    configs: LineGraphConfiguration,
    currentCoordinate: Pair<Float, Float>,
    boxSize: Size,
    labelOffset: Offset,
    textLayoutResult: TextLayoutResult
)