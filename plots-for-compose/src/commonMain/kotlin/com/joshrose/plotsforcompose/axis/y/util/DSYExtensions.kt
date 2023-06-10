package com.joshrose.plotsforcompose.axis.y.util

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.drawText
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.center
import com.joshrose.plotsforcompose.axis.config.axisline.AxisLineConfiguration
import com.joshrose.plotsforcompose.axis.config.guidelines.GuidelinesConfiguration
import com.joshrose.plotsforcompose.axis.config.labels.LabelsConfiguration
import com.joshrose.plotsforcompose.axis.util.AxisPosition.*
import com.joshrose.plotsforcompose.axis.util.makeTextLayout

internal fun DrawScope.drawYGuideline(
    guidelineConfig: GuidelinesConfiguration,
    y: Float,
    yAxisPosition: YAxis
) {
    val lineLength = size.width.minus(guidelineConfig.padding)
    val startX = if (yAxisPosition == Start) guidelineConfig.padding else 0f
    val endX = if (yAxisPosition == Center) size.width else startX.plus(lineLength)

    drawLine(
        start = Offset(x = startX, y = y),
        end = Offset(x = endX, y = y),
        color = guidelineConfig.lineColor,
        alpha = guidelineConfig.alpha.factor,
        pathEffect = guidelineConfig.pathEffect,
        strokeWidth = guidelineConfig.strokeWidth
    )
}

@Throws(IllegalStateException::class)
internal fun DrawScope.drawYTick(
    axisLineConfig: AxisLineConfiguration.Configuration,
    y: Float,
    yAxisPosition: YAxis,
    axisOffset: Float
) {
    val tickStart = when (yAxisPosition) {
        Start -> 0f.minus(axisOffset.div(2f))
        End -> size.width
        Center -> size.width.div(2f).minus(axisOffset.div(2f))
        else -> throw IllegalStateException("yAxisPosition must be of type AxisPosition.YAxis. Current state: $yAxisPosition")
    }
    val tickEnd =
        if (yAxisPosition == Center) tickStart.plus(axisOffset)
        else tickStart.plus(axisOffset.div(2f))

    drawLine(
        start = Offset(x = tickStart, y = y),
        end = Offset(x = tickEnd, y = y),
        color = axisLineConfig.lineColor,
        alpha = axisLineConfig.alpha.factor,
        strokeWidth = axisLineConfig.strokeWidth
    )
}

@Throws(IllegalStateException::class)
internal fun DrawScope.drawYAxis(
    axisLineConfig: AxisLineConfiguration.Configuration,
    yAxisPosition: YAxis
) {
    val x = when (yAxisPosition) {
        Start -> 0f
        End -> size.width
        Center -> size.width.div(2f)
        else -> throw IllegalStateException("yAxisPosition must be of type AxisPosition.YAxis. Current state: $yAxisPosition")
    }

    drawLine(
        start = Offset(x = x, y = 0f),
        end = Offset(x = x, y = size.height),
        color = axisLineConfig.lineColor,
        alpha = axisLineConfig.alpha.factor,
        pathEffect = axisLineConfig.pathEffect,
        strokeWidth = axisLineConfig.strokeWidth
    )
}

@ExperimentalTextApi
internal fun DrawScope.drawYLabel(
    y: Float,
    x: Float,
    label: Any,
    yAxisPosition: YAxis,
    textMeasurer: TextMeasurer,
    labelConfig: LabelsConfiguration
) {
    val labelDimensions = if (label is Number) {
        makeTextLayout(
            label = label.toFloat(),
            textMeasurer = textMeasurer,
            labelConfig = labelConfig
        )
    } else {
        makeTextLayout(
            label = label.toString(),
            textMeasurer = textMeasurer,
            labelConfig = labelConfig
        )
    }



    val offsetX =
        if (yAxisPosition == End) x.plus(labelConfig.axisOffset.toPx())
        else x.minus(labelDimensions.size.width.div(2f)).minus(labelConfig.axisOffset.toPx())

    val (xAdjusted, yAdjusted) = adjustYLabelCoordinates(
        y = y,
        x = offsetX,//if (yAxisPosition == END) x.plus(labelConfig.xOffset.toPx()) else x.minus(labelConfig.xOffset.toPx()),
        yOffset = labelDimensions.size.height.toFloat(),
        xOffset = labelDimensions.size.width.toFloat(),
        yAxisPosition = yAxisPosition
    )

    val (xPivot, yPivot) = yLabelPivotCoordinates(
        y = y,
        yAdjusted = yAdjusted,
        xAdjusted = xAdjusted,
        yAxisPosition = yAxisPosition,
        rotation = labelConfig.rotation,
        labelWidth = labelDimensions.size.width.toFloat(),
        labelSize = labelDimensions.size.center
    )

    rotate(degrees = labelConfig.rotation, pivot = Offset(x = xPivot, y = yPivot)) {
        drawText(
            textLayoutResult = labelDimensions,
            topLeft = Offset(x = xAdjusted, y = yAdjusted)
        )
    }
}

fun adjustYLabelCoordinates(
    y: Float,
    x: Float,
    yOffset: Float,
    xOffset: Float,
    yAxisPosition: YAxis
): Pair<Float, Float> {
    val xAdjusted = if (yAxisPosition == End) x else x.minus(xOffset.div(2f))
    val yAdjusted = y.minus(yOffset.div(2f))
    return xAdjusted to yAdjusted
}

fun yLabelPivotCoordinates(
    y: Float,
    yAdjusted: Float,
    xAdjusted: Float,
    yAxisPosition: YAxis,
    rotation: Float,
    labelWidth: Float,
    labelSize: IntOffset
): Pair<Float, Float> {
    val yPivot =
        if (rotation.mod(90f) == 0f) yAdjusted.plus(labelSize.y) else y
    val xPivot = when {
        rotation.mod(90f) == 0f -> xAdjusted.plus(labelSize.x)
        else -> if (yAxisPosition == End) xAdjusted else xAdjusted.plus(labelWidth)
    }
    return xPivot to yPivot
}