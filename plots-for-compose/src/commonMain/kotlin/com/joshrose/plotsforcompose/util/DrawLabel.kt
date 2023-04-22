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
import com.joshrose.plotsforcompose.axis.config.labels.ContinuousLabelsConfig
import com.joshrose.plotsforcompose.axis.util.formatToString

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
    val xAdjusted = if (maxXValue <= 0) x else x.minus(labelDimensions.size.width.div(2f))
    val yAdjusted = y.minus(labelDimensions.size.height.div(2f))

    val yPivot =
        if (labelConfig.rotation.mod(90f) == 0f) yAdjusted.plus(labelDimensions.size.center.y) else y
    val xPivot = when {
        labelConfig.rotation.mod(90f) == 0f -> xAdjusted.plus(labelDimensions.size.center.x)
        else -> if (maxXValue <= 0) xAdjusted else xAdjusted.plus(labelDimensions.size.width)
    }

    rotate(degrees = labelConfig.rotation, pivot = Offset(x = xPivot, y = yPivot)) {
        drawText(
            textLayoutResult = labelDimensions,
            topLeft = Offset(x = xAdjusted, y = yAdjusted)
        )
    }
}