package com.joshrose.plotsforcompose.internals.plots

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.rememberTextMeasurer
import com.joshrose.plotsforcompose.axis.config.axisline.AxisLineConfiguration
import com.joshrose.plotsforcompose.axis.config.guidelines.GuidelinesConfiguration
import com.joshrose.plotsforcompose.axis.config.labels.LabelsConfiguration
import com.joshrose.plotsforcompose.axis.util.floatLabels
import com.joshrose.plotsforcompose.internals.*
import com.joshrose.plotsforcompose.internals.aesthetics.axis.unboundXAxis
import com.joshrose.plotsforcompose.internals.aesthetics.axis.unboundYAxis
import com.joshrose.plotsforcompose.internals.util.Range

@OptIn(ExperimentalTextApi::class)
@Composable
fun LineGraph(plot: Plot, modifier: Modifier = Modifier) {
    val xTextMeasurer = rememberTextMeasurer()
    val yTextMeasurer = rememberTextMeasurer()

    val data = getData(plot.data)

    val x = asMappingData(data = data, mapping = plot.mapping.map, key = "x")
    val y = asMappingData(data = data, mapping = plot.mapping.map, key = "y")

    val scaleX: Scale? = plot.scales().lastOrNull { it.scale == ScaleKind.X }
    val scaleY: Scale? = plot.scales().lastOrNull { it.scale == ScaleKind.Y }

    val xAxisData = getAxisData(
        data = x,
        minValueAdjustment = scaleX?.labelConfigs?.minValueAdjustment,
        maxValueAdjustment = scaleX?.labelConfigs?.maxValueAdjustment,
        rangeAdjustment = scaleX?.labelConfigs?.rangeAdjustment
    )

    val xLabels = floatLabels(
        breaks = scaleX?.labelConfigs?.breaks ?: 5,
        minValue = xAxisData.min,
        maxValue = xAxisData.max
    )

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

    val xAxisLineConfigs = xConfigurationOrNull(scaleX)
    val yAxisLineConfigs = yConfigurationOrNull(scaleY)

    val xAxisPosition = getXAxisPosition(config = xAxisLineConfigs, yAxisData = yAxisData)
    val yAxisPosition = getYAxisPosition(config = yAxisLineConfigs, xAxisData = xAxisData)

    Canvas(modifier = modifier) {
        scaleX?.let {
            unboundXAxis(
                labelConfigs = it.labelConfigs ?: LabelsConfiguration(),
                guidelinesConfigs = it.guidelinesConfigs ?: GuidelinesConfiguration(),
                axisLineConfigs = xAxisLineConfigs ?: AxisLineConfiguration.XConfiguration(),
                labels = xLabels,
                xRangeValues = Range(min = xAxisData.min, max = xAxisData.max),
                xAxisPosition = xAxisPosition,
                yAxisPosition = yAxisPosition,
                drawYAxis = scaleY.isNotNull() && yAxisLineConfigs?.showAxisLine ?: AxisLineConfiguration.YConfiguration().ticks,
                range = xAxisData.range,
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