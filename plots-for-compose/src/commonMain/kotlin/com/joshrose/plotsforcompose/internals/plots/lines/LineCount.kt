@file:Suppress("DuplicatedCode")

package com.joshrose.plotsforcompose.internals.plots.lines

import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import com.joshrose.plotsforcompose.axis.config.axisline.AxisLineConfiguration
import com.joshrose.plotsforcompose.axis.config.guidelines.GuidelinesConfiguration
import com.joshrose.plotsforcompose.axis.config.labels.LabelsConfiguration
import com.joshrose.plotsforcompose.axis.util.AxisAlignment
import com.joshrose.plotsforcompose.axis.util.AxisPosition
import com.joshrose.plotsforcompose.internals.*
import com.joshrose.plotsforcompose.internals.aesthetics.axis.boundYAxis

@OptIn(ExperimentalTextApi::class)
internal fun DrawScope.lineCountAxis(
    x: List<Any?>,
    xAxisPosition: AxisPosition.XAxis,
    yAxisPosition: AxisPosition.YAxis,
    scaleX: Scale?,
    scaleY: Scale,
    yAxisLineConfigs: AxisLineConfiguration.YConfiguration?,
    yTextMeasurer: TextMeasurer
) {
    val y = x.groupingBy { it }.eachCount().values.toSet().sorted().countsRange()

    val yBreaks = getBoundBreaks(scale = scaleY, rawData = y)
    val yLabels = getBoundLabels(scale = scaleY, rawData = y, breaksData = yBreaks)
    val yLabelIndices = getIndices(scale = scaleY, rawData = y, breaksData = yBreaks)

    val yGuidelinesFactor =
        getYFactor(height = size.height, dataSize = yBreaks?.size, axisAlignment = yAxisLineConfigs?.axisAlignment)
    val yLabelFactor =
        getYFactor(height = size.height, dataSize = yLabels?.size, axisAlignment = yAxisLineConfigs?.axisAlignment)

    boundYAxis(
        labelConfigs = scaleY.labelConfigs ?: LabelsConfiguration(),
        guidelinesConfigs = scaleY.guidelinesConfigs ?: GuidelinesConfiguration(),
        axisLineConfigs = yAxisLineConfigs ?: AxisLineConfiguration.YConfiguration(),
        labelFactor = yLabelFactor,
        labelIndices = yLabelIndices,
        guidelinesFactor = yGuidelinesFactor,
        labels = yLabels,
        guidelines = yBreaks,
        yAxisPosition = yAxisPosition,
        xAxisPosition = xAxisPosition,
        drawXAxis = scaleX.isNotNull() && scaleX?.showAxisLine ?: AxisLineConfiguration.XConfiguration().ticks,
        axisAlignment = yAxisLineConfigs?.axisAlignment ?: AxisAlignment.SpaceBetween,
        textMeasurer = yTextMeasurer,
        scale = scaleY
    )
}