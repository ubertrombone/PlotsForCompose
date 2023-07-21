@file:Suppress("DuplicatedCode")

package com.joshrose.plotsforcompose.internals.plots

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import com.joshrose.plotsforcompose.axis.config.axisline.AxisLineConfiguration.XConfiguration
import com.joshrose.plotsforcompose.axis.config.axisline.AxisLineConfiguration.YConfiguration
import com.joshrose.plotsforcompose.axis.config.guidelines.GuidelinesConfiguration
import com.joshrose.plotsforcompose.axis.config.labels.LabelsConfiguration
import com.joshrose.plotsforcompose.axis.util.floatLabelsAndBreaks
import com.joshrose.plotsforcompose.internals.*
import com.joshrose.plotsforcompose.internals.aesthetics.axis.drawZero
import com.joshrose.plotsforcompose.internals.aesthetics.axis.unboundXAxis
import com.joshrose.plotsforcompose.internals.aesthetics.axis.unboundYAxis
import kotlin.math.roundToInt

@OptIn(ExperimentalTextApi::class)
@Composable
internal fun AxisPlot(plot: Plot, modifier: Modifier = Modifier) {
    val xTextMeasurer = rememberTextMeasurer()
    val yTextMeasurer = rememberTextMeasurer()
    val zeroTextMeasurer = rememberTextMeasurer()

    val data = getData(plot.data)

    val scaleX: Scale? = plot.scales().lastOrNull { it.scale == ScaleKind.X }
    val scaleY: Scale? = plot.scales().lastOrNull { it.scale == ScaleKind.Y }

    val x = asMappingData(data = data, mapping = plot.mapping.map, key = "x")
    val y = asMappingData(data = data, mapping = plot.mapping.map, key = "y")

    var xAxisData = getAxisData(
        data = x,
        minValueAdjustment = scaleX?.labelConfigs?.minValueAdjustment,
        maxValueAdjustment = scaleX?.labelConfigs?.maxValueAdjustment,
        rangeAdjustment = scaleX?.labelConfigs?.rangeAdjustment
    )
    if (xAxisData.min == xAxisData.max) xAxisData = xAxisData.copy(min = xAxisData.min.minus(1))

    val xBreaks = when {
        scaleX?.guidelinesConfigs?.showGuidelines == false -> null
        scaleX?.breaks == null -> floatLabelsAndBreaks(
            amount = x?.size ?: 20,
            minValue = xAxisData.min,
            maxValue = xAxisData.max
        )
        else -> floatLabelsAndBreaks(
            amount = ((x?.size ?: 20).times((scaleX.breaks.factor))).roundToInt(),
            minValue = xAxisData.min,
            maxValue = xAxisData.max
        )
    }

    val xLabels = when {
        scaleX?.labelConfigs?.showLabels == false -> null
        scaleX?.breaks == null && scaleX?.labels == null -> floatLabelsAndBreaks(
            amount = x?.size ?: 20,
            minValue = xAxisData.min,
            maxValue = xAxisData.max
        )
        scaleX.labels == null -> xBreaks
        xBreaks == null -> floatLabelsAndBreaks(
            amount = ((x?.size ?: 20).times((scaleX.labels.factor))).roundToInt(),
            minValue = xAxisData.min,
            maxValue = xAxisData.max
        )
        else -> floatLabelsAndBreaks(
            amount = (xBreaks.size.times((scaleX.labels.factor))).roundToInt(),
            minValue = xAxisData.min,
            maxValue = xAxisData.max
        )
    }

    val xLabelIndices = when {
        scaleX?.labelConfigs?.showLabels == false -> null
        scaleX?.labels == null -> xBreaks?.indices?.toList() ?: (x?.indices?.toList() ?: (1..20).toList())
        xBreaks == null ->
            List(x?.size ?: 20) { index -> if (index % (1.div(scaleX.labels.factor)).roundToInt() == 0) index else null }.filterNotNull()
        else ->
            List(xBreaks.size) { index -> if (index % (1.div(scaleX.labels.factor)).roundToInt() == 0) index else null }.filterNotNull()
    }

    var yAxisData = getAxisData(
        data = y,
        minValueAdjustment = scaleY?.labelConfigs?.minValueAdjustment,
        maxValueAdjustment = scaleY?.labelConfigs?.maxValueAdjustment,
        rangeAdjustment = scaleY?.labelConfigs?.rangeAdjustment
    )
    if (yAxisData.min == yAxisData.max) yAxisData = yAxisData.copy(min = yAxisData.min.minus(1))

    val yBreaks = when {
        scaleY?.guidelinesConfigs?.showGuidelines == false -> null
        scaleY?.breaks == null -> floatLabelsAndBreaks(
            amount = y?.size ?: 20,
            minValue = yAxisData.min,
            maxValue = yAxisData.max
        )
        else -> floatLabelsAndBreaks(
            amount = ((y?.size ?: 20).times((scaleY.breaks.factor))).roundToInt(),
            minValue = yAxisData.min,
            maxValue = yAxisData.max
        )
    }

    val yLabels = when {
        scaleY?.labelConfigs?.showLabels == false -> null
        scaleY?.breaks == null && scaleY?.labels == null -> floatLabelsAndBreaks(
            amount = y?.size ?: 20,
            minValue = yAxisData.min,
            maxValue = yAxisData.max
        )
        scaleY.labels == null -> yBreaks
        yBreaks == null -> floatLabelsAndBreaks(
            amount = ((y?.size ?: 20).times((scaleY.labels.factor))).roundToInt(),
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
        scaleY?.labelConfigs?.showLabels == false -> null
        scaleY?.labels == null -> yBreaks?.indices?.toList() ?: (y?.indices?.toList() ?: (1..20).toList())
        yBreaks == null ->
            List(y?.size ?: 20) { index -> if (index % (1.div(scaleY.labels.factor)).roundToInt() == 0) index else null }.filterNotNull()
        else ->
            List(yBreaks.size) { index -> if (index % (1.div(scaleY.labels.factor)).roundToInt() == 0) index else null }.filterNotNull()
    }

    val xAxisLineConfigs = scaleX.xConfigurationOrNull()
    val yAxisLineConfigs = scaleY.yConfigurationOrNull()

    val xAxisPosition = xAxisLineConfigs.getXAxisPosition(yAxisData = yAxisData)
    val yAxisPosition = yAxisLineConfigs.getYAxisPosition(xAxisData = xAxisData)

    val drawZero = drawZero(
        scaleX = scaleX,
        scaleY = scaleY,
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        xAxisPosition = xAxisPosition,
        yAxisPosition = yAxisPosition,
        xLabels = xLabels,
        yLabels = yLabels
    )

    Canvas(modifier = modifier) {
        scaleX?.let {
            unboundXAxis(
                labelConfigs = it.labelConfigs ?: LabelsConfiguration(),
                guidelinesConfigs = it.guidelinesConfigs ?: GuidelinesConfiguration(),
                axisLineConfigs = xAxisLineConfigs ?: XConfiguration(),
                labels = xLabels,
                labelIndices = xLabelIndices,
                guidelines = xBreaks,
                xAxisPosition = xAxisPosition,
                yAxisPosition = yAxisPosition,
                drawYAxis = scaleY.isNotNull() && yAxisLineConfigs?.showAxisLine ?: YConfiguration().ticks,
                drawZero = drawZero,
                textMeasurer = xTextMeasurer
            )
        }
        scaleY?.let {
            unboundYAxis(
                labelConfigs = it.labelConfigs ?: LabelsConfiguration(),
                guidelinesConfigs = it.guidelinesConfigs ?: GuidelinesConfiguration(),
                axisLineConfigs = yAxisLineConfigs ?: YConfiguration(),
                labels = yLabels,
                labelIndices = yLabelIndices,
                guidelines = yLabels,
                yAxisPosition = yAxisPosition,
                xAxisPosition = xAxisPosition,
                drawXAxis = scaleX.isNotNull() && xAxisLineConfigs?.showAxisLine ?: XConfiguration().ticks,
                drawZero = drawZero,
                textMeasurer = yTextMeasurer
            )
        }

        if (!drawZero) {
            drawZero(
                xAxisPosition = xAxisPosition,
                yAxisPosition = yAxisPosition,
                xAxisOffset = scaleY?.labelConfigs?.axisOffset?.toPx() ?: 25.dp.toPx(),
                yAxisOffset = scaleX?.labelConfigs?.axisOffset?.toPx() ?: 25.dp.toPx(),
                textMeasurer = zeroTextMeasurer,
                labelConfig = scaleY?.labelConfigs ?: LabelsConfiguration()
            )
        }
    }
}