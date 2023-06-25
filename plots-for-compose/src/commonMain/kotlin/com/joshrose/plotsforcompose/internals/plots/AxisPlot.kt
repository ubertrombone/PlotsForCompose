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
import com.joshrose.plotsforcompose.internals.util.Range

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

    val xLabels = floatLabelsAndBreaks(
        amount = scaleX?.labelConfigs?.breaks ?: 5,
        minValue = xAxisData.min,
        maxValue = xAxisData.max
    )

    var yAxisData = getAxisData(
        data = y,
        minValueAdjustment = scaleY?.labelConfigs?.minValueAdjustment,
        maxValueAdjustment = scaleY?.labelConfigs?.maxValueAdjustment,
        rangeAdjustment = scaleY?.labelConfigs?.rangeAdjustment
    )
    if (yAxisData.min == yAxisData.max) yAxisData = yAxisData.copy(min = yAxisData.min.minus(1))

    val yLabels = floatLabelsAndBreaks(
        amount = scaleY?.labelConfigs?.breaks ?: 5,
        minValue = yAxisData.min,
        maxValue = yAxisData.max
    )

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
                xRangeValues = Range(min = xAxisData.min, max = xAxisData.max),
                xAxisPosition = xAxisPosition,
                yAxisPosition = yAxisPosition,
                drawYAxis = scaleY.isNotNull() && yAxisLineConfigs?.showAxisLine ?: YConfiguration().ticks,
                drawZero = drawZero,
                range = xAxisData.range,
                textMeasurer = xTextMeasurer
            )
        }
        scaleY?.let {
            unboundYAxis(
                labelConfigs = it.labelConfigs ?: LabelsConfiguration(),
                guidelinesConfigs = it.guidelinesConfigs ?: GuidelinesConfiguration(),
                axisLineConfigs = yAxisLineConfigs ?: YConfiguration(),
                labels = yLabels,
                guidelines = yLabels,
                yRangeValues = Range(min = yAxisData.min, max = yAxisData.max),
                yAxisPosition = yAxisPosition,
                xAxisPosition = xAxisPosition,
                drawXAxis = scaleX.isNotNull() && xAxisLineConfigs?.showAxisLine ?: XConfiguration().ticks,
                drawZero = drawZero,
                range = yAxisData.range,
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