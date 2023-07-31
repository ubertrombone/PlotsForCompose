@file:Suppress("DuplicatedCode")

package com.joshrose.plotsforcompose.internals.plots.lines

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.rememberTextMeasurer
import com.joshrose.plotsforcompose.axis.config.axisline.AxisLineConfiguration
import com.joshrose.plotsforcompose.axis.config.guidelines.GuidelinesConfiguration
import com.joshrose.plotsforcompose.axis.config.labels.LabelsConfiguration
import com.joshrose.plotsforcompose.axis.util.AxisAlignment
import com.joshrose.plotsforcompose.figures.LineFigure
import com.joshrose.plotsforcompose.internals.*
import com.joshrose.plotsforcompose.internals.StatKind.COUNT
import com.joshrose.plotsforcompose.internals.StatKind.IDENTITY
import com.joshrose.plotsforcompose.internals.aesthetics.axis.boundXAxis

@OptIn(ExperimentalTextApi::class)
@Composable
fun LinePlot(plot: Plot, modifier: Modifier = Modifier) {
    val xTextMeasurer = rememberTextMeasurer()
    val yTextMeasurer = rememberTextMeasurer()

    val figure = plot.mapping.map["figure"] as LineFigure
    val configs = figure.configs
    val data = getData(plot.data)

    val x = asMappingData(data = data, mapping = plot.mapping.map, key = "x")
    requireNotNull(value = x) { "LinePlot must have values defined for X." }

    val y = when (figure.stat.kind) {
        COUNT -> x.groupingBy { it }.eachCount().values
        else -> asMappingData(data = data, mapping = plot.mapping.map, key = "y")
    }?.toList()
    requireNotNull(value = y) { "LinePlot must have values defined for Y." }
    require(value = isCastAsNumber(y)) { "LinePlot requires Y values be of type Number." }

    val (newX, newY) = if (figure.stat.kind == COUNT) x to y else {
        x
            .zip(y.map { it.toString().toFloatOrNull() })
            .filter { it.second != null }.map { it.first to it.second!! }
            .unzip()
    }

    val xData = if (figure.stat.kind == COUNT) newX.toSet() else newX

    val scaleX: Scale? = plot.scales().lastOrNull { it.scale == ScaleKind.X }
    val scaleY: Scale? = plot.scales().lastOrNull { it.scale == ScaleKind.Y }

    val xBreaks = getBoundBreaks(scale = scaleX, rawData = xData)
    val xLabels = getBoundLabels(scale = scaleX, rawData = xData, breaksData = xBreaks)
    val xLabelIndices = getIndices(scale = scaleX, rawData = xData, breaksData = xBreaks)

    val yAxisData = if (figure.stat.kind == IDENTITY) getAxisData(
        data = newY,
        minValueAdjustment = scaleY?.labelConfigs?.minValueAdjustment,
        maxValueAdjustment = scaleY?.labelConfigs?.maxValueAdjustment,
        rangeAdjustment = scaleY?.labelConfigs?.rangeAdjustment
    ) else null

    val xAxisLineConfigs = scaleX.xConfigurationOrNull()
    val yAxisLineConfigs = scaleY.yConfigurationOrNull()

    val xAxisPosition =
        if (figure.stat.kind == COUNT) xAxisLineConfigs.getXAxisPosition()
        else xAxisLineConfigs.getXAxisPosition(yAxisData = yAxisData!!)
    val yAxisPosition = yAxisLineConfigs.getYAxisPosition()

    Canvas(modifier = modifier) {
        val xGuidelineFactor =
            getXFactor(width = size.width, dataSize = xBreaks?.size, axisAlignment = xAxisLineConfigs?.axisAlignment)
        val xLabelFactor =
            getXFactor(width = size.width, dataSize = xLabels?.size, axisAlignment = xAxisLineConfigs?.axisAlignment)
        val xDataFactor =
            getXFactor(width = size.width, dataSize = xData.size, axisAlignment = xAxisLineConfigs?.axisAlignment)

        scaleX?.let {
            boundXAxis(
                labelConfigs = it.labelConfigs ?: LabelsConfiguration(),
                guidelinesConfigs = it.guidelinesConfigs ?: GuidelinesConfiguration(),
                axisLineConfigs = xAxisLineConfigs ?: AxisLineConfiguration.XConfiguration(),
                labelFactor = xLabelFactor,
                guidelinesFactor = xGuidelineFactor,
                labels = xLabels,
                labelIndices = xLabelIndices,
                guidelines = xBreaks,
                xAxisPosition = xAxisPosition,
                yAxisPosition = yAxisPosition,
                drawYAxis = scaleY.isNotNull() && scaleY?.showAxisLine ?: AxisLineConfiguration.YConfiguration().ticks,
                axisAlignment = xAxisLineConfigs?.axisAlignment ?: AxisAlignment.SpaceBetween,
                textMeasurer = xTextMeasurer,
                scale = scaleX
            )
        }

        if (figure.stat.kind == COUNT) {
            lineCountFigure(
                x = newX,
                xAxisPosition = xAxisPosition,
                yAxisPosition = yAxisPosition,
                scaleX = scaleX,
                scaleY = scaleY,
                xDataFactor = xDataFactor,
                lineConfigs = configs,
                yAxisLineConfigs = yAxisLineConfigs,
                xAxisLineConfigs = xAxisLineConfigs,
                yTextMeasurer = yTextMeasurer
            )
        } else {
            lineIdentityFigure(
                y = newY,
                yAxisData = yAxisData!!,
                xValues = xData,
                xAxisPosition = xAxisPosition,
                yAxisPosition = yAxisPosition,
                scaleX = scaleX,
                scaleY = scaleY,
                xDataFactor = xDataFactor,
                lineConfigs = configs,
                yAxisLineConfigs = yAxisLineConfigs,
                xAxisLineConfigs = xAxisLineConfigs,
                yTextMeasurer = yTextMeasurer
            )
        }
    }
}