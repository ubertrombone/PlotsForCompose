@file:Suppress("DuplicatedCode")

package com.joshrose.common.ui.axes

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.unit.dp
import com.joshrose.common.components.axes.AxesComponent
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
    var showXAxis by remember { mutableStateOf(true) }
    var showYAxis by remember { mutableStateOf(true) }
    var showXAxisLine by remember { mutableStateOf(true) }
    var showYAxisLine by remember { mutableStateOf(true) }
    var showXGuidelines by remember { mutableStateOf(true) }
    var showYGuidelines by remember { mutableStateOf(true) }
    var showXLabels by remember { mutableStateOf(true) }
    var showYLabels by remember { mutableStateOf(true) }

    var xRotation by remember { mutableStateOf(0f) }
    var yRotation by remember { mutableStateOf(0f) }

    // TODO: Build out the sample
    val xConfig = ContinuousAxisConfigDefaults.continuousAxisConfigDefaults()
        .copy(
            showAxis = showXAxis,
            showLabels = showXLabels,
            showAxisLine = showXAxisLine,
            showGuidelines = showXGuidelines,
            guidelines = GuidelinesConfigDefaults.guidelinesConfigDefaults().copy(
                strokeWidth = 1.dp,
                lineColor = MaterialTheme.colorScheme.onBackground,
                alpha = Multiplier(.5f),
                padding = 0.dp
            ),
            labels = ContinuousLabelsConfigDefaults.continuousLabelsConfigDefaults().copy(
                rotation = xRotation,
                axisOffset = 20.dp,
                rangeAdjustment = Multiplier(.1f)
            ),
            axisLine = AxisLineConfigDefaults.axisLineConfigDefaults().copy(
                ticks = true,
                lineColor = MaterialTheme.colorScheme.primary
            )
        )
    val yConfig = ContinuousAxisConfigDefaults.continuousAxisConfigDefaults()
        .copy(
            showAxis = showYAxis,
            showLabels = showYLabels,
            showAxisLine = showYAxisLine,
            showGuidelines = showYGuidelines,
            guidelines = GuidelinesConfigDefaults.guidelinesConfigDefaults().copy(
                strokeWidth = 1.dp,
                lineColor = MaterialTheme.colorScheme.onBackground,
                alpha = Multiplier(.5f),
                padding = 0.dp
            ),
            labels = ContinuousLabelsConfigDefaults.continuousLabelsConfigDefaults().copy(
                rotation = yRotation,
                axisOffset = 20.dp,
                rangeAdjustment = Multiplier(.1f)
            ),
            axisLine = AxisLineConfigDefaults.axisLineConfigDefaults().copy(
                ticks = true,
                lineColor = MaterialTheme.colorScheme.primary
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
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
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
                axisSelected = showXAxis,
                axisLineSelected = showXAxisLine,
                guidelinesSelected = showXGuidelines,
                labelsSelected = showXLabels,
                axisOnClick = { showXAxis = !showXAxis },
                axisLineOnClick = { showXAxisLine = !showXAxisLine },
                guidelinesOnClick = { showXGuidelines = !showXGuidelines },
                labelsOnClick = { showXLabels = !showXLabels },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(10.dp))
        }
        item {
            AxisChipRow(
                label = "Y-Axis:",
                axisSelected = showYAxis,
                axisLineSelected = showYAxisLine,
                guidelinesSelected = showYGuidelines,
                labelsSelected = showYLabels,
                axisOnClick = { showYAxis = !showYAxis },
                axisLineOnClick = { showYAxisLine = !showYAxisLine },
                guidelinesOnClick = { showYGuidelines = !showYGuidelines },
                labelsOnClick = { showYLabels = !showYLabels },
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