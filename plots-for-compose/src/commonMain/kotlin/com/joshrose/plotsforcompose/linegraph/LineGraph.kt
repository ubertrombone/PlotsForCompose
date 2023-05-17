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
import com.joshrose.plotsforcompose.axis.y.continuous.continuousYAxis
import com.joshrose.plotsforcompose.common.maxValue
import com.joshrose.plotsforcompose.common.minValue
import com.joshrose.plotsforcompose.common.range
import com.joshrose.plotsforcompose.exception.InvalidRangeException
import com.joshrose.plotsforcompose.linegraph.config.LineGraphConfig
import com.joshrose.plotsforcompose.linegraph.config.LineGraphConfigDefaults
import com.joshrose.plotsforcompose.linegraph.model.Coordinates
import com.joshrose.plotsforcompose.linegraph.model.Coordinates.Companion.firstXValue
import com.joshrose.plotsforcompose.linegraph.model.Coordinates.Companion.lastXValue
import com.joshrose.plotsforcompose.linegraph.model.Coordinates.Companion.maxYValue
import com.joshrose.plotsforcompose.linegraph.model.Coordinates.Companion.minYValue
import com.joshrose.plotsforcompose.util.LoadingState
import com.joshrose.plotsforcompose.util.LoadingState.*

// TODO: User should be able to only draw a subset of the labels leaving the rest as guidelines, etc.
@OptIn(ExperimentalTextApi::class)
@Composable
fun LineGraph(
    data: List<Coordinates<*>>,
    modifier: Modifier = Modifier,
    xAxisConfig: ContinuousAxisConfig = ContinuousAxisConfigDefaults.continuousAxisConfigDefaults(),
    yAxisConfig: ContinuousAxisConfig = ContinuousAxisConfigDefaults.continuousAxisConfigDefaults(),
    lineGraphConfig: LineGraphConfig = LineGraphConfigDefaults.lineGraphConfigDefaults()
) {
    var state by remember { mutableStateOf<LoadingState>(Success) }
    val yTextMeasurer = rememberTextMeasurer()

    if (data.isEmpty()) state = Empty

    val firstXValue by remember { derivedStateOf { data.firstXValue() } }
    val lastXValue by remember { derivedStateOf { data.lastXValue() } }
    val maxYValue by remember { derivedStateOf { maxValue(data.maxYValue(), yAxisConfig.labels.maxValueAdjustment) } }
    val minYValue by remember { derivedStateOf { minValue(data.minYValue(), maxYValue, yAxisConfig.labels.minValueAdjustment) } }
    val yRange by remember { derivedStateOf { range(minYValue, maxYValue, yAxisConfig.labels.rangeAdjustment) } }

    println("First X: ${firstXValue}, Last X: $lastXValue")

    var yLabels by remember { mutableStateOf(listOf(0f, 100f)) }
    try {
        yLabels = floatLabels(
            breaks = yAxisConfig.labels.breaks,
            minValue = minYValue,
            maxValue = maxYValue
        )
    } catch (e: InvalidRangeException) { state = Error }

    val xAxisPosition = xAxisConfig.axisLine.axisPosition?.toXAxisPosition() ?: when {
        maxYValue <= 0 -> XAxisPosition.TOP
        minYValue < 0 -> XAxisPosition.CENTER
        else -> XAxisPosition.BOTTOM
    }

    val yAxisPosition = yAxisConfig.axisLine.axisPosition?.toYAxisPosition() ?: YAxisPosition.START

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
    }
}