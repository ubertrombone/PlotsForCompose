@file:Suppress("DuplicatedCode")

package com.joshrose.common.ui.axes

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.joshrose.common.components.axes.AxesComponent
import com.joshrose.common.util.ImageResources.*
import com.joshrose.common.util.ScrollLazyColumn
import com.joshrose.common.util.createPainter
import com.joshrose.common.util.paddingBottomBar
import com.joshrose.plotsforcompose.axis.config.AxisConfiguration
import com.joshrose.plotsforcompose.axis.config.axisline.AxisLineConfiguration
import com.joshrose.plotsforcompose.axis.config.guidelines.GuidelinesConfigurationDefaults
import com.joshrose.plotsforcompose.axis.config.labels.LabelsConfigurationDefaults
import com.joshrose.plotsforcompose.axis.x.continuous.unboundXAxis
import com.joshrose.plotsforcompose.composePlot

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AxesContent(
    component: AxesComponent,
    modifier: Modifier = Modifier
) {
    val childStack by component.childStack.subscribeAsState()
    val activeComponent = childStack.active.instance

    val xVisibilityStates by component.xVisibilityState.subscribeAsState()
    val yVisibilityStates by component.yVisibilityState.subscribeAsState()
    val xAxisShowStates by component.xVisibilityState.subscribeAsState()
    val yAxisShowStates by component.yVisibilityState.subscribeAsState()
    val xGuidelinesStates by component.xGuidelinesState.subscribeAsState()
    val yGuidelinesStates by component.yGuidelinesState.subscribeAsState()
    val xAxisLineStates by component.xAxisLineState.subscribeAsState()
    val yAxisLineStates by component.yAxisLineState.subscribeAsState()
    val xLabelsStates by component.xLabelsState.subscribeAsState()
    val yLabelsStates by component.yLabelsState.subscribeAsState()

    val xConfig = AxisConfiguration.xAxisConfigurationDefaults()
        .copy(
            showAxis = xAxisShowStates.showAxis,
            showLabels = xAxisShowStates.showLabels,
            showAxisLine = xAxisShowStates.showAxisLine,
            showGuidelines = xAxisShowStates.showGuidelines,
            guidelines = GuidelinesConfigurationDefaults.guidelinesConfigurationDefaults().copy(
                strokeWidth = xGuidelinesStates.strokeWidth,
                lineColor = colorScheme.onBackground,
                alpha = xGuidelinesStates.alpha,
                padding = xGuidelinesStates.padding
            ),
            labels = LabelsConfigurationDefaults.labelsConfigurationDefault().copy(
                rotation = xLabelsStates.rotation,
                axisOffset = xLabelsStates.axisOffset.dp,
                rangeAdjustment = xLabelsStates.rangeAdjustment,
                minValueAdjustment = xLabelsStates.minValueAdjustment,
                maxValueAdjustment = xLabelsStates.maxValueAdjustment,
                breaks = xLabelsStates.breaks,
                fontColor = colorScheme.primary,
            ),
            axisLine = AxisLineConfiguration.xAxisLineConfigurationDefaults().copy(
                ticks = xAxisLineStates.ticks,
                lineColor = colorScheme.primary,
                strokeWidth = xAxisLineStates.strokeWidth,
                alpha = xAxisLineStates.alpha,
                axisPosition = xAxisLineStates.axisPosition
            )
        )
    val yConfig = AxisConfiguration.yAxisConfigurationDefaults()
        .copy(
            showAxis = yAxisShowStates.showAxis,
            showLabels = yAxisShowStates.showLabels,
            showAxisLine = yAxisShowStates.showAxisLine,
            showGuidelines = yAxisShowStates.showGuidelines,
            guidelines = GuidelinesConfigurationDefaults.guidelinesConfigurationDefaults().copy(
                strokeWidth = yGuidelinesStates.strokeWidth,
                lineColor = colorScheme.onBackground,
                alpha = yGuidelinesStates.alpha,
                padding = yGuidelinesStates.padding
            ),
            labels = LabelsConfigurationDefaults.labelsConfigurationDefault().copy(
                rotation = yLabelsStates.rotation,
                axisOffset = yLabelsStates.axisOffset.dp,
                rangeAdjustment = yLabelsStates.rangeAdjustment,
                minValueAdjustment = yLabelsStates.minValueAdjustment,
                maxValueAdjustment = yLabelsStates.maxValueAdjustment,
                breaks = yLabelsStates.breaks,
                fontColor = colorScheme.primary
            ),
            axisLine = AxisLineConfiguration.yAxisLineConfigurationDefaults().copy(
                ticks = yAxisLineStates.ticks,
                lineColor = colorScheme.primary,
                strokeWidth = yAxisLineStates.strokeWidth,
                alpha = yAxisLineStates.alpha,
                axisPosition = yAxisLineStates.axisPosition
            )
        )

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
                val givenData = component.dataValueStates.subscribeAsState()
                val data = mapOf(
                    "x" to givenData.value.data.map { it.x },
                    "y" to givenData.value.data.map { it.y }
                )
                val plot = composePlot(data = data) {
                    x = data["x"]
                    y = data["y"]
                }
                    .plus(unboundXAxis(
                        config = xConfig,
                        breaks = data["x"],
                        labels = data["x"]?.map { it.toString() },
                        reverse = false
                    ))
                    //.plus(plotSize(width = 500.dp, height = 300.dp))
                plot.show()
            }
