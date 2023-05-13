@file:Suppress("DuplicatedCode")

package com.joshrose.common.ui.axes

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.joshrose.common.components.axes.AxesComponent
import com.joshrose.common.ui.axes.axisline.AxisLineContent
import com.joshrose.common.ui.axes.guidelines.GuidelinesContent
import com.joshrose.common.ui.axes.labels.LabelsContent
import com.joshrose.common.ui.axes.visibility.VisibilityContent
import com.joshrose.common.util.ImageResources.*
import com.joshrose.common.util.ScrollLazyColumn
import com.joshrose.common.util.createPainter
import com.joshrose.common.util.paddingBottomBar
import com.joshrose.plotsforcompose.axis.config.ContinuousAxisConfigDefaults
import com.joshrose.plotsforcompose.axis.config.axisline.AxisLineConfigDefaults
import com.joshrose.plotsforcompose.axis.config.guidelines.GuidelinesConfigDefaults
import com.joshrose.plotsforcompose.axis.config.labels.ContinuousLabelsConfigDefaults

@OptIn(ExperimentalTextApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AxesContent(
    component: AxesComponent,
    modifier: Modifier = Modifier
) {
    // TODO: Add a reset function when doing the above
    val childStack by component.childStack.subscribeAsState()
    val activeComponent = childStack.active.instance

    val xAxisShowStates by component.xVisibilityState.subscribeAsState()
    val yAxisShowStates by component.yVisibilityState.subscribeAsState()
    val xGuidelinesStates by component.xGuidelinesState.subscribeAsState()
    val yGuidelinesStates by component.yGuidelinesState.subscribeAsState()
    val xAxisLineStates by component.xAxisLineState.subscribeAsState()
    val yAxisLineStates by component.yAxisLineState.subscribeAsState()
    val xLabelsStates by component.xLabelsState.subscribeAsState()
    val yLabelsStates by component.yLabelsState.subscribeAsState()

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
                rotation = xLabelsStates.rotation,
                axisOffset = xLabelsStates.axisOffset.dp,
                rangeAdjustment = xLabelsStates.rangeAdjustment,
                minValueAdjustment = xLabelsStates.minValueAdjustment,
                maxValueAdjustment = xLabelsStates.maxValueAdjustment,
                breaks = xLabelsStates.breaks,
                fontColor = colorScheme.primary,
            ),
            axisLine = AxisLineConfigDefaults.axisLineConfigDefaults().copy(
                ticks = xAxisLineStates.ticks,
                lineColor = colorScheme.primary,
                strokeWidth = xAxisLineStates.strokeWidth,
                alpha = xAxisLineStates.alpha,
                axisPosition = xAxisLineStates.axisPosition
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
                rotation = yLabelsStates.rotation,
                axisOffset = yLabelsStates.axisOffset.dp,
                rangeAdjustment = yLabelsStates.rangeAdjustment,
                minValueAdjustment = yLabelsStates.minValueAdjustment,
                maxValueAdjustment = yLabelsStates.maxValueAdjustment,
                breaks = yLabelsStates.breaks,
                fontColor = colorScheme.primary
            ),
            axisLine = AxisLineConfigDefaults.axisLineConfigDefaults().copy(
                ticks = yAxisLineStates.ticks,
                lineColor = colorScheme.primary,
                strokeWidth = yAxisLineStates.strokeWidth,
                alpha = yAxisLineStates.alpha,
                axisPosition = yAxisLineStates.axisPosition
            )
        )

    // TODO: Force set axis min and max -- For later

    Scaffold(
        modifier = modifier,
        bottomBar = {
            NavigationBar(containerColor = colorScheme.primary) {
                BottomBarItems(
                    label = "Visibility",
                    selected = activeComponent is AxesComponent.Child.VisibilityChild,
                    icon = { Icon(painter = createPainter(VISIBILITY), contentDescription = "Visibility Options") },
                    onClick = component::onVisibilityTabClicked
                )

                BottomBarItems(
                    label = "Guidelines",
                    selected = activeComponent is AxesComponent.Child.GuidelinesChild,
                    icon = { Icon(painter = createPainter(GUIDELINES), contentDescription = "Guidelines") },
                    onClick = component::onGuidelinesTabClicked
                )

                BottomBarItems(
                    label = "Axis Lines",
                    selected = activeComponent is AxesComponent.Child.AxisLinesChild,
                    icon = { Icon(painter = createPainter(AXIS_LINES), contentDescription = "Axis Lines") },
                    onClick = component::onAxisLinesTabClicked
                )

                BottomBarItems(
                    label = "Labels",
                    selected = activeComponent is AxesComponent.Child.LabelsChild,
                    icon = { Icon(painter = createPainter(LABELS), contentDescription = "Labels") },
                    onClick = component::onLabelsTabClicked
                )
            }
        }
    ) { padding ->
        ScrollLazyColumn(
            paddingValues = padding,
            modifier = Modifier
                .fillMaxSize()
                .paddingBottomBar(paddingValues = padding, start = 20.dp, end = 20.dp)
        ) {
            item {
                AxesCanvas(
                    component = component,
                    xConfig = xConfig,
                    yConfig = yConfig,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .padding(50.dp)
                )
            }
            item {
                Children(stack = childStack) {
                    when (val child = it.instance) {
                        is AxesComponent.Child.AxisLinesChild ->
                            AxisLineContent(component = child.component, modifier = Modifier.fillMaxSize())
                        is AxesComponent.Child.GuidelinesChild ->
                            GuidelinesContent(component = child.component, modifier = Modifier.fillMaxSize())
                        is AxesComponent.Child.LabelsChild ->
                            LabelsContent(component = child.component, modifier = Modifier.fillMaxSize())
                        is AxesComponent.Child.VisibilityChild ->
                            VisibilityContent(component = child.component, modifier = Modifier.fillMaxSize())
                    }
                }
            }
            item {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            component.updateData(
                                xList = List(2) { (-100..100).random().toFloat() },
                                yList = List(2) { (-100..100).random().toFloat() }
                            )
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorScheme.primaryContainer,
                            contentColor = colorScheme.onPrimaryContainer
                        )
                    ) {
                        Text(
                            text = "Generate New Axes",
                            style = typography.bodyMedium
                        )
                    }

                    Button(
                        onClick = component::resetAxis,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorScheme.primaryContainer,
                            contentColor = colorScheme.onPrimaryContainer
                        )
                    ) {
                        Text(
                            text = "Reset",
                            style = typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}