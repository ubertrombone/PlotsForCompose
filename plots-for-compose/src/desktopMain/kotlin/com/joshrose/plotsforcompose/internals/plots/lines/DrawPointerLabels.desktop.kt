package com.joshrose.plotsforcompose.internals.plots.lines

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.drawText
import com.joshrose.plotsforcompose.linegraph.config.LineGraphConfiguration

internal actual fun DrawScope.drawLabelRect(
    configs: LineGraphConfiguration,
    currentCoordinate: Pair<Float, Float>,
    boxSize: Size,
    labelOffset: Offset,
    textLayoutResult: TextLayoutResult
) {
    val topYOfBox =
        if ((0f..size.height.div(2f)).contains(currentCoordinate.second))
            size.height.minus(boxSize.height)
        else 0f

    val startXOfBox = when (currentCoordinate.first) {
        0f -> 0f
        size.width -> size.width.minus(boxSize.width)
        else -> currentCoordinate.first.minus(boxSize.width.div(2f))
    }

    val topYOfText = topYOfBox.plus(labelOffset.y)
    val startXOfText = startXOfBox.plus(labelOffset.x)

    drawRoundRect(
        color = configs.boxColor,
        topLeft = Offset(x = startXOfBox, y = topYOfBox),
        size = Size(width = boxSize.width, height = boxSize.height),
        alpha = configs.boxAlpha,
        cornerRadius = configs.rectCornerRadius
    )

    drawText(
        textLayoutResult = textLayoutResult,
        topLeft = Offset(x = startXOfText, y = topYOfText)
    )

    val startY = if (topYOfBox == 0f) boxSize.height else 0f
    if (currentCoordinate.second != 0f) {
        drawLine(
            start = Offset(x = currentCoordinate.first, y = startY),
            end = Offset(
                x = currentCoordinate.first,
                y = currentCoordinate.second.minus(configs.markerSize?.toPx()?.times(2f) ?: 5f)
            ),
            color = configs.labelLineColor,
            alpha = configs.labelLineAlpha,
            strokeWidth = configs.labelLineStrokeWidth,
            cap = configs.labelLineStrokeCap
        )
    }

    val endY = if (topYOfBox != 0f) topYOfBox else size.height
    if (currentCoordinate.second != size.height) {
        drawLine(
            start = Offset(
                x = currentCoordinate.first,
                y = currentCoordinate.second.plus(configs.markerSize?.toPx()?.times(2f) ?: 5f)
            ),
            end = Offset(x = currentCoordinate.first, y = endY),
            color = configs.labelLineColor,
            alpha = configs.labelLineAlpha,
            strokeWidth = configs.labelLineStrokeWidth,
            cap = configs.labelLineStrokeCap
        )
    }
}