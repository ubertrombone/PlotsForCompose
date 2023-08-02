package com.joshrose.plotsforcompose.internals.plots.lines

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.joshrose.plotsforcompose.linegraph.config.LineGraphConfiguration

// TODO: Add this to LineConfig class
// TODO: How will end user supply their custom labels? - They won't?
// TODO: If I will force the labels, then add StatKind to know how to format Int vs. Float
// TODO: If I add Z Axis, labels will need to be drawn differently
// TODO: Draw bright line on Label's x coord (and y maybe?)
internal fun DrawScope.drawPointerLabels(
    label: Pair<Any?, Any?>,
    coordinates: List<Pair<Float, Float>>,
    coordinateIndex: Int,
    configs: LineGraphConfiguration,
    dataLabelMeasurer: TextMeasurer,
    //statKind: StatKind
) {
    // TODO: Include labels or no
    // TODO: Font Color
    // TODO: Font Size
    val labelString = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Color.Red, fontSize = 20.sp)) {
            append(label.first.toString())
            append("\n")
            append(label.second.toString())//if (statKind == COUNT) label.second.toString().toInt().toString() else label.second.toString())
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
        currentCoordinate = currentCoordinate,
        boxSize = boxSize,
        labelOffset = labelOffsetFromBox,
        textLayoutResult = textLayoutResult
    )

    // TODO: circle color
    // TODO: sealed interface Radius: auto, withMarkers(size: Float), withoutMarkers(size: Float)
    // TODO: sealed interface Style: auto, fill, stroke(width: Float)
    val radiusWithMarkers = if (configs.markers) configs.markerSize?.toPx()?.plus(2f) ?: 5f else 10f
    val strokeWidthWithMarkers = radiusWithMarkers.minus(configs.markerSize?.toPx() ?: 3f)
    drawCircle(
        color = Color.Red,
        radius = radiusWithMarkers,
        center = Offset(x = currentCoordinate.first, y = currentCoordinate.second),
        style = if (!configs.markers) Fill else Stroke(width = strokeWidthWithMarkers)
    )
}

internal expect fun DrawScope.drawLabelRect(
    currentCoordinate: Pair<Float, Float>,
    boxSize: Size,
    labelOffset: Offset,
    textLayoutResult: TextLayoutResult
)