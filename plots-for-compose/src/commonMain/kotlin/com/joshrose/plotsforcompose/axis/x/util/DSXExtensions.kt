package com.joshrose.plotsforcompose.axis.x.util

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.drawText
import com.joshrose.plotsforcompose.axis.config.axisline.AxisLineConfiguration
import com.joshrose.plotsforcompose.axis.config.guidelines.GuidelinesConfiguration
import com.joshrose.plotsforcompose.axis.config.labels.LabelsConfiguration
import com.joshrose.plotsforcompose.axis.util.AxisPosition.*
import com.joshrose.plotsforcompose.axis.util.makeTextLayout

internal fun DrawScope.drawXGuideline(
    guidelineConfig: GuidelinesConfiguration,
    x: Float,
    xAxisPosition: XAxis
) {
    val lineLength = size.height.minus(guidelineConfig.padding)
    val startY = when (xAxisPosition) {
        Top -> guidelineConfig.padding
        Both -> guidelineConfig.padding
        else -> 0f
    }
    val endY = when (xAxisPosition) {
        Center -> size.height
        Both -> startY.plus(lineLength).minus(guidelineConfig.padding)
        else -> startY.plus(lineLength)
    }

    drawLine(
        start = Offset(x = x, y = startY),
        end = Offset(x = x, y = endY),
        color = guidelineConfig.lineColor,
        alpha = guidelineConfig.alpha.factor,
        pathEffect = guidelineConfig.pathEffect,
        strokeWidth = guidelineConfig.strokeWidth
    )
}

@Throws(IllegalStateException::class)
internal fun DrawScope.drawXTick(
    axisLineConfig: AxisLineConfiguration.XConfiguration,
    x: Float,
    xAxisPosition: XAxis,
    axisOffset: Float
) {
    val tickStart = when (xAxisPosition) {
        Top -> 0f.minus(axisOffset.div(2f))
        Bottom -> size.height
        Both -> size.height
        Center -> size.height.div(2f).minus(axisOffset.div(2f))
        else -> throw IllegalStateException("xAxisPosition must be of type AxisPosition.XAxis. Current state: $xAxisPosition")
    }
    val tickEnd =
        if (xAxisPosition == Center) tickStart.plus(axisOffset)
        else tickStart.plus(axisOffset.div(2f))

    drawLine(
        start = Offset(x = x, y = tickStart),
        end = Offset(x = x, y = tickEnd),
        color = axisLineConfig.lineColor,
        alpha = axisLineConfig.alpha.factor,
        strokeWidth = axisLineConfig.strokeWidth
    )

    if (xAxisPosition == Both) {
        val secondStart = 0f.minus(axisOffset.div(2f))
        val secondEnd = secondStart.plus(axisOffset.div(2f))

        drawLine(
            start = Offset(x = x, y = secondStart),
            end = Offset(x = x, y = secondEnd),
            color = axisLineConfig.lineColor,
            alpha = axisLineConfig.alpha.factor,
            strokeWidth = axisLineConfig.strokeWidth
        )
    }
}

@Throws(IllegalStateException::class)
internal fun DrawScope.drawXAxis(
    axisLineConfig: AxisLineConfiguration.XConfiguration,
    xAxisPosition: XAxis
) {
    val y = when (xAxisPosition) {
        Top -> 0f
        Bottom -> size.height
        Both -> size.height
        Center -> size.height.div(2f)
        else -> throw IllegalStateException("xAxisPosition must be of type AxisPosition.XAxis. Current state: $xAxisPosition")
    }

    drawLine(
        start = Offset(x = 0f, y = y),
        end = Offset(x = size.width, y = y),
        color = axisLineConfig.lineColor,
        alpha = axisLineConfig.alpha.factor,
        pathEffect = axisLineConfig.pathEffect,
        strokeWidth = axisLineConfig.strokeWidth
    )

    if (xAxisPosition == Both) drawLine(
        start = Offset(x = 0f, y = 0f),
        end = Offset(x = size.width, y = 0f),
        color = axisLineConfig.lineColor,
        alpha = axisLineConfig.alpha.factor,
        pathEffect = axisLineConfig.pathEffect,
        strokeWidth = axisLineConfig.strokeWidth
    )
}

@ExperimentalTextApi
internal fun DrawScope.drawXLabel(
    y: Float,
    x: Float,
    xAxisPosition: XAxis,
    label: Any,
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

    val offsetY =
        if (xAxisPosition == Top) y.minus(labelDimensions.size.height.div(2f)).minus(labelConfig.axisOffset.toPx())
        else y.plus(labelDimensions.size.height.div(2f)).plus(labelConfig.axisOffset.toPx())

    val (xAdjusted, yAdjusted, xPivot) = adjustXLabelCoordinates(
        x = x,
        y = offsetY,
        rotation = labelConfig.rotation,
        yOffset = labelDimensions.size.height.toFloat(),
        xOffset = labelDimensions.size.width.toFloat()
    )

    val degrees = labelConfig.rotation.times(if (xAxisPosition == Top) -1 else 1)
    rotate(degrees = degrees, pivot = Offset(x = xPivot, y = offsetY)) {
        drawText(
            textLayoutResult = labelDimensions,
            topLeft = Offset(x = xAdjusted, y = yAdjusted)
        )
    }

    if (xAxisPosition == Both) {
        val secondOffsetY = 0f.minus(labelDimensions.size.height.div(2f)).minus(labelConfig.axisOffset.toPx())
        val (secondXAdjusted, secondYAdjusted, secondXPivot) = adjustXLabelCoordinates(
            x = x,
            y = secondOffsetY,
            rotation = labelConfig.rotation,
            yOffset = labelDimensions.size.height.toFloat(),
            xOffset = labelDimensions.size.width.toFloat()
        )

        val secondDegrees = labelConfig.rotation.times(-1)
        rotate(degrees = secondDegrees, pivot = Offset(x = secondXPivot, y = secondOffsetY)) {
            drawText(
                textLayoutResult = labelDimensions,
                topLeft = Offset(x = secondXAdjusted, y = secondYAdjusted)
            )
        }
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