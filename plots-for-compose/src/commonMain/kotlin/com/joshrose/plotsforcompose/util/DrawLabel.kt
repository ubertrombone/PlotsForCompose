@file:OptIn(ExperimentalTextApi::class)
@file:Suppress("DuplicatedCode")

package com.joshrose.plotsforcompose.util

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.*
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.center
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.joshrose.plotsforcompose.axis.config.labels.ContinuousLabelsConfig
import java.text.DecimalFormat

@OptIn(ExperimentalTextApi::class)
fun DrawScope.drawYFloatLabel(
    y: Float,
    x: Float,
    label: Float,
    maxXValue: Float,
    textMeasurer: TextMeasurer,
    labelConfig: ContinuousLabelsConfig,
    labelFormat: String = "#.##"
) {
    val labelString = buildAnnotatedString {
        withStyle(style = SpanStyle(color = labelConfig.fontColor)) {
            append(label.formatToString(labelFormat))
        }
    }

    val labelDimensions = textMeasurer.measure(labelString)

    // Centers the label on the x-axis. xOffset is already applied when xPositions is called.
    val xAdjusted = x.minus(labelDimensions.size.width.div(2f))

    val yAdjusted = when {
            labelConfig.rotation == 0.dp -> y.minus(labelDimensions.size.center.y)
            labelConfig.rotation.value.mod(90f) == 0f -> y.minus(labelDimensions.size.width.div(2f))
            else -> y.minus(labelDimensions.size.center.y)
        }

    val yPivot = when {
        labelConfig.rotation == 90.dp -> yAdjusted.plus(labelDimensions.size.height)
        labelConfig.rotation > 0.dp -> yAdjusted
        else -> yAdjusted.plus(labelDimensions.size.height)
    }

    // TODO: Make sure calcs work when axis is on other side and for X
    val degrees = if (maxXValue < 0) labelConfig.rotation.abs().value else labelConfig.rotation.value
    rotate(degrees = degrees, pivot = Offset(x = x, y = yPivot)) {
        drawText(
            textMeasurer = textMeasurer,
            text = labelString,
            style = labelConfig.textStyle,
            topLeft = Offset(x = xAdjusted, y = yAdjusted),
            overflow = TextOverflow.Visible,
            softWrap = false,
            size = labelDimensions.size.toSize()
        )
    }
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
    val labelString = buildAnnotatedString {
        withStyle(style = SpanStyle(color = labelConfig.fontColor)) {
            append(label.formatToString(labelFormat))
        }
    }

    val labelDimensions = textMeasurer.measure(labelString)

    val yAdjusted = y.minus(labelDimensions.size.height.div(2f))

    val xAdjusted = when {
            labelConfig.rotation == 0.dp -> x.minus(labelDimensions.size.width.div(2f))
            labelConfig.rotation < 0.dp -> x.minus(labelDimensions.size.width)
            else -> x
        }

    val xPivot = if (labelConfig.rotation < 0.dp) xAdjusted.plus(labelDimensions.size.width) else xAdjusted

    val degrees = if (maxYValue < 0) -(labelConfig.rotation.value) else labelConfig.rotation.value
    rotate(degrees = degrees, pivot = Offset(x = xPivot, y = y)) {
        drawText(
            textMeasurer = textMeasurer,
            text = labelString,
            style = labelConfig.textStyle,
            topLeft = Offset(x = xAdjusted, y = yAdjusted),
            overflow = TextOverflow.Visible,
            softWrap = false,
            size = labelDimensions.size.toSize()
        )
    }
}

fun Float.formatToString(pattern: String): String = DecimalFormat(pattern).format(this).toString()