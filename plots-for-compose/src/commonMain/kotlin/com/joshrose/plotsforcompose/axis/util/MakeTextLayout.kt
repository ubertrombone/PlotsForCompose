package com.joshrose.plotsforcompose.axis.util

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import com.joshrose.plotsforcompose.axis.config.labels.LabelsConfiguration
import java.text.DecimalFormat

@OptIn(ExperimentalTextApi::class)
fun makeTextLayout(
    label: Float,
    textMeasurer: TextMeasurer,
    labelConfig: LabelsConfiguration,
): TextLayoutResult {
    val labelString = AnnotatedString(label.formatToString(labelConfig.format))
    return textMeasurer.measure(
        text = labelString,
        style = labelConfig.textStyle.copy(color = labelConfig.fontColor),
        overflow = TextOverflow.Visible,
        softWrap = false
    )
}

@OptIn(ExperimentalTextApi::class)
fun makeTextLayout(
    label: String,
    textMeasurer: TextMeasurer,
    labelConfig: LabelsConfiguration
) = textMeasurer.measure(
    text = label,
    style = labelConfig.textStyle.copy(color = labelConfig.fontColor),
    overflow = TextOverflow.Visible,
    softWrap = false
)

fun Float.formatToString(pattern: String): String = DecimalFormat(pattern).format(this).toString()