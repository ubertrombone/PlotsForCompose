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
    // TODO: Why don't these respond to state changes?
    val showXAxis by component.showXAxis.collectAsState()
    val showYAxis by component.showYAxis.collectAsState()
    val showXAxisLine by component.showXAxisLine.collectAsState()
    val showYAxisLine by component.showYAxisLine.collectAsState()
    val showXGuidelines by component.showXGuidelines.collectAsState()
    val showYGuidelines by component.showYGuidelines.collectAsState()
    val showXLabels by component.showXLabels.collectAsState()
    val showYLabels by component.showYLabels.collectAsState()

    val guidelinesStrokeWidthX by component.guidelinesStrokeWidthX.collectAsState()
    val guidelinesStrokeWidthY by component.guidelinesStrokeWidthY.collectAsState()
    val guidelinesAlphaX by component.guidelinesAlphaX.collectAsState()
    val guidelinesAlphaY by component.guidelinesAlphaY.collectAsState()
    val guidelinesPaddingX by remember { mutableStateOf(0.dp) }
    val guidelinesPaddingY by remember { mutableStateOf(0.dp) }

    val xRotation by component.xRotation.collectAsState()
    val yRotation by component.yRotation.collectAsState()

    // TODO: Build out the sample
    val xConfig = ContinuousAxisConfigDefaults.continuousAxisConfigDefaults()
        .copy(
            showAxis = showXAxis,
            showLabels = showXLabels,
            showAxisLine = showXAxisLine,
            showGuidelines = showXGuidelines,
            guidelines = GuidelinesConfigDefaults.guidelinesConfigDefaults().copy(
                strokeWidth = guidelinesStrokeWidthX,
                lineColor = colorScheme.onBackground,
                alpha = guidelinesAlphaX,
                padding = guidelinesPaddingX
            ),
            labels = ContinuousLabelsConfigDefaults.continuousLabelsConfigDefaults().copy(
                rotation = xRotation,
                axisOffset = 20.dp,
                rangeAdjustment = Multiplier(.1f)
            ),
            axisLine = AxisLineConfigDefaults.axisLineConfigDefaults().copy(
                ticks = true,
                lineColor = colorScheme.primary
            )
        )
    val yConfig = ContinuousAxisConfigDefaults.continuousAxisConfigDefaults()
        .copy(
            showAxis = showYAxis,
            showLabels = showYLabels,
            showAxisLine = showYAxisLine,
            showGuidelines = showYGuidelines,
            guidelines = GuidelinesConfigDefaults.guidelinesConfigDefaults().copy(
                strokeWidth = guidelinesStrokeWidthY,
                lineColor = colorScheme.onBackground,
                alpha = guidelinesAlphaY,
                padding = guidelinesPaddingY
            ),
            labels = ContinuousLabelsConfigDefaults.continuousLabelsConfigDefaults().copy(
                rotation = yRotation,
                axisOffset = 20.dp,
                rangeAdjustment = Multiplier(.1f)
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
                axisSelected = showXAxis,
                axisLineSelected = showXAxisLine,
                guidelinesSelected = showXGuidelines,
                labelsSelected = showXLabels,
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
                axisSelected = showYAxis,
                axisLineSelected = showYAxisLine,
                guidelinesSelected = showYGuidelines,
                labelsSelected = showYLabels,
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