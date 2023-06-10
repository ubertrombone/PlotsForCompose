package com.joshrose.plotsforcompose.internals.plots

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.joshrose.plotsforcompose.axis.config.axisline.AxisLineConfiguration
import com.joshrose.plotsforcompose.axis.config.guidelines.GuidelinesConfigurationDefaults
import com.joshrose.plotsforcompose.axis.config.labels.LabelsConfigurationDefaults
import com.joshrose.plotsforcompose.axis.util.*
import com.joshrose.plotsforcompose.axis.x.continuous.unboundXAxis
import com.joshrose.plotsforcompose.axis.y.continuous.unboundYAxis
import com.joshrose.plotsforcompose.common.maxValue
import com.joshrose.plotsforcompose.common.minValue
import com.joshrose.plotsforcompose.common.range
import com.joshrose.plotsforcompose.internals.*
import com.joshrose.plotsforcompose.util.width

@OptIn(ExperimentalTextApi::class)
@Composable
internal fun AxisPlot(plot: Plot, modifier: Modifier = Modifier) {
    val xTextMeasurer = rememberTextMeasurer()
    val yTextMeasurer = rememberTextMeasurer()
    val zeroTextMeasurer = rememberTextMeasurer()

    val data by remember { derivedStateOf { getData(plot.data) } }

    val scaleX: Scale? by remember { derivedStateOf { plot.scales().lastOrNull { it.scale == ScaleKind.X } } }
    val scaleY: Scale? by remember { derivedStateOf { plot.scales().lastOrNull { it.scale == ScaleKind.Y } } }

    // TODO: Is this still needed?
    val size: Map<String, Dp?>? =
        plot.otherFeatures().lastOrNull { it.kind == "size" }?.configs?.mapValues { it.value as Dp? }

    val x by remember { derivedStateOf { asMappingData(data = data, mapping = plot.mapping.map, key = "x") } }
    val y by remember { derivedStateOf { asMappingData(data = data, mapping = plot.mapping.map, key = "y") } }

    val rawXMax by remember { derivedStateOf { x?.mapNotNull { it.toFloatOrNull() }?.maxOrNull() ?: 100f } }
    val xMax by remember { derivedStateOf {
        maxValue(
            maxValue = rawXMax,
            maxValueAdjustment = scaleX?.labelConfigs?.maxValueAdjustment
        )
    }}

    val rawYMax by remember { derivedStateOf { y?.mapNotNull { it.toFloatOrNull() }?.maxOrNull() ?: 100f } }
    val yMax by remember { derivedStateOf {
        maxValue(
            maxValue = rawYMax,
            maxValueAdjustment = scaleY?.labelConfigs?.maxValueAdjustment
        )
    }}

    val rawXMin by remember { derivedStateOf { x?.mapNotNull { it.toFloatOrNull() }?.minOrNull() ?: 0f } }
    val xMin = minValue(
        minValue = rawXMin,
        maxValue = xMax,
        minValueAdjustment = scaleX?.labelConfigs?.minValueAdjustment
    )

    val rawYMin by remember { derivedStateOf { y?.mapNotNull { it.toFloatOrNull() }?.minOrNull() ?: 0f } }
    val yMin by remember { derivedStateOf {
        minValue(
            minValue = rawYMin,
            maxValue = yMax,
            minValueAdjustment = scaleY?.labelConfigs?.minValueAdjustment
        )
    }}

    val xRange by remember { derivedStateOf {
        range(
            minValue = xMin,
            maxValue = xMax,
            rangeAdjustment = scaleX?.labelConfigs?.rangeAdjustment
        )
    }}

    val yRange by remember { derivedStateOf {
        range(
            minValue = yMin,
            maxValue = yMax,
            rangeAdjustment = scaleY?.labelConfigs?.rangeAdjustment
        )
    }}

    val xLabels by remember { mutableStateOf(floatLabels(
        breaks = scaleX?.labelConfigs?.breaks ?: 5,
        minValue = xMin,
        maxValue = xMax
    ))}

    val yLabels by remember { mutableStateOf(floatLabels(
        breaks = scaleY?.labelConfigs?.breaks ?: 5,
        minValue = yMin,
        maxValue = yMax
    ))}

    // TODO: Test toXAxis and toYAxis
    val xAxisPosition = scaleX?.axisLineConfigs?.axisPosition.toXAxis() ?: when {
        yMax <= 0 -> AxisPosition.Top
        yMin < 0 -> AxisPosition.Center
        else -> AxisPosition.Bottom
    }

    val yAxisPosition = scaleY?.axisLineConfigs?.axisPosition.toYAxis() ?: when {
        xMax <= 0 -> AxisPosition.End
        xMin < 0 -> AxisPosition.Center
        else -> AxisPosition.Start
    }

    val drawZero = when {
        yMin == 0f && xMin == 0f &&
                xAxisPosition == AxisPosition.Bottom && yAxisPosition == AxisPosition.Start &&
                scaleX.isNotNull() && scaleY.isNotNull() &&
                scaleX?.labelConfigs?.showLabels == true && scaleY?.labelConfigs?.showLabels == true -> false
        yMax == 0f && xMin == 0f &&
                xAxisPosition == AxisPosition.Top && yAxisPosition == AxisPosition.Start &&
                scaleX.isNotNull() && scaleY.isNotNull() &&
                scaleX?.labelConfigs?.showLabels == true && scaleY?.labelConfigs?.showLabels == true -> false
        yMin == 0f && xMax == 0f &&
                xAxisPosition == AxisPosition.Bottom && yAxisPosition == AxisPosition.End &&
                scaleX.isNotNull() && scaleY.isNotNull() &&
                scaleX?.labelConfigs?.showLabels == true && scaleY?.labelConfigs?.showLabels == true -> false
        yMax == 0f && xMax == 0f &&
                xAxisPosition == AxisPosition.Top && yAxisPosition == AxisPosition.End &&
                scaleX.isNotNull() && scaleY.isNotNull() &&
                scaleX?.labelConfigs?.showLabels == true && scaleY?.labelConfigs?.showLabels == true -> false
        (xLabels.min() != 0f && xLabels.max() != 0f && xLabels.contains(0f)) &&
                (yLabels.min() != 0f && yLabels.max() != 0f && yLabels.contains(0f)) &&
                xAxisPosition == AxisPosition.Center && yAxisPosition == AxisPosition.Center &&
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
                labelConfigs = it.labelConfigs ?: LabelsConfigurationDefaults.labelsConfigurationDefault(),
                guidelinesConfigs = it.guidelinesConfigs ?: GuidelinesConfigurationDefaults.guidelinesConfigurationDefaults(),
                axisLineConfigs = it.axisLineConfigs ?: AxisLineConfiguration.xAxisLineConfigurationDefaults(),
                labels = xLabels,
                xRangeValues = Range(min = xMin, max = xMax),
                xAxisPosition = xAxisPosition,
                yAxisPosition = yAxisPosition,
                drawYAxis = scaleY.isNotNull() && scaleY!!.axisLineConfigs?.showAxisLine ?: true,
                drawZero = drawZero,
                range = xRange,
                textMeasurer = xTextMeasurer
            )
        }
        scaleY?.let {
            unboundYAxis(
                labelConfigs = it.labelConfigs ?: LabelsConfigurationDefaults.labelsConfigurationDefault(),
                guidelinesConfigs = it.guidelinesConfigs ?: GuidelinesConfigurationDefaults.guidelinesConfigurationDefaults(),
                axisLineConfigs = it.axisLineConfigs ?: AxisLineConfiguration.xAxisLineConfigurationDefaults(),
                labels = yLabels,
                yRangeValues = Range(min = yMin, max = yMax),
                yAxisPosition = yAxisPosition,
                xAxisPosition = xAxisPosition,
                drawXAxis = scaleX.isNotNull() && scaleY!!.axisLineConfigs?.showAxisLine ?: true,
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
                labelConfig = scaleY?.labelConfigs ?: LabelsConfigurationDefaults.labelsConfigurationDefault()
            )
        }
    }
}