@file:OptIn(ExperimentalTextApi::class)
@file:Suppress("DuplicatedCode")

package com.joshrose.plotsforcompose.util

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.center
import androidx.compose.ui.unit.dp
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
    val labelString = AnnotatedString(label.formatToString(labelFormat))
    val labelDimensions = textMeasurer.measure(
        text = labelString,
        style = labelConfig.textStyle.copy(color = labelConfig.fontColor),
        overflow = TextOverflow.Visible,
        softWrap = false
    )

    // Centers the label on the x-axis. xOffset is already applied when xPositions is called.
    val xAdjusted = if (maxXValue < 0) x else x.minus(labelDimensions.size.width.div(2f))
    val yAdjusted = y.minus(labelDimensions.size.height.div(2f))

    val yPivot =
        if (labelConfig.rotation.value.mod(90f) == 0f) yAdjusted.plus(labelDimensions.size.center.y) else y
    val xPivot = when {
        labelConfig.rotation.value.mod(90f) == 0f -> xAdjusted.plus(labelDimensions.size.center.x)
        else -> if (maxXValue < 0) xAdjusted else xAdjusted.plus(labelDimensions.size.width)
    }

    rotate(degrees = labelConfig.rotation.value, pivot = Offset(x = xPivot, y = yPivot)) {
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
    label: Float,
    maxYValue: Float,
    textMeasurer: TextMeasurer,
    labelConfig: ContinuousLabelsConfig,
    labelFormat: String = "#.##"
) {
    val labelString = AnnotatedString(label.formatToString(labelFormat))
    val labelDimensions = textMeasurer.measure(
        text = labelString,
        style = labelConfig.textStyle.copy(color = labelConfig.fontColor),
        overflow = TextOverflow.Visible,
        softWrap = false
    )

    val yAdjusted = y.minus(labelDimensions.size.height.div(2f))
    val xAdjusted = when {
            labelConfig.rotation == 0.dp -> x.minus(labelDimensions.size.width.div(2f))
            labelConfig.rotation < 0.dp -> x.minus(labelDimensions.size.width)
            else -> x
        }
    val xPivot = if (labelConfig.rotation < 0.dp) xAdjusted.plus(labelDimensions.size.width) else xAdjusted

    val degrees = labelConfig.rotation.value.times(if (maxYValue < 0) -1 else 1)
    rotate(degrees = degrees, pivot = Offset(x = xPivot, y = y)) {
        drawText(
            textLayoutResult = labelDimensions,
            topLeft = Offset(x = xAdjusted, y = yAdjusted)
        )
    }
}

fun Float.formatToString(pattern: String): String = DecimalFormat(pattern).format(this).toString()