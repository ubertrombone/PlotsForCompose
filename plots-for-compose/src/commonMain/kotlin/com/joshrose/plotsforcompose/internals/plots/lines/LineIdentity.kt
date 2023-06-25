package com.joshrose.plotsforcompose.internals.plots.lines

import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import com.joshrose.plotsforcompose.axis.config.axisline.AxisLineConfiguration
import com.joshrose.plotsforcompose.axis.config.guidelines.GuidelinesConfiguration
import com.joshrose.plotsforcompose.axis.config.labels.LabelsConfiguration
import com.joshrose.plotsforcompose.axis.util.AxisPosition
import com.joshrose.plotsforcompose.axis.util.floatLabelsAndBreaks
import com.joshrose.plotsforcompose.internals.*
import com.joshrose.plotsforcompose.internals.aesthetics.axis.unboundYAxis
import com.joshrose.plotsforcompose.internals.util.Range
import kotlin.math.roundToInt

@OptIn(ExperimentalTextApi::class)
internal fun DrawScope.lineIdentityAxis(
    plot: Plot,
    xAxisPosition: AxisPosition.XAxis,
    yAxisPosition: AxisPosition.YAxis,
    scaleX: Scale?,
    scaleY: Scale,
    xAxisLineConfigs: AxisLineConfiguration.XConfiguration?,
    yAxisLineConfigs: AxisLineConfiguration.YConfiguration?,
    yTextMeasurer: TextMeasurer
) {
    val y = asMappingData(data = getData(plot.data), mapping = plot.mapping.map, key = "y")
    requireNotNull(value = y) { "LinePlot must have values defined for Y." }
    require(value = isCastAsNumber(y)) { "LinePlot requires Y values be of type Number." }

    val yAxisData = getAxisData(
        data = y,
        minValueAdjustment = scaleY.labelConfigs?.minValueAdjustment,
        maxValueAdjustment = scaleY.labelConfigs?.maxValueAdjustment,
        rangeAdjustment = scaleY.labelConfigs?.rangeAdjustment
    )

    val yBreaks = when {
        scaleY.breaks == null && scaleY.labels == null -> floatLabelsAndBreaks(
            amount = y.size,
            minValue = yAxisData.min,
            maxValue = yAxisData.max
        )
        scaleY.breaks == null -> floatLabelsAndBreaks(
            amount = (y.size.times((scaleY.labels?.factor ?: 1f))).roundToInt(),
            minValue = yAxisData.min,
            maxValue = yAxisData.max
        )
        scaleY.guidelinesConfigs?.showGuidelines == false -> null
        else -> floatLabelsAndBreaks(
            amount = (y.size.times((scaleY.breaks.factor))).roundToInt(),
            minValue = yAxisData.min,
            maxValue = yAxisData.max
        )
    }

    val yLabels = when {
        scaleY.breaks == null && scaleY.labels == null -> floatLabelsAndBreaks(
            amount = y.size,
            minValue = yAxisData.min,
            maxValue = yAxisData.max
        )
        scaleY.labels == null -> yBreaks
        scaleY.labelConfigs?.showLabels == false -> null
        yBreaks == null -> floatLabelsAndBreaks(
            amount = (y.size.times((scaleY.labels.factor))).roundToInt(),
            minValue = yAxisData.min,
            maxValue = yAxisData.max
        )
        else -> floatLabelsAndBreaks(
            amount = (yBreaks.size.times((scaleY.labels.factor))).roundToInt(),
            minValue = yAxisData.min,
            maxValue = yAxisData.max
        )
    }

    unboundYAxis(
        labelConfigs = scaleY.labelConfigs ?: LabelsConfiguration(),
        guidelinesConfigs = scaleY.guidelinesConfigs ?: GuidelinesConfiguration(),
        axisLineConfigs = yAxisLineConfigs ?: AxisLineConfiguration.YConfiguration(),
        labels = yLabels,
        guidelines = yBreaks,
        yRangeValues = Range(min = yAxisData.min, max = yAxisData.max),
        yAxisPosition = yAxisPosition,
        xAxisPosition = xAxisPosition,
        drawXAxis = scaleX.isNotNull() && xAxisLineConfigs?.showAxisLine ?: AxisLineConfiguration.XConfiguration().ticks,
        range = yAxisData.range,
        textMeasurer = yTextMeasurer
    )
}