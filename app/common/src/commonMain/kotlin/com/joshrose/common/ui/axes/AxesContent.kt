@file:Suppress("DuplicatedCode")

package com.joshrose.common.ui.axes

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
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
import com.joshrose.plotsforcompose.axis.continuous.continuousXAxis
import com.joshrose.plotsforcompose.axis.continuous.continuousYAxis
import com.joshrose.plotsforcompose.axis.util.floatLabels
import com.joshrose.plotsforcompose.axis.util.xPositions
import com.joshrose.plotsforcompose.axis.util.yPositions
import kotlin.math.abs

@OptIn(ExperimentalTextApi::class)
@Composable
fun AxesContent(
    component: AxesComponent,
    modifier: Modifier = Modifier
) {
    var xRotation by remember { mutableStateOf(0.dp) }
    var yRotation by remember { mutableStateOf(0.dp) }

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
                yOffset = 15.dp
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
                xOffset = 15.dp
            )
        )

    val data = mapOf(1000f to 1000f, 2000f to 2000f, 3000f to 3000f)

    val xMax = data.maxOf { it.key }
    val xMaxAdjusted = xMax.plus(xMax.times(xConfig.labels.maxValueAdjustment.factor))
    val xMin = data.minOf { it.key }
    val xMinAdjusted = xMin.minus(abs(xMin.times(xConfig.labels.minValueAdjustment.factor)))
    val xMinFinal = if (xMin < 0) xMaxAdjusted.times(-1) else xMinAdjusted
    val yMax = data.maxOf { it.value }
    val yMaxAdjusted = yMax.plus(yMax.times(yConfig.labels.maxValueAdjustment.factor))
    val yMin = data.minOf { it.value }
    val yMinAdjusted = yMin.minus(abs(yMin.times(yConfig.labels.minValueAdjustment.factor)))
    val yMinFinal = if (yMin < 0) yMaxAdjusted.times(-1) else yMinAdjusted
    val xRange = xMaxAdjusted.minus(xMinFinal)
    val xRangeAdjusted = xRange.plus(xRange.times(xConfig.labels.rangeAdjustment.factor))
    val yRange = yMaxAdjusted.minus(yMinFinal)
    val yRangeAdjusted = yRange.plus(yRange.times(yConfig.labels.rangeAdjustment.factor))

    val xLabels = floatLabels(
        breaks = xConfig.labels.breaks,
        minValue = xMinFinal,
        maxValue = xMaxAdjusted
    )

    val yLabels = floatLabels(
        breaks = yConfig.labels.breaks,
        minValue = yMinFinal,
        maxValue = yMaxAdjusted
    ).reversed()

    ScrollLazyColumn(modifier = modifier.fillMaxSize().padding(20.dp)) {
        item {
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(10.dp)
            ) {
                val yPositions = yPositions(
                    height = size.height,
                    yMax = yMaxAdjusted,
                    yMin = yMinFinal,
                    yOffset = xConfig.labels.yOffset.toPx()
                )

                val xPositions = xPositions(
                    width = size.width,
                    xMax = xMaxAdjusted,
                    xMin = xMinFinal,
                    xOffset = yConfig.labels.xOffset.toPx()
                )

                continuousXAxis(
                    config = xConfig,
                    labels = xLabels,
                    yPositions = yPositions,
                    maxXValue = xMaxAdjusted,
                    maxYValue = yMaxAdjusted,
                    range = xRangeAdjusted,
                    textMeasurer = xTextMeasurer
                )
                continuousYAxis(
                    config = yConfig,
                    labels = yLabels,
                    xPositions = xPositions,
                    maxYValue = yMaxAdjusted,
                    maxXValue = xMaxAdjusted,
                    range = yRangeAdjusted,
                    textMeasurer = yTextMeasurer
                )
            }
        }
        item {
            Spacer(Modifier.height(50.dp))

            Text("X Rotation: ${xRotation.value}")
            Spacer(modifier.height(2.5.dp))
            Slider(
                value = xRotation.value,
                onValueChange = { xRotation = it.dp },
                valueRange = 0f..90f
            )
        }

        item {
            Spacer(Modifier.height(50.dp))

            Text("Y Rotation: ${yRotation.value}")
            Spacer(modifier.height(2.5.dp))
            Slider(
                value = yRotation.value,
                onValueChange = { yRotation = it.dp },
                valueRange = -90f..0f
            )
        }
    }
}