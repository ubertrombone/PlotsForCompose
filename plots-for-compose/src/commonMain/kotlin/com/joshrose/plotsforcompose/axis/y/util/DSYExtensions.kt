@file:Suppress("DuplicatedCode")
@file:OptIn(ExperimentalTextApi::class)

package com.joshrose.plotsforcompose.axis.y.util

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.drawText
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.center
import com.joshrose.plotsforcompose.axis.config.axisline.AxisLineConfig
import com.joshrose.plotsforcompose.axis.config.guidelines.GuidelinesConfig
import com.joshrose.plotsforcompose.axis.config.labels.ContinuousLabelsConfig
import com.joshrose.plotsforcompose.axis.util.AxisPosition
import com.joshrose.plotsforcompose.axis.util.AxisPosition.*
import com.joshrose.plotsforcompose.axis.util.makeTextLayout

fun DrawScope.drawYGuideline(
    guidelineConfig: GuidelinesConfig,
    y: Float,
    xPosition: Float
) {
    val lineLength = size.width.minus(guidelineConfig.padding.toPx())
    val startX = if (xPosition == 0f) guidelineConfig.padding.toPx() else 0f
    val endX = if (xPosition == size.width.div(2)) size.width else startX.plus(lineLength)

    drawLine(
        start = Offset(x = startX, y = y),
        end = Offset(x = endX, y = y),
        color = guidelineConfig.lineColor,
        alpha = guidelineConfig.alpha.factor,
        pathEffect = guidelineConfig.pathEffect,
        strokeWidth = guidelineConfig.strokeWidth.toPx()
    )
}

fun DrawScope.drawYTick(
    axisLineConfig: AxisLineConfig,
    y: Float,
    xPosition: Float,
    xOffset: Float
) {
    val tickStart = when (xPosition) {
        size.width -> xPosition
        size.width.div(2f) -> xPosition.minus(xOffset.div(4f))
        else -> xPosition.minus(xOffset.div(2f))
    }
    val tickEnd = tickStart.plus(xOffset.div(2f))

    drawLine(
        start = Offset(x = tickStart, y = y),
        end = Offset(x = tickEnd, y = y),
        color = axisLineConfig.lineColor,
        alpha = axisLineConfig.alpha.factor,
        strokeWidth = axisLineConfig.strokeWidth.toPx()
    )
}

fun DrawScope.drawYAxis(
    axisLineConfig: AxisLineConfig,
    xPosition: Float
) {
    drawLine(
        start = Offset(x = xPosition, y = 0f),
        end = Offset(x = xPosition, y = size.height),
        color = axisLineConfig.lineColor,
        alpha = axisLineConfig.alpha.factor,
        pathEffect = axisLineConfig.pathEffect,
        strokeWidth = axisLineConfig.strokeWidth.toPx()
    )
}

@OptIn(ExperimentalTextApi::class)
fun DrawScope.drawYFloatLabel(
    y: Float,
    x: Float,
    label: Float,
    maxXValue: Float,
    textMeasurer: TextMeasurer,
    labelConfig: ContinuousLabelsConfig
) {
    val labelDimensions = makeTextLayout(
        label = label,
        textMeasurer = textMeasurer,
        labelConfig = labelConfig
    )

    // Centers the label on the x-axis. xOffset is already applied when xPositions is called.
    val (xAdjusted, yAdjusted) = adjustYLabelCoordinates(
        y = y,
        x = x,
        yOffset = labelDimensions.size.height.toFloat(),
        xOffset = labelDimensions.size.width.toFloat(),
        maxXValue = maxXValue
    )

    val (xPivot, yPivot) = yLabelPivotCoordinates(
        y = y,
        yAdjusted = yAdjusted,
        xAdjusted = xAdjusted,
        maxXValue = maxXValue,
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

fun DrawScope.drawYFloatLabel(
    y: Float,
    x: Float,
    label: Float,
    axisPos: AxisPosition,
    textMeasurer: TextMeasurer,
    labelConfig: ContinuousLabelsConfig
) {
    val labelDimensions = makeTextLayout(
        label = label,
        textMeasurer = textMeasurer,
        labelConfig = labelConfig
    )

    // Centers the label on the x-axis. xOffset is already applied when xPositions is called.
    val (xAdjusted, yAdjusted) = adjustYLabelCoordinates(
        y = y,
        x = x,
        yOffset = labelDimensions.size.height.toFloat(),
        xOffset = labelDimensions.size.width.toFloat(),
        labelPos = axisPos
    )

    val (xPivot, yPivot) = yLabelPivotCoordinates(
        y = y,
        yAdjusted = yAdjusted,
        xAdjusted = xAdjusted,
        labelPos = axisPos,
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
    maxXValue: Float
): Pair<Float, Float> {
    val xAdjusted = if (maxXValue <= 0) x else x.minus(xOffset.div(2f))
    val yAdjusted = y.minus(yOffset.div(2f))
    return xAdjusted to yAdjusted
}

fun adjustYLabelCoordinates(
    y: Float,
    x: Float,
    yOffset: Float,
    xOffset: Float,
    labelPos: AxisPosition
): Pair<Float, Float> {
    val xAdjusted = if (labelPos == BOTTOM_END) x else x.minus(xOffset.div(2f))
    val yAdjusted = y.minus(yOffset.div(2f))
    return xAdjusted to yAdjusted
}

fun yLabelPivotCoordinates(
    y: Float,
    yAdjusted: Float,
    xAdjusted: Float,
    maxXValue: Float,
    rotation: Float,
    labelWidth: Float,
    labelSize: IntOffset
): Pair<Float, Float> {
    val yPivot =
        if (rotation.mod(90f) == 0f) yAdjusted.plus(labelSize.y) else y
    val xPivot = when {
        rotation.mod(90f) == 0f -> xAdjusted.plus(labelSize.x)
        else -> if (maxXValue <= 0) xAdjusted else xAdjusted.plus(labelWidth)
    }
    return xPivot to yPivot
}

fun yLabelPivotCoordinates(
    y: Float,
    yAdjusted: Float,
    xAdjusted: Float,
    labelPos: AxisPosition,
    rotation: Float,
    labelWidth: Float,
    labelSize: IntOffset
): Pair<Float, Float> {
    val yPivot =
        if (rotation.mod(90f) == 0f) yAdjusted.plus(labelSize.y) else y
    val xPivot = when {
        rotation.mod(90f) == 0f -> xAdjusted.plus(labelSize.x)
        else -> if (labelPos == BOTTOM_END) xAdjusted else xAdjusted.plus(labelWidth)
    }
    return xPivot to yPivot
}