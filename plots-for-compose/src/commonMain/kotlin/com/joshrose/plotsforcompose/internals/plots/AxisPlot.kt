@file:Suppress("DuplicatedCode")

package com.joshrose.plotsforcompose.internals.plots

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.joshrose.plotsforcompose.axis.config.axisline.AxisLineConfiguration.XConfiguration
import com.joshrose.plotsforcompose.axis.config.axisline.AxisLineConfiguration.YConfiguration
import com.joshrose.plotsforcompose.axis.config.guidelines.GuidelinesConfiguration
import com.joshrose.plotsforcompose.axis.config.labels.LabelsConfiguration
import com.joshrose.plotsforcompose.axis.util.AxisPosition.*
import com.joshrose.plotsforcompose.axis.util.floatLabels
import com.joshrose.plotsforcompose.internals.*
import com.joshrose.plotsforcompose.internals.aesthetics.axis.drawZero
import com.joshrose.plotsforcompose.internals.aesthetics.axis.unboundXAxis
import com.joshrose.plotsforcompose.internals.aesthetics.axis.unboundYAxis
import com.joshrose.plotsforcompose.internals.util.Range
import com.joshrose.plotsforcompose.internals.util.maxValue
import com.joshrose.plotsforcompose.internals.util.minValue
import com.joshrose.plotsforcompose.internals.util.range
import com.joshrose.plotsforcompose.util.width

