package com.joshrose.plotsforcompose.axis.continuous

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import com.joshrose.plotsforcompose.axis.config.ContinuousAxisConfig
import com.joshrose.plotsforcompose.axis.config.ContinuousAxisConfigDefaults
import com.joshrose.plotsforcompose.util.drawFloatLabel

@OptIn(ExperimentalTextApi::class)
fun DrawScope.continuousYAxis(
    config: ContinuousAxisConfig = ContinuousAxisConfigDefaults.continuousAxisConfigDefaults(),
    maxValue: Float,
    minValue: Float,
    xMinValue: Float,
    range: Float,
    textMeasurer: TextMeasurer,
) {
    val adjustedRange = range.plus(range.times(config.labels.rangeAdjustment.factor))
    val breaks = config.labels.breaks
    // if finalMinValue is less than 0 and an even number, increase breaks by 1 to ensure the center break is 0
    val centeredBreaks = if (breaks.mod(2) == 0) breaks.plus(1) else breaks
    val steps =
        if (minValue < 0) maxValue.div(breaks.plus(if (breaks.mod(2) == 0) 1 else 0).div(2))
        else maxValue.minus(minValue).div(breaks.minus(1))
    val labels =
        if (minValue < 0) (0..centeredBreaks.minus(1)).map { minValue.plus(it.times(steps)) }.reversed()
        else (0..breaks).map { minValue.plus(it.times(steps)) }.reversed()
    val x =
        if (xMinValue < 0) size.width.div(2f).minus(config.labels.xOffset.toPx())
        else 0f.minus(config.labels.xOffset.toPx())

    labels.forEach { label ->
        /*
        * currentDiff - gets the distance between maxValue and the current label
        * rangeDiff - gets the difference between the range and currentDiff
        * y - calculates the proportion of the range that rangeDiff occupies and then scales that
        * difference to the DrawScope's height. For the y-axis, we then have to subtract that value from the height.
        */
        val currentDiff = maxValue.minus(label)
        val rangeDiff = adjustedRange.minus(currentDiff)
        val y = size.height.minus(rangeDiff.div(range).times(size.height))

        drawFloatLabel(
            y = y,
            x = x,
            label = label,
            fontColor = config.labels.fontColor,
            textStyle = config.labels.textStyle,
            textMeasurer = textMeasurer,
            rotation = config.labels.rotation,
            yOffset = config.labels.yOffset,
        )

        drawLine(
            start = Offset(x = 0f.plus(config.guidelines.padding.toPx()), y = y),
            end = Offset(x = size.width, y = y),
            color = config.guidelines.lineColor,
            pathEffect = config.guidelines.pathEffect,
            alpha = config.guidelines.alpha.factor,
            strokeWidth = config.guidelines.strokeWidth.toPx()
        )
    }

    // TODO: Draw actual axis line?
    // TODO: Once tested, add axis line color as a config option
    // TODO: Once test, add strokeWidth as a config option
    // TODO: End Y Point -- Should I use the rangeDiff formula to find a proper endpoint for this?
    drawLine(
        start = Offset(x = x, y = 0f),
        end = Offset(x = x, y = size.height),
        color = Color.Black,
        strokeWidth = 3f
    )
}