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
import com.joshrose.plotsforcompose.axis.config.AxisConfiguration
import com.joshrose.plotsforcompose.axis.util.AxisPosition.*
import com.joshrose.plotsforcompose.axis.util.Range
import com.joshrose.plotsforcompose.axis.util.drawZero
import com.joshrose.plotsforcompose.axis.util.floatLabels
import com.joshrose.plotsforcompose.axis.x.continuous.unboundXAxis
import com.joshrose.plotsforcompose.axis.y.continuous.continuousYAxis
import com.joshrose.plotsforcompose.exception.InvalidRangeException
import com.joshrose.plotsforcompose.util.Coordinates

@Suppress("DuplicatedCode")
@ExperimentalTextApi
@Composable
fun AxesCanvas(
    component: AxesComponent,
    xConfig: AxisConfiguration.XConfiguration,
    yConfig: AxisConfiguration.YConfiguration,
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
                data = List(2) {
                    Coordinates(
                        x = (-100..100).random().toFloat(),
                        y = (-100..100).random().toFloat()
                    )
                }
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
                data = List(2) {
                    Coordinates(
                        x = (-100..100).random().toFloat(),
                        y = (-100..100).random().toFloat()
                    )
                }
            )
        }

        val xAxisPosition = xConfig.axisLine.axisPosition ?: when {
            yMax <= 0 -> Top
            yMin < 0 -> Center
            else -> Bottom
        }

        val yAxisPosition = yConfig.axisLine.axisPosition ?: when {
            xMax <= 0 -> End
            xMin < 0 -> Center
            else -> Start
        }

        val drawZero = when {
            yMin == 0f && xMin == 0f &&
                xAxisPosition == Bottom && yAxisPosition == Start &&
                xConfig.showAxis && yConfig.showAxis &&
                xConfig.showLabels && yConfig.showLabels -> false
            yMax == 0f && xMin == 0f &&
                xAxisPosition == Top && yAxisPosition == Start &&
                xConfig.showAxis && yConfig.showAxis &&
                xConfig.showLabels && yConfig.showLabels -> false
            yMin == 0f && xMax == 0f &&
                xAxisPosition == Bottom && yAxisPosition == End &&
                xConfig.showAxis && yConfig.showAxis &&
                xConfig.showLabels && yConfig.showLabels -> false
            yMax == 0f && xMax == 0f &&
                xAxisPosition == Top && yAxisPosition == End &&
                xConfig.showAxis && yConfig.showAxis &&
                xConfig.showLabels && yConfig.showLabels -> false
            (xLabels.min() != 0f && xLabels.max() != 0f && xLabels.contains(0f)) &&
                (yLabels.min() != 0f && yLabels.max() != 0f && yLabels.contains(0f)) &&
                xAxisPosition == Center && yAxisPosition == Center &&
                xConfig.showAxis && yConfig.showAxis &&
                (xConfig.showLabels || yConfig.showLabels) -> false
            else -> true
        }

        Canvas(modifier = modifier) {
            if (xConfig.showAxis) {
                unboundXAxis(
                    config = xConfig,
                    labels = xLabels,
                    xRangeValues = Range(min = xMin, max = xMax),
                    xAxisPosition = xAxisPosition,
                    yAxisPosition = yAxisPosition,
                    drawYAxis = yConfig.showAxis && yConfig.showAxisLine,
                    drawZero = drawZero,
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
                    xAxisPosition = xAxisPosition,
                    drawXAxis = xConfig.showAxis && xConfig.showAxisLine,
                    drawZero = drawZero,
                    range = yRange,
                    textMeasurer = yTextMeasurer
                )
            }

            if (!drawZero) {
                drawZero(
                    xAxisPosition = xAxisPosition,
                    yAxisPosition = yAxisPosition,
                    xAxisOffset = yConfig.labels.axisOffset.toPx(),
                    yAxisOffset = xConfig.labels.axisOffset.toPx(),
                    textMeasurer = zeroTextMeasurer,
                    labelConfig = yConfig.labels
                )
            }
        }
    }
}