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
import com.joshrose.plotsforcompose.internals.Scale
import com.joshrose.plotsforcompose.internals.aesthetics.axis.boundYAxis
import com.joshrose.plotsforcompose.internals.countsRange
import com.joshrose.plotsforcompose.internals.isNotNull
import kotlin.math.roundToInt

@OptIn(ExperimentalTextApi::class)
internal fun DrawScope.lineCountAxis(
    x: List<Any?>,
    xAxisPosition: AxisPosition.XAxis,
    yAxisPosition: AxisPosition.YAxis,
    scaleX: Scale?,
    scaleY: Scale,
    xAxisLineConfigs: AxisLineConfiguration.XConfiguration?,
    yAxisLineConfigs: AxisLineConfiguration.YConfiguration?,
    yTextMeasurer: TextMeasurer
) {
    val y = x.groupingBy { it }.eachCount().values.toSet().sorted().countsRange()

    val yBreaks = when {
        scaleY.guidelinesConfigs?.showGuidelines == false -> null
        scaleY.breaks == null -> y
        else -> y.filterIndexed { index, _ -> index % (1.div(scaleY.breaks.factor)).roundToInt() == 0 }
    }

    val yLabels = when {
        scaleY.labelConfigs?.showLabels == false -> null
        scaleY.breaks == null && scaleY.labels == null -> y
        scaleY.labels == null -> yBreaks
        yBreaks == null -> y.filterIndexed { index, _ -> index % (1.div(scaleY.labels.factor)).roundToInt() == 0 }
        else -> yBreaks.filterIndexed { index, _ -> index % (1.div(scaleY.labels.factor)).roundToInt() == 0 }
    }

    val yLabelIndices = when {
        scaleY.labelConfigs?.showLabels == false -> null
        scaleY.labels == null -> yBreaks?.indices?.toList() ?: y.indices.toList()
        yBreaks == null ->
            List(y.size) { index -> if (index % (1.div(scaleY.labels.factor)).roundToInt() == 0) index else null }.filterNotNull()
        else ->
            List(yBreaks.size) { index -> if (index % (1.div(scaleY.labels.factor)).roundToInt() == 0) index else null }.filterNotNull()
    }

    val yGuidelinesFactor =
        if (yBreaks?.size == 1 && (yAxisLineConfigs?.axisAlignment == AxisAlignment.SpaceBetween))
            size.height.div(yBreaks.size.toFloat())
        else size.height.div(yBreaks?.size?.plus((yAxisLineConfigs?.axisAlignment ?: AxisAlignment.SpaceBetween).offset)?.toFloat() ?: 1f)

    val yLabelFactor =
        if (yLabels?.size == 1 && (yAxisLineConfigs?.axisAlignment == AxisAlignment.SpaceBetween))
            size.height.div(yLabels.size.toFloat())
        else size.height.div(yLabels?.size?.plus((yAxisLineConfigs?.axisAlignment ?: AxisAlignment.SpaceBetween).offset)?.toFloat() ?: 1f)

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
        drawXAxis = scaleX.isNotNull() && xAxisLineConfigs?.showAxisLine ?: AxisLineConfiguration.XConfiguration().ticks,
        axisAlignment = yAxisLineConfigs?.axisAlignment ?: AxisAlignment.SpaceBetween,
        textMeasurer = yTextMeasurer
    )
}