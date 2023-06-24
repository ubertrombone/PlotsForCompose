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
import kotlin.math.roundToInt

// TODO: On boundAxis, user should deal with sorting...
@OptIn(ExperimentalTextApi::class)
@Composable
fun LinePlot(plot: Plot, modifier: Modifier = Modifier) {
    val xTextMeasurer = rememberTextMeasurer()
    val yTextMeasurer = rememberTextMeasurer()

    val figure = plot.mapping.map["figure"] as LineFigure
    val data = getData(plot.data)

    val x = asMappingData(data = data, mapping = plot.mapping.map, key = "x")
    requireNotNull(value = x) { "LinePlot must have values defined for X." }

    val y = when (figure.stat.kind) {
        COUNT -> x.groupingBy { it }.eachCount().values
        else -> asMappingData(data = data, mapping = plot.mapping.map, key = "y")
    }?.toList()
    requireNotNull(value = y) { "LinePlot must have values defined for Y." }
    require(value = isCastAsNumber(y)) { "LinePlot requires Y values be of type Number." }

    val xData = if (figure.stat.kind == COUNT) x.toSet() else x

    val scaleX: Scale? = plot.scales().lastOrNull { it.scale == ScaleKind.X }
    val scaleY: Scale? = plot.scales().lastOrNull { it.scale == ScaleKind.Y }

    // TODO: IF xLabels.last() != xStat.last(), draw some space after last label/guideline
    // Consider:
        // --> Case1: scaleX?.breaks && scaleX?.labels == null -- xBreaks = xData && xLabels = xData
        // --> Case2: scaleX?.breaks?.factor is null -- xBreaks = xLabels
        // --> Case3: scaleX?.guidelinesConfigs?.showGuidelines = false -- xBreaks = null; do not draw
        // --> Case4: xLabels.size < xBreaks.size
        // --> Case5: xLabels.size > xBreaks.size -- scaleX?.labels?.factor = scaleX?.breaks?.factor
    val xBreaks = when {
        scaleX?.breaks == null && scaleX?.labels == null -> xData.toList()
        scaleX.breaks == null -> xData.filterIndexed { index, _ -> index % (1.div(scaleX.labels?.factor ?: 1f)).roundToInt() == 0 }
        scaleX.guidelinesConfigs?.showGuidelines == false -> null
        else -> xData.filterIndexed { index, _ -> index % (1.div(scaleX.breaks.factor)).roundToInt() == 0 }
    }

    val xLabels = when {
        scaleX?.breaks == null && scaleX?.labels == null -> xData.toList()
        scaleX.labels == null -> xBreaks
        scaleX.labelConfigs?.showLabels == false -> null
        else -> xData.filterIndexed { index, _ -> index % (1.div(scaleX.labels.factor)).roundToInt() == 0 }
    }

    val yAxisData = if (figure.stat.kind == IDENTITY) getAxisData(
        data = y,
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
        // TODO: Guideline factors
        val xLabelFactor =
            size.width.div(xLabels?.size?.plus((xAxisLineConfigs?.axisAlignment ?: AxisAlignment.SpaceBetween).offset)?.toFloat() ?: 1f)

        scaleX?.let {
            boundXAxis(
                labelConfigs = it.labelConfigs ?: LabelsConfiguration(),
                guidelinesConfigs = it.guidelinesConfigs ?: GuidelinesConfiguration(),
                axisLineConfigs = xAxisLineConfigs ?: AxisLineConfiguration.XConfiguration(),
                factor = xLabelFactor,
                labels = xLabels,
                xAxisPosition = xAxisPosition,
                yAxisPosition = yAxisPosition,
                drawYAxis = scaleY.isNotNull() && yAxisLineConfigs?.showAxisLine ?: AxisLineConfiguration.YConfiguration().ticks,
                axisAlignment = xAxisLineConfigs?.axisAlignment ?: AxisAlignment.SpaceBetween,
                textMeasurer = xTextMeasurer
            )
        }
        scaleY?.let {
            if (figure.stat.kind == COUNT) {
                lineCountAxis(
                    plot = plot,
                    x = x,
                    xAxisPosition = xAxisPosition,
                    yAxisPosition = yAxisPosition,
                    scaleX = scaleX,
                    xAxisLineConfigs = xAxisLineConfigs,
                    yAxisLineConfigs = yAxisLineConfigs,
                    yTextMeasurer = yTextMeasurer
                )
            } else {
                lineIdentityAxis(
                    plot = plot,
                    xAxisPosition = xAxisPosition,
                    yAxisPosition = yAxisPosition,
                    scaleX = scaleX,
                    xAxisLineConfigs = xAxisLineConfigs,
                    yAxisLineConfigs = yAxisLineConfigs,
                    yTextMeasurer = yTextMeasurer
                )
            }
        }
    }
}