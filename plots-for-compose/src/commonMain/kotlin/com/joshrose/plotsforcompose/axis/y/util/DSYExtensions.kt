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
    yAxisPosition: AxisPosition
) {
    val lineLength = size.width.minus(guidelineConfig.padding.toPx())
    val startX = if (yAxisPosition == TOP_START) guidelineConfig.padding.toPx() else 0f
    val endX = if (yAxisPosition == CENTER) size.width else startX.plus(lineLength)

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
    yAxisPosition: AxisPosition,
    xOffset: Float
) {
    val tickStart = when (yAxisPosition) {
        TOP_START -> 0f.minus(xOffset.div(2f))
        BOTTOM_END -> size.width
        CENTER -> size.width.div(2f).minus(xOffset.div(4f))
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
    yAxisPosition: AxisPosition
) {
    val x = when (yAxisPosition) {
        TOP_START -> 0f
        BOTTOM_END -> size.width
        CENTER -> size.width.div(2f)
    }

    drawLine(
        start = Offset(x = x, y = 0f),
        end = Offset(x = x, y = size.height),
        color = axisLineConfig.lineColor,
        alpha = axisLineConfig.alpha.factor,
        pathEffect = axisLineConfig.pathEffect,
        strokeWidth = axisLineConfig.strokeWidth.toPx()
    )
}

fun DrawScope.drawYFloatLabel(
    y: Float,
    x: Float,
    label: Float,
    yAxisPosition: AxisPosition,
    textMeasurer: TextMeasurer,
    labelConfig: ContinuousLabelsConfig
) {
    val labelDimensions = makeTextLayout(
        label = label,
        textMeasurer = textMeasurer,
        labelConfig = labelConfig
    )

    val (xAdjusted, yAdjusted) = adjustYLabelCoordinates(
        y = y,
        x = if (yAxisPosition == BOTTOM_END) x.plus(labelConfig.xOffset.toPx()) else x.minus(labelConfig.xOffset.toPx()),
        yOffset = labelDimensions.size.height.toFloat(),
        xOffset = labelDimensions.size.width.toFloat(),
        labelPos = yAxisPosition
    )

    val (xPivot, yPivot) = yLabelPivotCoordinates(
        y = y,
        yAdjusted = yAdjusted,
        xAdjusted = xAdjusted,
        labelPos = yAxisPosition,
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