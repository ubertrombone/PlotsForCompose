package com.joshrose.plotsforcompose.linegraph

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.joshrose.plotsforcompose.axis.config.ContinuousAxisConfig
import com.joshrose.plotsforcompose.axis.config.ContinuousAxisConfigDefaults
import com.joshrose.plotsforcompose.axis.util.floatLabels
import com.joshrose.plotsforcompose.common.maxValue
import com.joshrose.plotsforcompose.common.minValue
import com.joshrose.plotsforcompose.common.range
import com.joshrose.plotsforcompose.exception.InvalidRangeException
import com.joshrose.plotsforcompose.linegraph.config.LineGraphConfig
import com.joshrose.plotsforcompose.linegraph.config.LineGraphConfigDefaults
import com.joshrose.plotsforcompose.linegraph.model.*
import com.joshrose.plotsforcompose.util.LoadingState
import com.joshrose.plotsforcompose.util.LoadingState.*

@Composable
fun LineGraph(
    data: List<LineData>,
    modifier: Modifier = Modifier,
    xAxisConfig: ContinuousAxisConfig = ContinuousAxisConfigDefaults.continuousAxisConfigDefaults(),
    yAxisConfig: ContinuousAxisConfig = ContinuousAxisConfigDefaults.continuousAxisConfigDefaults(),
    lineGraphConfig: LineGraphConfig = LineGraphConfigDefaults.lineGraphConfigDefaults()
) {
    var state by remember { mutableStateOf<LoadingState>(Success) }

    if (data.isEmpty()) state = Empty

    val firstXValue by remember { derivedStateOf { data.firstXValue() } }
    val lastXValue by remember { derivedStateOf { data.lastXValue() } }
    val maxYValue by remember { derivedStateOf { maxValue(data.maxYValue(), yAxisConfig.labels.maxValueAdjustment) } }
    val minYValue by remember { derivedStateOf { minValue(data.minYValue(), maxYValue, yAxisConfig.labels.minValueAdjustment) } }
    val yRange by remember { derivedStateOf { range(minYValue, maxYValue, yAxisConfig.labels.rangeAdjustment) } }

    var yLabels by remember { mutableStateOf(listOf(0f, 100f)) }
    try {
        yLabels = floatLabels(
            breaks = yAxisConfig.labels.breaks,
            minValue = minYValue,
            maxValue = maxYValue
        )
    } catch (e: InvalidRangeException) { state = Error }

    Canvas(modifier = modifier) {

    }
}