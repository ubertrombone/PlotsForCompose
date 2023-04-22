package com.joshrose.plotsforcompose.axis.x.util

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.*
import androidx.compose.ui.text.style.TextOverflow
import com.joshrose.plotsforcompose.axis.config.axisline.AxisLineConfig
import com.joshrose.plotsforcompose.axis.config.guidelines.GuidelinesConfig
import com.joshrose.plotsforcompose.axis.config.labels.ContinuousLabelsConfig
import com.joshrose.plotsforcompose.axis.util.formatToString

fun DrawScope.drawXGuideline(
    guidelineConfig: GuidelinesConfig,
    x: Float,
    yPosition: Float
) {
    val lineLength = size.height.minus(guidelineConfig.padding.toPx())
    val startY = if (yPosition == 0f) guidelineConfig.padding.toPx() else 0f
    val endY = if (yPosition == size.height.div(2)) size.height else startY.plus(lineLength)

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
    yPosition: Float,
    yOffset: Float
) {
    val tickStart = when (yPosition) {
        size.height -> yPosition
        size.width.div(2f) -> yPosition.minus(yOffset.div(4f))
        else -> yPosition.minus(yOffset.div(2f))
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
    yPosition: Float
) {
    drawLine(
        start = Offset(x = 0f, y = yPosition),
        end = Offset(x = size.width, y = yPosition),
        color = axisLineConfig.lineColor,
        alpha = axisLineConfig.alpha.factor,
        pathEffect = axisLineConfig.pathEffect,
        strokeWidth = axisLineConfig.strokeWidth.toPx()
    )
}

@OptIn(ExperimentalTextApi::class)
fun DrawScope.drawXFloatLabel(
    y: Float,
    x: Float,
    label: Float,
    maxYValue: Float,
    textMeasurer: TextMeasurer,
    labelConfig: ContinuousLabelsConfig,
    labelFormat: String = "#.##"
) {
    val labelDimensions = makeTextLayout(
        label = label, textMeasurer = textMeasurer, labelConfig = labelConfig, labelFormat = labelFormat
    )

    val (xAdjusted, yAdjusted, xPivot) = adjustCoordinates(
        x = x,
        y = y,
        rotation = labelConfig.rotation,
        yOffset = labelDimensions.size.height.toFloat(),
        xOffset = labelDimensions.size.width.toFloat()
    )

    val degrees = labelConfig.rotation.times(if (maxYValue <= 0) -1 else 1)
    rotate(degrees = degrees, pivot = Offset(x = xPivot, y = y)) {
        drawText(
            textLayoutResult = labelDimensions,
            topLeft = Offset(x = xAdjusted, y = yAdjusted)
        )
    }
}

@OptIn(ExperimentalTextApi::class)
fun DrawScope.drawXFloatLabel(
    y: Float,
    x: Float,
    axisPos: XAxisPos,
    label: Float,
    textMeasurer: TextMeasurer,
    labelConfig: ContinuousLabelsConfig,
    labelFormat: String = "#.##"
) {
    val labelDimensions = makeTextLayout(
        label = label, textMeasurer = textMeasurer, labelConfig = labelConfig, labelFormat = labelFormat
    )

    val (xAdjusted, yAdjusted, xPivot) = adjustCoordinates(
        x = x,
        y = y,
        rotation = labelConfig.rotation,
        yOffset = labelDimensions.size.height.toFloat(),
        xOffset = labelDimensions.size.width.toFloat()
    )

    val degrees = labelConfig.rotation.times(if (axisPos == XAxisPos.TOP) -1 else 1)
    rotate(degrees = degrees, pivot = Offset(x = xPivot, y = y)) {
        drawText(
            textLayoutResult = labelDimensions,
            topLeft = Offset(x = xAdjusted, y = yAdjusted)
        )
    }
}

@OptIn(ExperimentalTextApi::class)
fun makeTextLayout(
    label: Float,
    textMeasurer: TextMeasurer,
    labelConfig: ContinuousLabelsConfig,
    labelFormat: String = "#.##"
): TextLayoutResult {
    val labelString = AnnotatedString(label.formatToString(labelFormat))
    return textMeasurer.measure(
        text = labelString,
        style = labelConfig.textStyle.copy(color = labelConfig.fontColor),
        overflow = TextOverflow.Visible,
        softWrap = false
    )
}

fun adjustCoordinates(
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