package com.joshrose.plotsforcompose.internals.plots.lines

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.drawText

internal actual fun DrawScope.drawLabelRect(
    currentCoordinate: Pair<Float, Float>,
    boxSize: Size,
    labelOffset: Offset,
    textLayoutResult: TextLayoutResult
) {
    val startXOfBox = when (currentCoordinate.first) {
        0f -> 0f
        size.width -> size.width.minus(boxSize.width)
        else -> currentCoordinate.first.minus(boxSize.width.div(2f))
    }

    val topYOfText = labelOffset.y
    val startXOfText = startXOfBox.plus(labelOffset.x)

    drawRoundRect(
        color = Color.LightGray,
        topLeft = Offset(x = startXOfBox, y = 0f),
        size = Size(width = boxSize.width, height = boxSize.height),
        alpha = .3f,
        cornerRadius = CornerRadius(x = 10f, y = 10f)
    )

    drawText(
        textLayoutResult = textLayoutResult,
        topLeft = Offset(x = startXOfText, y = topYOfText)
    )
}