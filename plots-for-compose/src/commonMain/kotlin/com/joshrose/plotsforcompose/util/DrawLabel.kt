@file:OptIn(ExperimentalTextApi::class)
@file:Suppress("DuplicatedCode")

package com.joshrose.plotsforcompose.util

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.joshrose.plotsforcompose.axis.config.labels.ContinuousLabelsConfig
import java.text.DecimalFormat
import kotlin.math.cos
import kotlin.math.sin

val colorList: List<Color> = listOf(Color.Black,
    Color.Blue, Color.Yellow, Color.Red, Color.Green, Color.Magenta)
@OptIn(ExperimentalTextApi::class)
fun DrawScope.drawYFloatLabel(
    y: Float,
    x: Float,
    label: Float,
    maxXValue: Float,
    textMeasurer: TextMeasurer,
    labelConfig: ContinuousLabelsConfig
) {
    val labelString = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = labelConfig.fontColor,
                fontSize = labelConfig.textStyle.fontSize,
                fontWeight = labelConfig.textStyle.fontWeight,
                fontStyle = labelConfig.textStyle.fontStyle,
                fontSynthesis = labelConfig.textStyle.fontSynthesis,
                fontFamily = labelConfig.textStyle.fontFamily,
                fontFeatureSettings = labelConfig.textStyle.fontFeatureSettings,
                letterSpacing = labelConfig.textStyle.letterSpacing,
                baselineShift = labelConfig.textStyle.baselineShift,
                textGeometricTransform = labelConfig.textStyle.textGeometricTransform,
                localeList = labelConfig.textStyle.localeList,
                background = labelConfig.textStyle.background,
                textDecoration = labelConfig.textStyle.textDecoration,
                shadow = labelConfig.textStyle.shadow
            )
        ) {
            val pattern = if (label == 0f) "#" else "#.##"
            append(DecimalFormat(pattern).format(label))
        }
    }

    val labelDimensions = textMeasurer.measure(labelString)

    // TODO: This DrawText calcs differently, adjust below:
    val rotationRadians = labelConfig.rotation.abs().value.toRadians()
    val offsetFromLabelCenter = sin(rotationRadians).times(labelDimensions.size.width.div(2f))
    val offsetBetweenLabelHeightCenterAndRotatedCorner = cos(rotationRadians).times(labelDimensions.size.height.div(2f))
    val offsetBetweenRotatedCornerAndAxisTick = offsetFromLabelCenter.minus(offsetBetweenLabelHeightCenterAndRotatedCorner)

    val yRotated =
        if (labelConfig.rotation == 0.dp) y.plus(labelDimensions.size.height.div(2f)).plus(labelConfig.yOffset.toPx())
        else y.plus(labelDimensions.size.height).plus(offsetBetweenRotatedCornerAndAxisTick).plus(labelConfig.yOffset.toPx())

    val degrees = if (maxXValue < 0) labelConfig.rotation.abs().value else labelConfig.rotation.value
    rotate(degrees = degrees, pivot = Offset(x = x, y = yRotated)) {
        drawRect(
            brush = Brush.horizontalGradient(colors = colorList),
            size = labelDimensions.size.toSize(),
            topLeft = Offset(x = x.minus(labelDimensions.size.width.div(2f)), y = yRotated)
        )
        drawText(
            textMeasurer = textMeasurer,
            text = labelString,
            topLeft = Offset(x = x.minus(labelDimensions.size.width.div(2f)), y = yRotated)
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
    labelConfig: ContinuousLabelsConfig
) {
    val labelString = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = labelConfig.fontColor,
                fontSize = labelConfig.textStyle.fontSize,
                fontWeight = labelConfig.textStyle.fontWeight,
                fontStyle = labelConfig.textStyle.fontStyle,
                fontSynthesis = labelConfig.textStyle.fontSynthesis,
                fontFamily = labelConfig.textStyle.fontFamily,
                fontFeatureSettings = labelConfig.textStyle.fontFeatureSettings,
                letterSpacing = labelConfig.textStyle.letterSpacing,
                baselineShift = labelConfig.textStyle.baselineShift,
                textGeometricTransform = labelConfig.textStyle.textGeometricTransform,
                localeList = labelConfig.textStyle.localeList,
                background = labelConfig.textStyle.background,
                textDecoration = labelConfig.textStyle.textDecoration,
                shadow = labelConfig.textStyle.shadow
            )
        ) {
            val pattern = if (label == 0f) "#" else "#.##"
            append(DecimalFormat(pattern).format(label))
        }
    }

    val labelDimensions = textMeasurer.measure(labelString)

    val rotationRadians = 90f.minus(labelConfig.rotation.toPx()).toRadians()
    val offsetFromLabelCenter = sin(rotationRadians).times(labelDimensions.size.width.div(2f))
    val offsetBetweenLabelHeightCenterAndRotatedCorner = cos(rotationRadians).times(labelDimensions.size.height.div(2f))
    val offsetBetweenRotatedCornerAndAxisTick = offsetFromLabelCenter.minus(offsetBetweenLabelHeightCenterAndRotatedCorner)

    val xRotated =
        if (labelConfig.rotation.toPx() == 0f) x.plus(labelConfig.xOffset.toPx())
        else x.plus(labelConfig.xOffset.toPx()).plus(offsetBetweenRotatedCornerAndAxisTick)

    val degrees = if (maxYValue < 0) labelConfig.rotation.abs().value else labelConfig.rotation.value
    rotate(degrees = degrees, pivot = Offset(x = xRotated, y = y)) {
        drawRect(
            brush = Brush.horizontalGradient(colors = colorList),
            size = labelDimensions.size.toSize(),
            topLeft = Offset(x = xRotated, y = y)
        )
        drawText(
            textMeasurer = textMeasurer,
            text = labelString,
            topLeft = Offset(x = xRotated, y = y)
        )
    }
}