@OptIn(ExperimentalTextApi::class)
@Composable
internal fun AxisPlot(plot: Plot, modifier: Modifier = Modifier) {
    val xTextMeasurer = rememberTextMeasurer()
    val yTextMeasurer = rememberTextMeasurer()
    val zeroTextMeasurer = rememberTextMeasurer()

    val data = getData(plot.data)

    val scaleX: Scale? = plot.scales().lastOrNull { it.scale == ScaleKind.X }
    val scaleY: Scale? = plot.scales().lastOrNull { it.scale == ScaleKind.Y }

    // TODO: Is this still needed?
    val size: Map<String, Dp?>? =
        plot.otherFeatures().lastOrNull { it.kind == "size" }?.configs?.mapValues { it.value as Dp? }

    val x = asMappingData(data = data, mapping = plot.mapping.map, key = "x")
    val y = asMappingData(data = data, mapping = plot.mapping.map, key = "y")

    val rawXMax = x?.mapNotNull { it.toFloatOrNull() }?.maxOrNull() ?: 100f
    val xMax = maxValue(
        maxValue = rawXMax,
        maxValueAdjustment = scaleX?.labelConfigs?.maxValueAdjustment
    )

    val rawXMin = x?.mapNotNull { it.toFloatOrNull() }?.minOrNull() ?: 0f
    val xMin = minValue(
        minValue = rawXMin,
        maxValue = xMax,
        minValueAdjustment = scaleX?.labelConfigs?.minValueAdjustment
    )

    val xRange = range(
        minValue = xMin,
        maxValue = xMax,
        rangeAdjustment = scaleX?.labelConfigs?.rangeAdjustment
    )

    val xLabels = floatLabels(
        breaks = scaleX?.labelConfigs?.breaks ?: 5,
        minValue = xMin,
        maxValue = xMax
    )

    val rawYMax = y?.mapNotNull { it.toFloatOrNull() }?.maxOrNull() ?: 100f
    val yMax = maxValue(
        maxValue = rawYMax,
        maxValueAdjustment = scaleY?.labelConfigs?.maxValueAdjustment
    )

    val rawYMin = y?.mapNotNull { it.toFloatOrNull() }?.minOrNull() ?: 0f
    val yMin = minValue(
        minValue = rawYMin,
        maxValue = yMax,
        minValueAdjustment = scaleY?.labelConfigs?.minValueAdjustment
    )

    val yRange = range(
        minValue = yMin,
        maxValue = yMax,
        rangeAdjustment = scaleY?.labelConfigs?.rangeAdjustment
    )

    val yLabels = floatLabels(
        breaks = scaleY?.labelConfigs?.breaks ?: 5,
        minValue = yMin,
        maxValue = yMax
    )

    val xAxisLineConfigs = when (scaleX?.axisLineConfigs) {
        is XConfiguration -> scaleX.axisLineConfigs
        is YConfiguration ->
            throw IllegalStateException("Axis Line Configurations on the X scale should be of type XConfiguration.")
        null -> null
    }

    val yAxisLineConfigs = when (scaleY?.axisLineConfigs) {
        is XConfiguration ->
            throw IllegalStateException("Axis Line Configurations on the Y scale should be of type YConfiguration.")
        is YConfiguration -> scaleY.axisLineConfigs
        null -> null
    }

    // TODO: Add concept of axis on both sides
    val xAxisPosition = xAxisLineConfigs?.axisPosition ?: when {
        yMax <= 0 -> Top
        yMin < 0 -> Center
        else -> Bottom
    }

    val yAxisPosition = yAxisLineConfigs?.axisPosition ?: when {
        xMax <= 0 -> End
        xMin < 0 -> Center
        else -> Start
    }

    val drawZero = when {
        yMin == 0f && xMin == 0f &&
                xAxisPosition == Bottom && yAxisPosition == Start &&
                scaleX.isNotNull() && scaleY.isNotNull() &&
                scaleX?.labelConfigs?.showLabels == true && scaleY?.labelConfigs?.showLabels == true -> false
        yMax == 0f && xMin == 0f &&
                xAxisPosition == Top && yAxisPosition == Start &&
                scaleX.isNotNull() && scaleY.isNotNull() &&
                scaleX?.labelConfigs?.showLabels == true && scaleY?.labelConfigs?.showLabels == true -> false
        yMin == 0f && xMax == 0f &&
                xAxisPosition == Bottom && yAxisPosition == End &&
                scaleX.isNotNull() && scaleY.isNotNull() &&
                scaleX?.labelConfigs?.showLabels == true && scaleY?.labelConfigs?.showLabels == true -> false
        yMax == 0f && xMax == 0f &&
                xAxisPosition == Top && yAxisPosition == End &&
                scaleX.isNotNull() && scaleY.isNotNull() &&
                scaleX?.labelConfigs?.showLabels == true && scaleY?.labelConfigs?.showLabels == true -> false
        (xLabels.min() != 0f && xLabels.max() != 0f && xLabels.contains(0f)) &&
                (yLabels.min() != 0f && yLabels.max() != 0f && yLabels.contains(0f)) &&
                xAxisPosition == Center && yAxisPosition == Center &&
                scaleX.isNotNull() && scaleY.isNotNull() &&
                (scaleX?.labelConfigs?.showLabels == true || scaleY?.labelConfigs?.showLabels == true) -> false
        else -> true
    }

    Canvas(
        modifier = modifier
            .height(size?.get("height") ?: 500.dp)
            .width(size?.get("width"))
    ) {
        scaleX?.let {
            unboundXAxis(
                labelConfigs = it.labelConfigs ?: LabelsConfiguration(),
                guidelinesConfigs = it.guidelinesConfigs ?: GuidelinesConfiguration(),
                axisLineConfigs = xAxisLineConfigs ?: XConfiguration(),
                labels = xLabels,
                xRangeValues = Range(min = xMin, max = xMax),
                xAxisPosition = xAxisPosition,
                yAxisPosition = yAxisPosition,
                drawYAxis = scaleY.isNotNull() && yAxisLineConfigs?.showAxisLine ?: YConfiguration().ticks,
                drawZero = drawZero,
                range = xRange,
                textMeasurer = xTextMeasurer
            )
        }
        scaleY?.let {
            unboundYAxis(
                labelConfigs = it.labelConfigs ?: LabelsConfiguration(),
                guidelinesConfigs = it.guidelinesConfigs ?: GuidelinesConfiguration(),
                axisLineConfigs = yAxisLineConfigs ?: YConfiguration(),
                labels = yLabels,
                yRangeValues = Range(min = yMin, max = yMax),
                yAxisPosition = yAxisPosition,
                xAxisPosition = xAxisPosition,
                drawXAxis = scaleX.isNotNull() && xAxisLineConfigs?.showAxisLine ?: XConfiguration().ticks,
                drawZero = drawZero,
                range = yRange,
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