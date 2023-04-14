@file:OptIn(ExperimentalTextApi::class)

package com.joshrose.plotsforcompose.util

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.*
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import java.text.DecimalFormat
import kotlin.math.cos
import kotlin.math.sin

@OptIn(ExperimentalTextApi::class)
fun DrawScope.drawFloatLabel(
    y: Float,
    x: Float,
    label: Float,
    fontColor: Color,
    textStyle: TextStyle,
    textMeasurer: TextMeasurer,
    rotation: Dp,
    yOffset: Dp
) {
    val labelString = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = fontColor,
                fontSize = textStyle.fontSize,
                fontWeight = textStyle.fontWeight,
                fontStyle = textStyle.fontStyle,
                fontSynthesis = textStyle.fontSynthesis,
                fontFamily = textStyle.fontFamily,
                fontFeatureSettings = textStyle.fontFeatureSettings,
                letterSpacing = textStyle.letterSpacing,
                baselineShift = textStyle.baselineShift,
                textGeometricTransform = textStyle.textGeometricTransform,
                localeList = textStyle.localeList,
                background = textStyle.background,
                textDecoration = textStyle.textDecoration,
                shadow = textStyle.shadow
            )
        ) {
            val pattern = if (label == 0f) "#" else "#.##"
            append(DecimalFormat(pattern).format(label))
        }
    }

    val labelDimensions = textMeasurer.measure(labelString)

    val rotationRadians = rotation.abs().toRadians()
    val rotateOffset = sin(rotationRadians).times(labelDimensions.size.width.div(2f))
    val bottomCornerOffset = cos(rotationRadians).times(labelDimensions.size.height.div(2f))
    val centerBottomRightCorner = rotateOffset.minus(bottomCornerOffset)

    val yRotated =
        if (rotation == 0.dp) y.plus(labelDimensions.size.height).plus(yOffset.toPx())
        else y.plus(labelDimensions.size.height).plus(centerBottomRightCorner).plus(yOffset.toPx())

    rotate(degrees = rotation.value, pivot = Offset(x = x, y = yRotated)) {
        drawText(textMeasurer, labelString)
    }
}