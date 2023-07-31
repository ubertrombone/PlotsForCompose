package com.joshrose.plotsforcompose.internals.plots.lines

import androidx.compose.ui.graphics.Path
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
import com.joshrose.plotsforcompose.linegraph.config.LineGraphConfiguration
import com.joshrose.plotsforcompose.linegraph.util.LineType.STRAIGHT

@OptIn(ExperimentalTextApi::class)
internal fun DrawScope.lineCountFigure(
    x: List<Any?>,
    xAxisPosition: AxisPosition.XAxis,
    yAxisPosition: AxisPosition.YAxis,
    scaleX: Scale?,
    scaleY: Scale?,
    xDataFactor: Float,
    lineConfigs: LineGraphConfiguration,
    yAxisLineConfigs: AxisLineConfiguration.YConfiguration?,
    xAxisLineConfigs: AxisLineConfiguration.XConfiguration?,
    yTextMeasurer: TextMeasurer
) {
    val dataMap = x.groupingBy { it }.eachCount().map { Pair(it.key, it.value) }
    val y = x.groupingBy { it }.eachCount().values.toSet().sorted().countsRange()

    val yBreaks = getBoundBreaks(scale = scaleY, rawData = y)
    val yLabels = getBoundLabels(scale = scaleY, rawData = y, breaksData = yBreaks)
    val yLabelIndices = getIndices(scale = scaleY, rawData = y, breaksData = yBreaks)

    val yGuidelinesFactor =
        getYFactor(height = size.height, dataSize = yBreaks?.size, axisAlignment = yAxisLineConfigs?.axisAlignment)
    val yLabelFactor =
        getYFactor(height = size.height, dataSize = yLabels?.size, axisAlignment = yAxisLineConfigs?.axisAlignment)
    val yDataFactor =
        getYFactor(height = size.height, dataSize = y.size, axisAlignment = yAxisLineConfigs?.axisAlignment)

    scaleY?.let {
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

    drawLineCount(
        data = dataMap,
        yValues = y,
        xFactor = xDataFactor,
        yFactor = yDataFactor,
        xAxisAlignment = xAxisLineConfigs?.axisAlignment,
        yAxisAlignment = yAxisLineConfigs?.axisAlignment,
        lineConfigs = lineConfigs
    )
}

internal fun DrawScope.drawLineCount(
    data: List<Pair<Any?, Int>>,
    yValues: List<Int>,
    xFactor: Float,
    yFactor: Float,
    xAxisAlignment: AxisAlignment.XAxis?,
    yAxisAlignment: AxisAlignment.YAxis?,
    lineConfigs: LineGraphConfiguration
) {
    val coordinates: MutableList<Pair<Float, Float>> = mutableListOf()
    val linePath = Path().apply { moveTo(x = 0f, y = size.height) }

    data.forEachIndexed { index, (_, count) ->
        val x =
            if (xAxisAlignment == AxisAlignment.Start || xAxisAlignment == AxisAlignment.SpaceBetween) index.times(xFactor)
            else index.plus(1).times(xFactor)

        val y =
            if (yAxisAlignment == AxisAlignment.Top || yAxisAlignment == AxisAlignment.SpaceBetween)
                size.height.minus(yValues.indexOf(count).times(yFactor))
            else size.height.minus(yValues.indexOf(count).plus(1).times(yFactor))

        coordinates.add(x to y)
        // TODO: Custom marker types
        // TODO: Labels on tap/hover
    }

    coordinates.forEachIndexed { index, (x, y) ->
        if (index == 0) linePath.moveTo(x, y)
        else {
            if (lineConfigs.lineType == STRAIGHT) linePath.lineTo(x, y)
            else {
                linePath.cubicTo(
                    x1 = (x.plus(coordinates[index.minus(1)].first)) / 2f,
                    y1 = coordinates[index.minus(1)].second,
                    x2 = (x.plus(coordinates[index.minus(1)].first)) / 2f,
                    y2 = y,
                    x3 = x,
                    y3 = y
                )
            }
        }
    }

    drawLinePath(
        coordinates = coordinates,
        path = linePath,
        configs = lineConfigs
    )
}