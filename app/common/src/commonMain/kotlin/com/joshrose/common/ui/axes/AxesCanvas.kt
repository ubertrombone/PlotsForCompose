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
import com.joshrose.plotsforcompose.axis.util.AxisPosition.YAxisPosition.START
import com.joshrose.plotsforcompose.axis.util.Range
import com.joshrose.plotsforcompose.axis.util.drawZero
import com.joshrose.plotsforcompose.axis.util.floatLabels
import com.joshrose.plotsforcompose.axis.x.continuous.continuousXAxis
import com.joshrose.plotsforcompose.axis.y.continuous.continuousYAxis
import com.joshrose.plotsforcompose.exception.InvalidRangeException
import com.joshrose.plotsforcompose.util.Coordinates

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

        val xAxisPosition = xConfig.axisLine.axisPosition?.toXAxisPosition() ?: when {
            yMax <= 0 -> TOP
            yMin < 0 -> CENTER
            else -> BOTTOM
        }

        val yAxisPosition = yConfig.axisLine.axisPosition?.toYAxisPosition() ?: when {
            xMax <= 0 -> END
            xMin < 0 -> YAxisPosition.CENTER
            else -> START
        }

        val drawXZero = when {
            (yMax == 0f || yMin == 0f) && xMin == 0f && yConfig.axisLine.axisPosition == END -> true
            (yMax == 0f || yMin == 0f) && xMin == 0f && yConfig.axisLine.axisPosition == YAxisPosition.CENTER -> true
            (yMax == 0f || yMin == 0f) && xMax == 0f && yConfig.axisLine.axisPosition == START -> true
            (yMax == 0f || yMin == 0f) && xMax == 0f && yConfig.axisLine.axisPosition == YAxisPosition.CENTER -> true
            yMin == 0f && (xMin == 0f || xMax == 0f) && xConfig.axisLine.axisPosition == TOP -> true
            yMax == 0f && (xMin == 0f || xMax == 0f) && xConfig.axisLine.axisPosition == BOTTOM -> true
            (yMax == 0f || yMin == 0f) && (xMax == 0f || xMin == 0f) && !yConfig.showAxis -> true
            else -> false
        }

        val drawYZero = when {
            (xMin == 0f || xMax == 0f) && yMin == 0f && xConfig.axisLine.axisPosition == TOP -> true
            (xMin == 0f || xMax == 0f) && yMin == 0f && xConfig.axisLine.axisPosition == CENTER -> true
            (xMin == 0f || xMax == 0f) && yMax == 0f && xConfig.axisLine.axisPosition == BOTTOM -> true
            (xMin == 0f || xMax == 0f) && yMax == 0f && xConfig.axisLine.axisPosition == CENTER -> true
            xMin == 0f && (yMin == 0f || yMax == 0f) && yConfig.axisLine.axisPosition == END -> true
            xMax == 0f && (yMin == 0f || yMax == 0f) && yConfig.axisLine.axisPosition == START -> true
            (xMax == 0f || xMin == 0f) && (yMax == 0f || yMin == 0f) && !xConfig.showAxis -> true
            else -> false
        }

        Canvas(modifier = modifier) {
            if (xConfig.showAxis) {
                continuousXAxis(
                    config = xConfig,
                    labels = xLabels,
                    xRangeValues = Range(min = xMin, max = xMax),
                    xAxisPosition = xAxisPosition,
                    yAxisPosition = yAxisPosition,
                    drawYAxis = yConfig.showAxis && yConfig.showAxisLine,
                    drawZero = drawXZero,
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
                    drawZero = drawYZero,
                    range = yRange,
                    textMeasurer = yTextMeasurer
                )
            }

            if (!drawXZero && !drawYZero) {
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