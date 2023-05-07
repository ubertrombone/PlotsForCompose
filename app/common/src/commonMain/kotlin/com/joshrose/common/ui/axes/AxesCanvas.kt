package com.joshrose.common.ui.axes

import androidx.compose.foundation.Canvas
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.rememberTextMeasurer
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.joshrose.common.components.axes.DefaultAxesComponent
import com.joshrose.common.components.axes.models.LoadingState
import com.joshrose.plotsforcompose.axis.config.ContinuousAxisConfig
import com.joshrose.plotsforcompose.axis.util.AxisPosition.Companion.toXAxisPosition
import com.joshrose.plotsforcompose.axis.util.AxisPosition.Companion.toYAxisPosition
import com.joshrose.plotsforcompose.axis.util.Range
import com.joshrose.plotsforcompose.axis.util.XAxisPosition
import com.joshrose.plotsforcompose.axis.util.YAxisPosition
import com.joshrose.plotsforcompose.axis.util.floatLabels
import com.joshrose.plotsforcompose.axis.x.continuous.continuousXAxis
import com.joshrose.plotsforcompose.axis.y.continuous.continuousYAxis

@ExperimentalTextApi
@Composable
fun AxesCanvas(
    component: DefaultAxesComponent,
    xConfig: ContinuousAxisConfig,
    yConfig: ContinuousAxisConfig,
    modifier: Modifier = Modifier
) {
    val dataValues by component.dataValueStates.subscribeAsState()
    val loading by component.loadingState.subscribeAsState()

    LaunchedEffect(dataValues.data) {
        component.calculateData(
            xConfig = xConfig.labels,
            yConfig = yConfig.labels
        )
    }

    val xTextMeasurer = rememberTextMeasurer()
    val yTextMeasurer = rememberTextMeasurer()

    if (loading == LoadingState.Loading) CircularProgressIndicator(color = colorScheme.primary)
    else {
        val xMax = dataValues.maxXValue ?: 100f
        val yMax = dataValues.maxYValue ?: 100f
        val xMin = dataValues.minXValue ?: 0f
        val yMin = dataValues.minYValue ?: 0f
        val xRange = dataValues.xRange ?: 100f
        val yRange = dataValues.yRange ?: 100f

        // TODO: Test what happens when min and max are equal -- Nothing -- prevent this!
        val xLabels = floatLabels(
            breaks = xConfig.labels.breaks,
            minValue = xMin,
            maxValue = xMax
        )

        val yLabels = floatLabels(
            breaks = yConfig.labels.breaks,
            minValue = yMin,
            maxValue = yMax
        )

        val xAxisPosition = xConfig.axisLine.axisPosition?.toXAxisPosition() ?: when {
            yMax <= 0 -> XAxisPosition.TOP
            yMin < 0 -> XAxisPosition.CENTER
            else -> XAxisPosition.BOTTOM
        }

        val yAxisPosition = yConfig.axisLine.axisPosition?.toYAxisPosition() ?: when {
            xMax <= 0 -> YAxisPosition.END
            xMin < 0 -> YAxisPosition.CENTER
            else -> YAxisPosition.START
        }

        Canvas(modifier = modifier) {
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