@file:Suppress("DuplicatedCode")

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
import kotlin.math.roundToInt

@OptIn(ExperimentalTextApi::class)
internal fun DrawScope.lineIdentityAxis(
    plot: Plot,
    xAxisPosition: AxisPosition.XAxis,
    yAxisPosition: AxisPosition.YAxis,
    scaleX: Scale?,
    scaleY: Scale,
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
        !scaleY.showGuidelines -> null
        scaleY.breaks == null -> floatLabelsAndBreaks(
            amount = y.size,
            minValue = yAxisData.min,
            maxValue = yAxisData.max
        )
        else -> floatLabelsAndBreaks(
            amount = (y.size.times((scaleY.breaks.factor))).roundToInt(),
            minValue = yAxisData.min,
            maxValue = yAxisData.max
        )
    }

    val yLabels = when {
        !scaleY.showLabels -> null
        scaleY.breaks == null && scaleY.labels == null -> floatLabelsAndBreaks(
            amount = y.size,
            minValue = yAxisData.min,
            maxValue = yAxisData.max
        )
        scaleY.labels == null -> yBreaks
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

    val yLabelIndices = when {
        !scaleY.showLabels -> null
        scaleY.labels == null -> yBreaks?.indices?.toList() ?: y.indices.toList()
        yBreaks == null ->
            List(y.size) { index -> if (index % (1.div(scaleY.labels.factor)).roundToInt() == 0) index else null }.filterNotNull()
        else ->
            List(yBreaks.size) { index -> if (index % (1.div(scaleY.labels.factor)).roundToInt() == 0) index else null }.filterNotNull()
    }

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