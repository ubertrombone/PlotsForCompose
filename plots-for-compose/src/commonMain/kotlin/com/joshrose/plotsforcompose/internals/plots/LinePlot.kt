package com.joshrose.plotsforcompose.internals.plots

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.rememberTextMeasurer
import com.joshrose.plotsforcompose.axis.config.axisline.AxisLineConfiguration
import com.joshrose.plotsforcompose.axis.config.guidelines.GuidelinesConfiguration
import com.joshrose.plotsforcompose.axis.config.labels.LabelsConfiguration
import com.joshrose.plotsforcompose.axis.util.AxisAlignment
import com.joshrose.plotsforcompose.axis.util.floatLabels
import com.joshrose.plotsforcompose.internals.*
import com.joshrose.plotsforcompose.internals.aesthetics.axis.boundXAxis
import com.joshrose.plotsforcompose.internals.aesthetics.axis.unboundYAxis
import com.joshrose.plotsforcompose.internals.exception.MissingAestheticException
import com.joshrose.plotsforcompose.internals.util.Range

@OptIn(ExperimentalTextApi::class)
@Composable
fun LinePlot(plot: Plot, modifier: Modifier = Modifier) {
    val xTextMeasurer = rememberTextMeasurer()
    val yTextMeasurer = rememberTextMeasurer()

    val data = getData(plot.data)

    val x = asMappingData(data = data, mapping = plot.mapping.map, key = "x")
        ?: throw MissingAestheticException("LinePlot must have an X aesthetic mapping.")
    // TODO: Y should be a list of counts of the X items.
    // TODO: Check the StatKind
    val y = asMappingData(data = data, mapping = plot.mapping.map, key = "y")

    val scaleX: Scale? = plot.scales().lastOrNull { it.scale == ScaleKind.X }
    val scaleY: Scale? = plot.scales().lastOrNull { it.scale == ScaleKind.Y }

    val xLabels = x.sortedNotNull()

    val yAxisData = getAxisData(
        data = y,
        minValueAdjustment = scaleY?.labelConfigs?.minValueAdjustment,
        maxValueAdjustment = scaleY?.labelConfigs?.maxValueAdjustment,
        rangeAdjustment = scaleY?.labelConfigs?.rangeAdjustment
    )

    val yLabels = floatLabels(
        breaks = scaleY?.labelConfigs?.breaks ?: 5,
        minValue = yAxisData.min,
        maxValue = yAxisData.max
    )

    val xAxisLineConfigs = scaleX.xConfigurationOrNull()
    val yAxisLineConfigs = scaleY.yConfigurationOrNull()

    val xAxisPosition = xAxisLineConfigs.getXAxisPosition(yAxisData = yAxisData)
    val yAxisPosition = yAxisLineConfigs.getYAxisPosition()

    Canvas(modifier = modifier) {
        scaleX?.let {
            boundXAxis(
                labelConfigs = it.labelConfigs ?: LabelsConfiguration(),
                guidelinesConfigs = it.guidelinesConfigs ?: GuidelinesConfiguration(),
                axisLineConfigs = xAxisLineConfigs ?: AxisLineConfiguration.XConfiguration(),
                labels = xLabels,
                xAxisPosition = xAxisPosition,
                yAxisPosition = yAxisPosition,
                drawYAxis = scaleY.isNotNull() && yAxisLineConfigs?.showAxisLine ?: AxisLineConfiguration.YConfiguration().ticks,
                axisAlignment = xAxisLineConfigs?.axisAlignment ?: AxisAlignment.SpaceBetween,
                textMeasurer = xTextMeasurer
            )
        }
        scaleY?.let {
            unboundYAxis(
                labelConfigs = it.labelConfigs ?: LabelsConfiguration(),
                guidelinesConfigs = it.guidelinesConfigs ?: GuidelinesConfiguration(),
                axisLineConfigs = yAxisLineConfigs ?: AxisLineConfiguration.YConfiguration(),
                labels = yLabels,
                yRangeValues = Range(min = yAxisData.min, max = yAxisData.max),
                yAxisPosition = yAxisPosition,
                xAxisPosition = xAxisPosition,
                drawXAxis = scaleX.isNotNull() && xAxisLineConfigs?.showAxisLine ?: AxisLineConfiguration.XConfiguration().ticks,
                range = yAxisData.range,
                textMeasurer = yTextMeasurer
            )
        }
    }
}