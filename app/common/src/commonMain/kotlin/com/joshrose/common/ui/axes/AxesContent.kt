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
import com.joshrose.plotsforcompose.axis.util.AxisPosition.*
import com.joshrose.plotsforcompose.axis.util.Range
import com.joshrose.plotsforcompose.axis.util.floatLabels
import com.joshrose.plotsforcompose.axis.x.continuous.continuousXAxis
import com.joshrose.plotsforcompose.axis.x.util.yAxisPosition
import com.joshrose.plotsforcompose.axis.y.continuous.continuousYAxis
import com.joshrose.plotsforcompose.axis.y.util.xAxisPosition
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

    // TODO: Make Force Axis Placement default and check placement in Canvas
    // TODO: Make Axis placement a nullable config property
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
                val xAxisPosition = xConfig.axisLine.axisPosition?.let {
                    xAxisPosition(
                        height = size.height,
                        yOffset = xConfig.labels.yOffset.toPx(),
                        axisPos = it
                    )
                } ?: xAxisPosition(
                    height = size.height,
                    yMax = yMaxAdjusted,
                    yMin = yMinFinal,
                    yOffset = xConfig.labels.yOffset.toPx()
                )

                val yAxisPosition = yConfig.axisLine.axisPosition?.let {
                    yAxisPosition(
                        width = size.width,
                        xOffset = xConfig.labels.xOffset.toPx(),
                        axisPos = it
                    )
                } ?: yAxisPosition(
                    width = size.width,
                    xMax = xMaxAdjusted,
                    xMin = xMinFinal,
                    xOffset = yConfig.labels.xOffset.toPx()
                )

                // TODO: resolve the purpose of axisPosition
                // TODO: Remove original overload functions
                continuousXAxis(
                    config = xConfig,
                    labels = xLabels,
                    xRangeValues = Range(min = xMinFinal, max = xMaxAdjusted),
                    yAxisPosition = yAxisPosition,
                    yRangeValues = Range(min = yMinFinal, max = yMaxAdjusted),
                    xAxisPosition = xAxisPosition,
                    range = xRangeAdjusted,
                    textMeasurer = xTextMeasurer,
                    axisPosition = when {
                        yMaxAdjusted <= 0 -> TOP_START
                        yMinFinal < 0 -> CENTER
                        else -> BOTTOM_END
                    }
                )

                continuousYAxis(
                    config = yConfig,
                    labels = yLabels,
                    yRangeValues = Range(min = yMinFinal, max = yMaxAdjusted),
                    xAxisPosition = xAxisPosition,
                    xRangeValues = Range(min = xMinFinal, max = xMaxAdjusted),
                    yAxisPosition = yAxisPosition,
                    range = yRangeAdjusted,
                    textMeasurer = yTextMeasurer,
                    axisPosition = when {
                        xMaxAdjusted <= 0 -> BOTTOM_END
                        xMinFinal < 0 -> CENTER
                        else -> TOP_START
                    }
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