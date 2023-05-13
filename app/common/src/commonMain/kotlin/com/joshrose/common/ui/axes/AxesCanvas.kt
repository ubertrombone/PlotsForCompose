package com.joshrose.common.ui.axes

import androidx.compose.foundation.Canvas
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.rememberTextMeasurer
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.joshrose.common.components.axes.AxesComponent
import com.joshrose.common.components.axes.models.LoadingState
import com.joshrose.plotsforcompose.axis.config.ContinuousAxisConfig
import com.joshrose.plotsforcompose.axis.util.AxisPosition.Companion.toXAxisPosition
import com.joshrose.plotsforcompose.axis.util.AxisPosition.Companion.toYAxisPosition
import com.joshrose.plotsforcompose.axis.util.AxisPosition.XAxisPosition.*
import com.joshrose.plotsforcompose.axis.util.AxisPosition.YAxisPosition
import com.joshrose.plotsforcompose.axis.util.AxisPosition.YAxisPosition.END
import com.joshrose.plotsforcompose.axis.util.Range
import com.joshrose.plotsforcompose.axis.util.drawZero
import com.joshrose.plotsforcompose.axis.util.floatLabels
import com.joshrose.plotsforcompose.axis.x.continuous.continuousXAxis
import com.joshrose.plotsforcompose.axis.y.continuous.continuousYAxis
import com.joshrose.plotsforcompose.exception.InvalidRangeException

@Suppress("DuplicatedCode")
@ExperimentalTextApi
@Composable
fun AxesCanvas(
    component: AxesComponent,
    xConfig: ContinuousAxisConfig,
    yConfig: ContinuousAxisConfig,
    modifier: Modifier = Modifier
) {
    val dataValues by component.dataValueStates.subscribeAsState()
    val loading by component.loadingState.subscribeAsState()
    val xLabelState by component.xLabelsState.subscribeAsState()
    val yLabelState by component.yLabelsState.subscribeAsState()

    LaunchedEffect(dataValues.data, xLabelState, yLabelState) {
        component.calculateData(
            xConfig = xConfig.labels,
            yConfig = yConfig.labels
        )
    }

    val xTextMeasurer = rememberTextMeasurer()
    val yTextMeasurer = rememberTextMeasurer()
    val zeroTextMeasurer = rememberTextMeasurer()

    if (loading == LoadingState.Loading) CircularProgressIndicator(color = colorScheme.primary)
    else {
        val xMax = dataValues.maxXValue ?: 100f
        val yMax = dataValues.maxYValue ?: 100f
        val xMin = dataValues.minXValue ?: 0f
        val yMin = dataValues.minYValue ?: 0f
        val xRange = dataValues.xRange ?: 100f
        val yRange = dataValues.yRange ?: 100f

        var xLabels by remember { mutableStateOf(listOf(0f, 100f)) }
        var yLabels by remember { mutableStateOf(listOf(0f, 100f)) }

        try {
            xLabels = floatLabels(
                breaks = xConfig.labels.breaks,
                minValue = xMin,
                maxValue = xMax
            )
        } catch (e: InvalidRangeException) {
            component.updateData(
                xList = List(2) { (-10_000..10_000).random().toFloat() },
                yList = List(2) { (-10_000..10_000).random().toFloat() }
            )
        }

        try {
            yLabels = floatLabels(
                breaks = yConfig.labels.breaks,
                minValue = yMin,
                maxValue = yMax
            )
        } catch (e: InvalidRangeException) {
            component.updateData(
                xList = List(2) { (-10_000..10_000).random().toFloat() },
                yList = List(2) { (-10_000..10_000).random().toFloat() }
            )
        }

        val xAxisPosition = xConfig.axisLine.axisPosition?.toXAxisPosition() ?: when {
            yMax <= 0 -> TOP
            yMin < 0 -> CENTER
            else -> BOTTOM
        }

        val yAxisPosition = yConfig.axisLine.axisPosition?.toYAxisPosition() ?: when {
            xMax <= 0 -> END
            xMin < 0 -> YAxisPosition.CENTER
            else -> YAxisPosition.START
        }

        Canvas(modifier = modifier) {
            if ((xMax == 0f || xMin == 0f) && (yMax == 0f || yMin == 0f)) {
                println("TRUE")
                drawZero(
                    xAxisPosition = xAxisPosition,
                    yAxisPosition = yAxisPosition,
                    xAxisOffset = yConfig.labels.axisOffset.toPx(),
                    yAxisOffset = xConfig.labels.axisOffset.toPx(),
                    textMeasurer = zeroTextMeasurer,
                    labelConfig = yConfig.labels
                )
            }

            if (xConfig.showAxis) {
                continuousXAxis(
                    config = xConfig,
                    labels = xLabels,
                    xRangeValues = Range(min = xMin, max = xMax),
                    xAxisPosition = xAxisPosition,
                    yRangeValues = Range(min = yMin, max = yMax),
                    yAxisPosition = yAxisPosition,
                    drawYAxis = yConfig.showAxis,
                    range = xRange,
                    textMeasurer = xTextMeasurer
                )
            }

            if (yConfig.showAxis) {
                continuousYAxis(
                    config = yConfig,
                    labels = yLabels,
                    yRangeValues = Range(min = yMin, max = yMax),
                    yAxisPosition = yAxisPosition,
                    xRangeValues = Range(min = xMin, max = xMax),
                    xAxisPosition = xAxisPosition,
                    drawXAxis = xConfig.showAxis,
                    range = yRange,
                    textMeasurer = yTextMeasurer
                )
            }
        }
    }
}