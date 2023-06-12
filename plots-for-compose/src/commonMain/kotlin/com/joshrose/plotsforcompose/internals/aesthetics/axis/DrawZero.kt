package com.joshrose.plotsforcompose.internals.aesthetics.axis

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.drawText
import com.joshrose.plotsforcompose.axis.config.labels.LabelsConfiguration
import com.joshrose.plotsforcompose.axis.util.AxisPosition.*

@ExperimentalTextApi
internal fun DrawScope.drawZero(
    xAxisPosition: XAxis,
    yAxisPosition: YAxis,
    xAxisOffset: Float,
    yAxisOffset: Float,
    textMeasurer: TextMeasurer,
    labelConfig: LabelsConfiguration
) {
    val labelDimensions = makeTextLayout(
        label = 0f,
        textMeasurer = textMeasurer,
        labelConfig = labelConfig
    )

    val x = when (yAxisPosition) {
        End -> size.width
        Start -> 0f
        Center -> size.width.div(2f)
        else -> throw IllegalStateException("yAxisPosition must be of type AxisPosition.YAxis. Current state: $yAxisPosition")
    }
    val y = when (xAxisPosition) {
        Top -> 0f
        Bottom -> size.height
        Center -> size.height.div(2f)
        else -> throw IllegalStateException("xAxisPosition must be of type AxisPosition.XAxis. Current state: $xAxisPosition")
    }

    // Use yAxisOffset because this is the offset from the yAxis to the label.
    val xOffset =
        if (yAxisPosition == End) x.plus(yAxisOffset.div(2f))
        else x.minus(labelDimensions.size.width).minus(yAxisOffset.div(2f))

    val yOffset =
        if (xAxisPosition == Top) y.minus(labelDimensions.size.height).minus(xAxisOffset.div(2f))
        else y.plus(xAxisOffset.div(2f))

    drawText(
        textLayoutResult = labelDimensions,
        topLeft = Offset(x = xOffset, y = yOffset)
    )
}