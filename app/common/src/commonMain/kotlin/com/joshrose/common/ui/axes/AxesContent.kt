@file:Suppress("DuplicatedCode")

package com.joshrose.common.ui.axes

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import com.joshrose.common.components.axes.AxesComponent
import com.joshrose.common.util.ScrollLazyColumn
import com.joshrose.plotsforcompose.axis.config.ContinuousAxisConfigDefaults
import com.joshrose.plotsforcompose.axis.config.guidelines.GuidelinesConfigDefaults
import com.joshrose.plotsforcompose.axis.config.labels.ContinuousLabelsConfigDefaults
import com.joshrose.plotsforcompose.axis.config.util.Multiplier
import com.joshrose.plotsforcompose.axis.util.AxisPosition.Companion.toXAxisPosition
import com.joshrose.plotsforcompose.axis.util.AxisPosition.Companion.toYAxisPosition
import com.joshrose.plotsforcompose.axis.util.Range
import com.joshrose.plotsforcompose.axis.util.XAxisPosition
import com.joshrose.plotsforcompose.axis.util.YAxisPosition
import com.joshrose.plotsforcompose.axis.util.floatLabels
import com.joshrose.plotsforcompose.axis.x.continuous.continuousXAxis
import com.joshrose.plotsforcompose.axis.y.continuous.continuousYAxis
import kotlin.math.abs

@OptIn(ExperimentalTextApi::class)
@Composable
fun AxesContent(
    component: AxesComponent,
    modifier: Modifier = Modifier
) {
    var xRotation by remember { mutableStateOf(0f) }
    var yRotation by remember { mutableStateOf(0f) }

    val xTextMeasurer = rememberTextMeasurer()
    val yTextMeasurer = rememberTextMeasurer()

    // TODO: Break down configs. I think that's more user friendly
    val xConfig = ContinuousAxisConfigDefaults.continuousAxisConfigDefaults()
        .copy(
            showGuidelines = true,
            guidelines = GuidelinesConfigDefaults.guidelinesConfigDefaults().copy(
                strokeWidth = 1.dp,
                lineColor = MaterialTheme.colorScheme.onBackground
            ),
            labels = ContinuousLabelsConfigDefaults.continuousLabelsConfigDefaults().copy(
                rotation = xRotation,
                yOffset = 15.dp,
                rangeAdjustment = Multiplier(.1f)
            )
        )
    val yConfig = ContinuousAxisConfigDefaults.continuousAxisConfigDefaults()
        .copy(
            showGuidelines = true,
            guidelines = GuidelinesConfigDefaults.guidelinesConfigDefaults().copy(
                strokeWidth = 1.dp,
                lineColor = MaterialTheme.colorScheme.onBackground
            ),
            labels = ContinuousLabelsConfigDefaults.continuousLabelsConfigDefaults().copy(
                rotation = yRotation,
                xOffset = 25.dp,
                rangeAdjustment = Multiplier(.1f)
            )
        )

    // TODO: Force set axis min and max

    var xData by remember { mutableStateOf(listOf(0f, 2000f, 3000f)) }
    var yData by remember { mutableStateOf(listOf(100f, 2000f, 3000f)) }

    val xMax = xData.max()
    val xMaxAdjusted = xMax.plus(xMax.times(xConfig.labels.maxValueAdjustment.factor))
    val xMin = xData.min()
    val xMinAdjusted = xMin.minus(abs(xMin.times(xConfig.labels.minValueAdjustment.factor)))
    val xMinFinal = if (xMin < 0 && xMaxAdjusted > 0) xMaxAdjusted.times(-1) else xMinAdjusted
    val yMax = yData.max()
    val yMaxAdjusted = yMax.plus(yMax.times(yConfig.labels.maxValueAdjustment.factor))
    val yMin = yData.min()
    val yMinAdjusted = yMin.minus(abs(yMin.times(yConfig.labels.minValueAdjustment.factor)))
    val yMinFinal = if (yMin < 0 && yMaxAdjusted > 0) yMaxAdjusted.times(-1) else yMinAdjusted
    val xRange = xMaxAdjusted.minus(xMinFinal)
    val xRangeAdjusted = when {
        xMinFinal <= 0 && xMaxAdjusted >= 0 -> xRange
        xMinFinal == 0f || xMaxAdjusted == 0f -> xRange
        else -> xRange.plus(xRange.times(xConfig.labels.rangeAdjustment.factor))
    }
    val yRange = yMaxAdjusted.minus(yMinFinal)
    val yRangeAdjusted = when {
        yMinFinal <= 0 && yMaxAdjusted >= 0 -> yRange
        yMinFinal == 0f || yMaxAdjusted == 0f -> yRange
        else -> yRange.plus(yRange.times(yConfig.labels.rangeAdjustment.factor))
    }

    val xLabels = floatLabels(
        breaks = xConfig.labels.breaks,
        minValue = xMinFinal,
        maxValue = xMaxAdjusted
    )

    val yLabels = floatLabels(
        breaks = yConfig.labels.breaks,
        minValue = yMinFinal,
        maxValue = yMaxAdjusted
    )

    ScrollLazyColumn(modifier = modifier.fillMaxSize().padding(20.dp)) {
        item {
            Button(
                onClick = {
                    xData = (1..2).map { (-10_000..10_000).random().toFloat() }
                    yData = (1..2).map { (-10_000..10_000).random().toFloat() }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = "Generate New Axes",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        item {
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(30.dp)
            ) {
                val xAxisPosition = xConfig.axisLine.axisPosition?.toXAxisPosition() ?: when {
                    yMaxAdjusted <= 0 -> XAxisPosition.TOP
                    yMinFinal < 0 -> XAxisPosition.CENTER
                    else -> XAxisPosition.BOTTOM
                }

                val yAxisPosition = yConfig.axisLine.axisPosition?.toYAxisPosition() ?: when {
                    xMaxAdjusted <= 0 -> YAxisPosition.END
                    xMinFinal < 0 -> YAxisPosition.CENTER
                    else -> YAxisPosition.START
                }

                continuousXAxis(
                    config = xConfig,
                    labels = xLabels,
                    xRangeValues = Range(min = xMinFinal, max = xMaxAdjusted),
                    xAxisPosition = xAxisPosition,
                    yRangeValues = Range(min = yMinFinal, max = yMaxAdjusted),
                    yAxisPosition = yAxisPosition,
                    range = xRangeAdjusted,
                    textMeasurer = xTextMeasurer
                )

                continuousYAxis(
                    config = yConfig,
                    labels = yLabels,
                    yRangeValues = Range(min = yMinFinal, max = yMaxAdjusted),
                    yAxisPosition = yAxisPosition,
                    xRangeValues = Range(min = xMinFinal, max = xMaxAdjusted),
                    xAxisPosition = xAxisPosition,
                    range = yRangeAdjusted,
                    textMeasurer = yTextMeasurer
                )
            }
        }
        item {
            Spacer(Modifier.height(50.dp))

            Text("X Rotation: $xRotation")
            Spacer(modifier.height(2.5.dp))
            Slider(
                value = xRotation,
                onValueChange = { xRotation = it },
                valueRange = -90f..90f
            )
        }

        item {
            Spacer(Modifier.height(50.dp))

            Text("Y Rotation: $yRotation")
            Spacer(modifier.height(2.5.dp))
            Slider(
                value = yRotation,
                onValueChange = { yRotation = it },
                valueRange = -90f..90f
            )
        }
    }
}