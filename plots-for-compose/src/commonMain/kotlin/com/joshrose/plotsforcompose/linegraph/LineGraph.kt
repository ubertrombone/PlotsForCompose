@file:OptIn(ExperimentalTextApi::class)

package com.joshrose.plotsforcompose.linegraph

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.rememberTextMeasurer
import com.joshrose.plotsforcompose.axis.config.ContinuousAxisConfig
import com.joshrose.plotsforcompose.axis.config.ContinuousAxisConfigDefaults
import com.joshrose.plotsforcompose.axis.util.AxisPosition.Companion.toXAxisPosition
import com.joshrose.plotsforcompose.axis.util.AxisPosition.Companion.toYAxisPosition
import com.joshrose.plotsforcompose.axis.util.AxisPosition.XAxisPosition
import com.joshrose.plotsforcompose.axis.util.AxisPosition.YAxisPosition
import com.joshrose.plotsforcompose.axis.util.Range
import com.joshrose.plotsforcompose.axis.util.floatLabels
import com.joshrose.plotsforcompose.axis.x.continuous.zeroBoundXAxis
import com.joshrose.plotsforcompose.axis.y.continuous.continuousYAxis
import com.joshrose.plotsforcompose.common.maxValue
import com.joshrose.plotsforcompose.common.minValue
import com.joshrose.plotsforcompose.common.range
import com.joshrose.plotsforcompose.exception.InvalidRangeException
import com.joshrose.plotsforcompose.linegraph.config.LineGraphConfig
import com.joshrose.plotsforcompose.linegraph.config.LineGraphConfigDefaults
import com.joshrose.plotsforcompose.linegraph.model.*
import com.joshrose.plotsforcompose.util.LoadingState
import com.joshrose.plotsforcompose.util.LoadingState.*

// TODO: User should be able to only draw a subset of the labels leaving the rest as guidelines, etc.
// TODO: How does adding drawscope functions directly into the function work? Will it draw normally?

@Composable
fun LineGraph(
    data: List<StringData>,
    modifier: Modifier = Modifier,
    xAxisConfig: ContinuousAxisConfig = ContinuousAxisConfigDefaults.continuousAxisConfigDefaults(),
    yAxisConfig: ContinuousAxisConfig = ContinuousAxisConfigDefaults.continuousAxisConfigDefaults(),
    lineGraphConfig: LineGraphConfig = LineGraphConfigDefaults.lineGraphConfigDefaults(),
) {

}

@Composable
fun LineGraph(
    data: List<NumberData>,
    modifier: Modifier = Modifier,
    xAxisConfig: ContinuousAxisConfig = ContinuousAxisConfigDefaults.continuousAxisConfigDefaults(),
    yAxisConfig: ContinuousAxisConfig = ContinuousAxisConfigDefaults.continuousAxisConfigDefaults(),
    lineGraphConfig: LineGraphConfig = LineGraphConfigDefaults.lineGraphConfigDefaults(),
) {
    var state by remember { mutableStateOf<LoadingState>(Success) }
    val yTextMeasurer = rememberTextMeasurer()
    val xTextMeasurer = rememberTextMeasurer()

    if (data.isEmpty()) state = Empty

    val minXValue by remember { derivedStateOf { data.minXValue() } }
    val maxXValue by remember { derivedStateOf { data.maxXValue() } }
    val maxYValue by remember { derivedStateOf { maxValue(data.maxYValue(), yAxisConfig.labels.maxValueAdjustment) } }
    val minYValue by remember { derivedStateOf { minValue(data.minYValue(), maxYValue, yAxisConfig.labels.minValueAdjustment) } }
    val yRange by remember { derivedStateOf { range(minYValue, maxYValue, yAxisConfig.labels.rangeAdjustment) } }

    println("First X: ${minXValue}, Last X: $maxXValue")

    var yLabels by remember { mutableStateOf(listOf(0f, 100f)) }
    try {
        yLabels = floatLabels(
            breaks = yAxisConfig.labels.breaks,
            minValue = minYValue,
            maxValue = maxYValue
        )
    } catch (e: InvalidRangeException) { state = Error }

    val xLabels by remember { mutableStateOf(data.map { it.x }) }

    val xAxisPosition = xAxisConfig.axisLine.axisPosition?.toXAxisPosition() ?: when {
        maxYValue <= 0 -> XAxisPosition.TOP
        minYValue < 0 -> XAxisPosition.CENTER
        else -> XAxisPosition.BOTTOM
    }

    // TODO: When number and axisPosition is null, calculate
    val yAxisPosition = yAxisConfig.axisLine.axisPosition?.toYAxisPosition() ?: YAxisPosition.START

    // TODO: Prepare all of the new x and y axes.
    Canvas(modifier = modifier) {
        if (yAxisConfig.showAxis) {
            continuousYAxis(
                config = yAxisConfig,
                labels = yLabels,
                range = yRange,
                yRangeValues = Range(min = minYValue, max = maxYValue),
                yAxisPosition = yAxisPosition,
                xAxisPosition = xAxisPosition,
                drawXAxis = false,
                drawZero = true,
                textMeasurer = yTextMeasurer
            )
        }

        if (xAxisConfig.showAxis) {
            zeroBoundXAxis(
                config = xAxisConfig,
                totalXValues = xLabels.size,
                labels = xLabels,
                xAxisPosition = xAxisPosition,
                yAxisPosition = yAxisPosition,
                drawYAxis = yAxisConfig.showAxis && yAxisConfig.showAxisLine,
                textMeasurer = xTextMeasurer
            )
        }
    }
}