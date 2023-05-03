@file:Suppress("DuplicatedCode")

package com.joshrose.common.ui.axes

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.unit.dp
import com.joshrose.common.components.axes.AxesComponent
import com.joshrose.common.ui.axes.guidelines.Guidelines
import com.joshrose.common.util.ScrollLazyColumn
import com.joshrose.plotsforcompose.axis.config.ContinuousAxisConfigDefaults
import com.joshrose.plotsforcompose.axis.config.axisline.AxisLineConfigDefaults
import com.joshrose.plotsforcompose.axis.config.guidelines.GuidelinesConfigDefaults
import com.joshrose.plotsforcompose.axis.config.labels.ContinuousLabelsConfigDefaults
import com.joshrose.plotsforcompose.axis.config.util.Multiplier
import com.joshrose.plotsforcompose.util.Coordinates

@OptIn(ExperimentalTextApi::class)
@Composable
fun AxesContent(
    component: AxesComponent,
    modifier: Modifier = Modifier
) {
    // TODO: Move these states to component?
    // TODO: Add a reset function when doing the above
    val xAxisShowStates by component.showXAxisStates.collectAsState()
    val yAxisShowStates by component.showYAxisStates.collectAsState()
    val xGuidelinesStates by component.showXGuidelines.collectAsState()
    val yGuidelinesStates by component.showYGuidelines.collectAsState()

    val xRotation by component.xRotation.collectAsState()
    val yRotation by component.yRotation.collectAsState()

    // TODO: Build out the sample
    val xConfig = ContinuousAxisConfigDefaults.continuousAxisConfigDefaults()
        .copy(
            showAxis = xAxisShowStates.showAxis,
            showLabels = xAxisShowStates.showLabels,
            showAxisLine = xAxisShowStates.showAxisLine,
            showGuidelines = xAxisShowStates.showGuidelines,
            guidelines = GuidelinesConfigDefaults.guidelinesConfigDefaults().copy(
                strokeWidth = xGuidelinesStates.strokeWidth,
                lineColor = colorScheme.onBackground,
                alpha = xGuidelinesStates.alpha,
                padding = xGuidelinesStates.padding
            ),
            labels = ContinuousLabelsConfigDefaults.continuousLabelsConfigDefaults().copy(
                rotation = xRotation,
                axisOffset = 20.dp,
                rangeAdjustment = Multiplier(.1f),
                fontColor = colorScheme.primary
            ),
            axisLine = AxisLineConfigDefaults.axisLineConfigDefaults().copy(
                ticks = true,
                lineColor = colorScheme.primary
            )
        )
    val yConfig = ContinuousAxisConfigDefaults.continuousAxisConfigDefaults()
        .copy(
            showAxis = yAxisShowStates.showAxis,
            showLabels = yAxisShowStates.showLabels,
            showAxisLine = yAxisShowStates.showAxisLine,
            showGuidelines = yAxisShowStates.showGuidelines,
            guidelines = GuidelinesConfigDefaults.guidelinesConfigDefaults().copy(
                strokeWidth = yGuidelinesStates.strokeWidth,
                lineColor = colorScheme.onBackground,
                alpha = yGuidelinesStates.alpha,
                padding = yGuidelinesStates.padding
            ),
            labels = ContinuousLabelsConfigDefaults.continuousLabelsConfigDefaults().copy(
                rotation = yRotation,
                axisOffset = 20.dp,
                rangeAdjustment = Multiplier(.1f),
                fontColor = colorScheme.primary
            ),
            axisLine = AxisLineConfigDefaults.axisLineConfigDefaults().copy(
                ticks = true,
                lineColor = colorScheme.primary
            )
        )

    // TODO: Force set axis min and max -- For later

    var data by remember {
        mutableStateOf(
            listOf(Coordinates(0f, 100f), Coordinates(2000f, 2000f), Coordinates(3000f, 3000f))
        )
    }

    ScrollLazyColumn(modifier = modifier.fillMaxSize().padding(horizontal = 20.dp, vertical = 10.dp)) {
        item {
            Button(
                onClick = {
                    data = List(2) {
                        Coordinates(
                            x = (-10_000..10_000).random().toFloat(),
                            y = (-10_000..10_000).random().toFloat()
                        )
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorScheme.primaryContainer,
                    contentColor = colorScheme.onPrimaryContainer
                )
            ) {
                Text(
                    text = "Generate New Axes",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(Modifier.height(20.dp))
        }
        item {
            AxisChipRow(
                label = "X-Axis:",
                axisSelected = xAxisShowStates.showAxis,
                axisLineSelected = xAxisShowStates.showAxisLine,
                guidelinesSelected = xAxisShowStates.showGuidelines,
                labelsSelected = xAxisShowStates.showLabels,
                axisOnClick = component::updateShowXAxis,
                axisLineOnClick = component::updateShowXAxisLine,
                guidelinesOnClick = component::updateShowXGuidelines,
                labelsOnClick = component::updateShowXLabels,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(10.dp))
        }
        item {
            AxisChipRow(
                label = "Y-Axis:",
                axisSelected = yAxisShowStates.showAxis,
                axisLineSelected = yAxisShowStates.showAxisLine,
                guidelinesSelected = yAxisShowStates.showGuidelines,
                labelsSelected = yAxisShowStates.showLabels,
                axisOnClick = component::updateShowYAxis,
                axisLineOnClick = component::updateShowYAxisLine,
                guidelinesOnClick = component::updateShowYGuidelines,
                labelsOnClick = component::updateShowYLabels,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(10.dp))
        }
        item {
            AxesCanvas(
                component = component,
                xConfig = xConfig,
                yConfig = yConfig,
                data = data,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(50.dp)
            )
        }
        item {
            Guidelines(
                component = component,
                modifier = Modifier.fillMaxWidth().padding(10.dp)
            )
        }
        item {
            Spacer(Modifier.height(50.dp))

            Text("X Rotation: $xRotation")
            Spacer(modifier.height(2.5.dp))
            Slider(
                value = xRotation,
                onValueChange = component::updateXRotation,
                valueRange = -90f..90f
            )
        }

        item {
            Spacer(Modifier.height(50.dp))

            Text("Y Rotation: $yRotation")
            Spacer(modifier.height(2.5.dp))
            Slider(
                value = yRotation,
                onValueChange = component::updateYRotation,
                valueRange = -90f..90f
            )
        }
    }
}