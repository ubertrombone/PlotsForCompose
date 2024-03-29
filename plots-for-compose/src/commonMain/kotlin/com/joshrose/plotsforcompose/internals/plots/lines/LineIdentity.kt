package com.joshrose.plotsforcompose.internals.plots.lines

import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import com.joshrose.plotsforcompose.axis.config.axisline.AxisLineConfiguration
import com.joshrose.plotsforcompose.axis.config.guidelines.GuidelinesConfiguration
import com.joshrose.plotsforcompose.axis.config.labels.LabelsConfiguration
import com.joshrose.plotsforcompose.axis.config.util.Multiplier
import com.joshrose.plotsforcompose.axis.util.AxisAlignment
import com.joshrose.plotsforcompose.axis.util.AxisPosition
import com.joshrose.plotsforcompose.internals.*
import com.joshrose.plotsforcompose.internals.aesthetics.axis.unboundYAxis
import com.joshrose.plotsforcompose.internals.util.AxisData
import com.joshrose.plotsforcompose.internals.util.Range
import com.joshrose.plotsforcompose.linegraph.config.LineGraphConfiguration
import com.joshrose.plotsforcompose.linegraph.util.LineType
import com.joshrose.plotsforcompose.util.calculateOffset

@OptIn(ExperimentalTextApi::class)
internal fun DrawScope.lineIdentityFigure(
    x: Collection<Any?>,
    y: List<Any?>,
    coordinates: MutableList<Pair<Float, Float>>,
    yAxisData: AxisData,
    data: MutableList<Pair<Any?, Float>>,
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
    data.clear()
    data.addAll(x.zip(y.map { it.toString().toFloat() }))

    val yBreaks = getUnboundBreaks(scale = scaleY, rawData = y, axisData = yAxisData)
    val yLabels = getUnboundLabels(scale = scaleY, rawData = y, breaksData = yBreaks, axisData = yAxisData)
    val yLabelIndices = getIndices(scale = scaleY, rawData = y, breaksData = yBreaks)

    scaleY?.let {
        unboundYAxis(
            labelConfigs = scaleY.labelConfigs ?: LabelsConfiguration(),
            guidelinesConfigs = scaleY.guidelinesConfigs ?: GuidelinesConfiguration(),
            axisLineConfigs = yAxisLineConfigs ?: AxisLineConfiguration.YConfiguration(),
            labels = yLabels,
            labelIndices = yLabelIndices,
            guidelines = yBreaks,
            yAxisPosition = yAxisPosition,
            xAxisPosition = xAxisPosition,
            drawXAxis = scaleX.isNotNull() && scaleX?.showAxisLine ?: AxisLineConfiguration.XConfiguration().ticks,
            textMeasurer = yTextMeasurer,
            scale = scaleY
        )
    }

    drawLineIdentity(
        data = data,
        yAxisData = yAxisData,
        xFactor = xDataFactor,
        xAxisAlignment = xAxisLineConfigs?.axisAlignment,
        lineConfigs = lineConfigs,
        rangeAdj = scaleY?.labelConfigs?.rangeAdjustment ?: Multiplier(0f),
        coordinates = coordinates
    )
}

internal fun DrawScope.drawLineIdentity(
    data: List<Pair<Any?, Float>>,
    coordinates: MutableList<Pair<Float, Float>>,
    yAxisData: AxisData,
    xFactor: Float,
    xAxisAlignment: AxisAlignment.XAxis?,
    lineConfigs: LineGraphConfiguration,
    rangeAdj: Multiplier
) {
    coordinates.clear()
    val linePath = Path().apply { moveTo(x = 0f, y = size.height) }

    val reducedData = Range(min = yAxisData.min, max = yAxisData.max)
    val range = yAxisData.range

    data.forEachIndexed { index, (_, value) ->
        val x =
            if (xAxisAlignment == AxisAlignment.Start || xAxisAlignment == AxisAlignment.SpaceBetween) index.times(xFactor)
            else index.plus(1).times(xFactor)

        val y = getY(
            height = size.height,
            yValues = reducedData,
            value = value,
            range = range,
            rangeAdj = rangeAdj
        )

        coordinates.add(x to y)
    }

    coordinates.forEachIndexed { index, (x, y) ->
        if (index == 0) linePath.moveTo(x, y)
        else {
            if (lineConfigs.lineType == LineType.STRAIGHT) linePath.lineTo(x, y)
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

internal fun getY(
    height: Float,
    yValues: Range<Float>,
    value: Float,
    range: Float,
    rangeAdj: Multiplier
): Float {
    val rangeDiff = calculateOffset(maxValue = yValues.max, offsetValue = value, range = range)
    val rangeAdjustment = if (yValues.min == 0f || yValues.max == 0f) 0f else rangeAdj.factor
    return if (yValues.max <= 0f) height.times(1f.plus(rangeAdjustment)).minus(rangeDiff.div(range).times(height))
    else height.minus(rangeDiff.div(range).times(height))
}