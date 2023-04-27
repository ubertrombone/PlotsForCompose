@file:Suppress("DuplicatedCode")
@file:OptIn(ExperimentalTextApi::class)

package com.joshrose.plotsforcompose.axis.x.util

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.drawText
import com.joshrose.plotsforcompose.axis.config.axisline.AxisLineConfig
import com.joshrose.plotsforcompose.axis.config.guidelines.GuidelinesConfig
import com.joshrose.plotsforcompose.axis.config.labels.ContinuousLabelsConfig
import com.joshrose.plotsforcompose.axis.util.XAxisPosition
import com.joshrose.plotsforcompose.axis.util.XAxisPosition.*
import com.joshrose.plotsforcompose.axis.util.makeTextLayout

fun DrawScope.drawXGuideline(
    guidelineConfig: GuidelinesConfig,
    x: Float,
    xAxisPosition: XAxisPosition
) {
    val lineLength = size.height.minus(guidelineConfig.padding.toPx())
    val startY = if (xAxisPosition == TOP) guidelineConfig.padding.toPx() else 0f
    val endY = if (xAxisPosition == CENTER) size.height else startY.plus(lineLength)

    drawLine(
        start = Offset(x = x, y = startY),
        end = Offset(x = x, y = endY),
        color = guidelineConfig.lineColor,
        alpha = guidelineConfig.alpha.factor,
        pathEffect = guidelineConfig.pathEffect,
        strokeWidth = guidelineConfig.strokeWidth.toPx()
    )
}

fun DrawScope.drawXTick(
    axisLineConfig: AxisLineConfig,
    x: Float,
    xAxisPosition: XAxisPosition,
    yOffset: Float
) {
    val tickStart = when (xAxisPosition) {
        TOP -> 0f.minus(yOffset.div(2f))
        BOTTOM -> size.height
        CENTER -> size.height.div(2f).minus(yOffset.div(4f))
    }
    val tickEnd = tickStart.plus(yOffset.div(2f))

    drawLine(
        start = Offset(x = x, y = tickStart),
        end = Offset(x = x, y = tickEnd),
        color = axisLineConfig.lineColor,
        alpha = axisLineConfig.alpha.factor,
        strokeWidth = axisLineConfig.strokeWidth.toPx()
    )
}

fun DrawScope.drawXAxis(
    axisLineConfig: AxisLineConfig,
    xAxisPosition: XAxisPosition
) {
    val y = when (xAxisPosition) {
        TOP -> 0f
        BOTTOM -> size.height
        CENTER -> size.height.div(2f)
    }

    drawLine(
        start = Offset(x = 0f, y = y),
        end = Offset(x = size.width, y = y),
        color = axisLineConfig.lineColor,
        alpha = axisLineConfig.alpha.factor,
        pathEffect = axisLineConfig.pathEffect,
        strokeWidth = axisLineConfig.strokeWidth.toPx()
    )
}

fun DrawScope.drawXFloatLabel(
    y: Float,
    x: Float,
    xAxisPosition: XAxisPosition,
    label: Float,
    textMeasurer: TextMeasurer,
    labelConfig: ContinuousLabelsConfig
) {
    val labelDimensions = makeTextLayout(
        label = label,
        textMeasurer = textMeasurer,
        labelConfig = labelConfig
    )

    val offsetY = if (xAxisPosition == TOP) y.minus(labelConfig.yOffset.toPx()) else y.plus(labelConfig.yOffset.toPx())

    val (xAdjusted, yAdjusted, xPivot) = adjustXLabelCoordinates(
        x = x,
        y = offsetY,
        rotation = labelConfig.rotation,
        yOffset = labelDimensions.size.height.toFloat(),
        xOffset = labelDimensions.size.width.toFloat()
    )

    val degrees = labelConfig.rotation.times(if (xAxisPosition == TOP) -1 else 1)
    rotate(degrees = degrees, pivot = Offset(x = xPivot, y = offsetY)) {
        drawText(
            textLayoutResult = labelDimensions,
            topLeft = Offset(x = xAdjusted, y = yAdjusted)
        )
    }
}

fun adjustXLabelCoordinates(
    y: Float,
    x: Float,
    rotation: Float,
    yOffset: Float,
    xOffset: Float
): Triple<Float, Float, Float> {
    val yAdjusted = y.minus(yOffset.div(2f))
    val xAdjusted = when {
        rotation == 0f -> x.minus(xOffset.div(2f))
        rotation < 0f -> x.minus(xOffset)
        else -> x
    }
    val xPivot = if (rotation < 0f) xAdjusted.plus(xOffset) else xAdjusted
    return Triple(xAdjusted, yAdjusted, xPivot)
}