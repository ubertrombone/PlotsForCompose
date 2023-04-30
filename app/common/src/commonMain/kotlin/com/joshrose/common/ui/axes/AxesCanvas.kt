package com.joshrose.common.ui.axes

import androidx.compose.foundation.Canvas
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.rememberTextMeasurer
import com.joshrose.common.components.axes.AxesComponent
import com.joshrose.plotsforcompose.axis.config.ContinuousAxisConfig
import com.joshrose.plotsforcompose.axis.util.AxisPosition.Companion.toXAxisPosition
import com.joshrose.plotsforcompose.axis.util.AxisPosition.Companion.toYAxisPosition
import com.joshrose.plotsforcompose.axis.util.Range
import com.joshrose.plotsforcompose.axis.util.XAxisPosition
import com.joshrose.plotsforcompose.axis.util.YAxisPosition
import com.joshrose.plotsforcompose.axis.util.floatLabels
import com.joshrose.plotsforcompose.axis.x.continuous.continuousXAxis
import com.joshrose.plotsforcompose.axis.y.continuous.continuousYAxis
import com.joshrose.plotsforcompose.util.Coordinates
import com.joshrose.plotsforcompose.util.LoadingState.LOADING

@ExperimentalTextApi
@Composable
fun AxesCanvas(
    component: AxesComponent,
    xConfig: ContinuousAxisConfig,
    yConfig: ContinuousAxisConfig,
    data: List<Coordinates>,
    modifier: Modifier = Modifier
) {
    val xMaxValue by component.maxXValue.collectAsState()
    val yMaxValue by component.maxYValue.collectAsState()
    val xMinValue by component.minXValue.collectAsState()
    val yMinValue by component.minYValue.collectAsState()
    val xRangeValue by component.xRange.collectAsState()
    val yRangeValue by component.yRange.collectAsState()
    val loading by component.loading.collectAsState()

    LaunchedEffect(data) {
        component.calculateData(
            xConfig = xConfig.labels,
            yConfig = yConfig.labels,
            data = data
        )
    }

    val xTextMeasurer = rememberTextMeasurer()
    val yTextMeasurer = rememberTextMeasurer()

    if (loading == LOADING) CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
    else {
        val xMax = xMaxValue ?: 100f
        val yMax = yMaxValue ?: 100f
        val xMin = xMinValue ?: 0f
        val yMin = yMinValue ?: 0f
        val xRange = xRangeValue ?: 100f
        val yRange = yRangeValue ?: 100f

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
            continuousXAxis(
                config = xConfig,
                labels = xLabels,
                xRangeValues = Range(min = xMin, max = xMax),
                xAxisPosition = xAxisPosition,
                yRangeValues = Range(min = yMin, max = yMax),
                yAxisPosition = yAxisPosition,
                range = xRange,
                textMeasurer = xTextMeasurer
            )

            continuousYAxis(
                config = yConfig,
                labels = yLabels,
                yRangeValues = Range(min = yMin, max = yMax),
                yAxisPosition = yAxisPosition,
                xRangeValues = Range(min = xMin, max = xMax),
                xAxisPosition = xAxisPosition,
                range = yRange,
                textMeasurer = yTextMeasurer
            )
        }
    }
}