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
import com.joshrose.plotsforcompose.axis.util.YAxisPosition
import com.joshrose.plotsforcompose.axis.util.YAxisPosition.*
import com.joshrose.plotsforcompose.axis.util.makeTextLayout

fun DrawScope.drawYGuideline(
    guidelineConfig: GuidelinesConfig,
    y: Float,
    yAxisPosition: YAxisPosition
) {
    val lineLength = size.width.minus(guidelineConfig.padding.toPx())
    val startX = if (yAxisPosition == START) guidelineConfig.padding.toPx() else 0f
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
    yAxisPosition: YAxisPosition
) {
    val tickStart = when (yAxisPosition) {
        START -> 0f.minus(axisLineConfig.ticks.value.div(2f))
        END -> size.width
        CENTER -> size.width.div(2f).minus(axisLineConfig.ticks.value.div(4f))
    }
    val tickEnd = tickStart.plus(axisLineConfig.ticks.value.div(2f))

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
    yAxisPosition: YAxisPosition
) {
    val x = when (yAxisPosition) {
        START -> 0f
        END -> size.width
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
    yAxisPosition: YAxisPosition,
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
        x = if (yAxisPosition == END) x.plus(labelConfig.xOffset.toPx()) else x.minus(labelConfig.xOffset.toPx()),
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
    yAxisPosition: YAxisPosition
): Pair<Float, Float> {
    val xAdjusted = if (yAxisPosition == END) x else x.minus(xOffset.div(2f))
    val yAdjusted = y.minus(yOffset.div(2f))
    return xAdjusted to yAdjusted
}

fun yLabelPivotCoordinates(
    y: Float,
    yAdjusted: Float,
    xAdjusted: Float,
    yAxisPosition: YAxisPosition,
    rotation: Float,
    labelWidth: Float,
    labelSize: IntOffset
): Pair<Float, Float> {
    val yPivot =
        if (rotation.mod(90f) == 0f) yAdjusted.plus(labelSize.y) else y
    val xPivot = when {
        rotation.mod(90f) == 0f -> xAdjusted.plus(labelSize.x)
        else -> if (yAxisPosition == END) xAdjusted else xAdjusted.plus(labelWidth)
    }
    return xPivot to yPivot
}