//            item {
//                AxesCanvas(
//                    component = component,
//                    xConfig = xConfig,
//                    yConfig = yConfig,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(300.dp)
//                        .padding(50.dp)
//                )
//            }
//            item {
//                Children(stack = childStack) {
//                    when (val child = it.instance) {
//                        is AxesComponent.Child.AxisLinesChild ->
//                            AxisLineContent(
//                                component = child.component,
//                                xEnabled = !(!xVisibilityStates.showAxis || !xVisibilityStates.showAxisLine),
//                                yEnabled = !(!yVisibilityStates.showAxis || !yVisibilityStates.showAxisLine),
//                                modifier = Modifier.fillMaxSize()
//                            )
//                        is AxesComponent.Child.GuidelinesChild ->
//                            GuidelinesContent(
//                                component = child.component,
//                                xEnabled = !(!xVisibilityStates.showAxis || !xVisibilityStates.showGuidelines),
//                                yEnabled = !(!yVisibilityStates.showAxis || !yVisibilityStates.showGuidelines),
//                                modifier = Modifier.fillMaxSize()
//                            )
//                        is AxesComponent.Child.LabelsChild ->
//                            LabelsContent(
//                                component = child.component,
//                                data = component.dataValueStates,
//                                xEnabled = !(!xVisibilityStates.showAxis || !xVisibilityStates.showLabels),
//                                yEnabled = !(!yVisibilityStates.showAxis || !yVisibilityStates.showLabels),
//                                modifier = Modifier.fillMaxSize()
//                            )
//                        is AxesComponent.Child.VisibilityChild ->
//                            VisibilityContent(component = child.component, modifier = Modifier.fillMaxSize())
//                    }
//                }
//            }
//            item {
//                Row(
//                    modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.SpaceEvenly
//                ) {
//                    Button(
//                        onClick = {
//                            component.updateData(
//                                data = List(2) {
//                                    NumberData(
//                                        x = (-100..100).random().toFloat(),
//                                        y = (-100..100).random().toFloat()
//                                    )
//                                }
//                            )
//                        },
//                        colors = ButtonDefaults.buttonColors(
//                            containerColor = colorScheme.primaryContainer,
//                            contentColor = colorScheme.onPrimaryContainer
//                        )
//                    ) {
//                        Text(
//                            text = "Generate New Axes",
//                            style = typography.bodyMedium
//                        )
//                    }
//
//                    Button(
//                        onClick = component::resetAxis,
//                        colors = ButtonDefaults.buttonColors(
//                            containerColor = colorScheme.primaryContainer,
//                            contentColor = colorScheme.onPrimaryContainer
//                        )
//                    ) {
//                        Text(
//                            text = "Reset",
//                            style = typography.bodyMedium
//                        )
//                    }
//                }
//            }
        }
    }
}