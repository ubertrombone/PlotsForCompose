@file:Suppress("DuplicatedCode")

package com.joshrose.common.ui.axes

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    val xTextMeasurer = rememberTextMeasurer()
    val yTextMeasurer = rememberTextMeasurer()

    val xConfig = ContinuousAxisConfigDefaults.continuousAxisConfigDefaults()
        .copy(
            showGuidelines = true,
            guidelines = GuidelinesConfigDefaults.guidelinesConfigDefaults().copy(
                lineColor = Color.White,
                strokeWidth = 5.dp
            )
        )
    val yConfig = ContinuousAxisConfigDefaults.continuousAxisConfigDefaults()
        .copy(
            showGuidelines = true,
            guidelines = GuidelinesConfigDefaults.guidelinesConfigDefaults().copy(
                lineColor = Color.Black,
                strokeWidth = 5.dp
            ),
            labels = ContinuousLabelsConfigDefaults.continuousLabelsConfigDefaults().copy(
                rotation = (-45).dp
            )
        )

    val data = mapOf(1f to 1000f, 2f to 2000f, 3f to 3000f)

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

    ScrollLazyColumn(modifier = modifier.fillMaxSize().background(Color.Cyan).padding(20.dp)) {
        item {
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .height(300.dp)
            ) {
                println("SIZE: $size")

                val yPositions = yPositions(
                    height = size.height,
                    yMax = yMaxAdjusted,
                    yMin = yMinFinal,
                    yOffset = xConfig.labels.yOffset.toPx()
                )
                println("YPOS: $yPositions")
                val xPositions = xPositions(
                    width = size.width,
                    xMax = xMaxAdjusted,
                    xMin = xMinFinal,
                    xOffset = yConfig.labels.xOffset.toPx()
                )
                println("XPOS: $xPositions")


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
    }
}