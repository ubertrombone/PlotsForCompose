package com.joshrose.plotsforcompose.axis.util

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.drawText
import com.joshrose.plotsforcompose.axis.config.labels.ContinuousLabelsConfig
import com.joshrose.plotsforcompose.axis.util.AxisPosition.XAxisPosition
import com.joshrose.plotsforcompose.axis.util.AxisPosition.XAxisPosition.BOTTOM
import com.joshrose.plotsforcompose.axis.util.AxisPosition.XAxisPosition.TOP
import com.joshrose.plotsforcompose.axis.util.AxisPosition.YAxisPosition
import com.joshrose.plotsforcompose.axis.util.AxisPosition.YAxisPosition.*

@OptIn(ExperimentalTextApi::class)
fun DrawScope.drawZero(
    xAxisPosition: XAxisPosition,
    yAxisPosition: YAxisPosition,
    xAxisOffset: Float,
    yAxisOffset: Float,
    textMeasurer: TextMeasurer,
    labelConfig: ContinuousLabelsConfig
) {
    val labelDimensions = makeTextLayout(
        label = 0f,
        textMeasurer = textMeasurer,
        labelConfig = labelConfig
    )

    val x = when (yAxisPosition) {
        END -> size.width
        START -> 0f
        CENTER -> size.width.div(2f)
    }
    val y = when (xAxisPosition) {
        TOP -> 0f
        BOTTOM -> size.height
        XAxisPosition.CENTER -> size.height.div(2f)
    }

    // Use yAxisOffset because this is the offset from the yAxis to the label.
    val xOffset =
        if (yAxisPosition == END) x.plus(yAxisOffset.div(2f))
        else x.minus(labelDimensions.size.width).minus(yAxisOffset.div(2f))

    val yOffset =
        if (xAxisPosition == TOP) y.minus(labelDimensions.size.height).minus(xAxisOffset.div(2f))
        else y.plus(xAxisOffset.div(2f))

    drawText(
        textLayoutResult = labelDimensions,
        topLeft = Offset(x = xOffset, y = yOffset)
    )
}