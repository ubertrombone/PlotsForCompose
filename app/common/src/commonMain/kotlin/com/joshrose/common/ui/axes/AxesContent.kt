@file:Suppress("DuplicatedCode")

package com.joshrose.common.ui.axes

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.joshrose.common.components.axes.AxesComponent
import com.joshrose.common.ui.axes.axisline.AxisLineContent
import com.joshrose.common.ui.axes.guidelines.GuidelinesContent
import com.joshrose.common.ui.axes.labels.LabelsContent
import com.joshrose.common.ui.axes.visibility.VisibilityContent
import com.joshrose.common.util.*
import com.joshrose.plotsforcompose.axis.config.axisline.xConfiguration
import com.joshrose.plotsforcompose.axis.config.axisline.yConfiguration
import com.joshrose.plotsforcompose.axis.config.guidelines.guidelinesConfiguration
import com.joshrose.plotsforcompose.axis.config.labels.labelsConfiguration
import com.joshrose.plotsforcompose.axis.xAxis
import com.joshrose.plotsforcompose.axis.yAxis
import com.joshrose.plotsforcompose.composePlot
import com.joshrose.plotsforcompose.linegraph.model.NumberData
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun AxesContent(
    component: AxesComponent,
    modifier: Modifier = Modifier
) {
    val childStack by component.childStack.subscribeAsState()
    val activeComponent = childStack.active.instance

    val xVisibilityStates by component.xVisibilityState.subscribeAsState()
    val yVisibilityStates by component.yVisibilityState.subscribeAsState()
    val xGuidelinesStates by component.xGuidelinesState.subscribeAsState()
    val yGuidelinesStates by component.yGuidelinesState.subscribeAsState()
    val xAxisLineStates by component.xAxisLineState.subscribeAsState()
    val yAxisLineStates by component.yAxisLineState.subscribeAsState()
    val xLabelsStates by component.xLabelsState.subscribeAsState()
    val yLabelsStates by component.yLabelsState.subscribeAsState()
    val xBreakStates by component.xBreakState.subscribeAsState()
    val yBreakStates by component.yBreakState.subscribeAsState()
    val dataStates by component.dataValueStates.subscribeAsState()

    val primaryColor = colorScheme.primary
    val xLabelConfigs = labelsConfiguration {
        rotation = xLabelsStates.rotation
        axisOffset = xLabelsStates.axisOffset.dp
        rangeAdjustment = xLabelsStates.rangeAdjustment
        minValueAdjustment = xLabelsStates.minValueAdjustment
        maxValueAdjustment = xLabelsStates.maxValueAdjustment
        fontColor = primaryColor
    }
    val yLabelConfigs = labelsConfiguration {
        rotation = yLabelsStates.rotation
        axisOffset = yLabelsStates.axisOffset.dp
        rangeAdjustment = yLabelsStates.rangeAdjustment
        minValueAdjustment = yLabelsStates.minValueAdjustment
        maxValueAdjustment = yLabelsStates.maxValueAdjustment
        fontColor = primaryColor
    }

    val onBackgroundColor = colorScheme.onBackground
    val xGuidelineConfigs = guidelinesConfiguration {
        strokeWidth = xGuidelinesStates.strokeWidth
        lineColor = onBackgroundColor
        alpha = xGuidelinesStates.alpha
        padding = xGuidelinesStates.padding
    }
    val yGuidelineConfigs = guidelinesConfiguration {
        strokeWidth = yGuidelinesStates.strokeWidth
        lineColor = onBackgroundColor
        alpha = yGuidelinesStates.alpha
        padding = yGuidelinesStates.padding
    }

    val xAxisLineConfigs = xConfiguration {
        ticks = xAxisLineStates.ticks
        lineColor = primaryColor
        strokeWidth = xAxisLineStates.strokeWidth
        alpha = xAxisLineStates.alpha
        axisPosition = xAxisLineStates.axisPosition
    }
    val yAxisLineConfigs = yConfiguration {
        ticks = yAxisLineStates.ticks
        lineColor = primaryColor
        strokeWidth = yAxisLineStates.strokeWidth
        alpha = yAxisLineStates.alpha
        axisPosition = yAxisLineStates.axisPosition
    }

    Scaffold(
        modifier = modifier,
        bottomBar = {
            NavigationBar(containerColor = colorScheme.primary) {
                BottomBarItems(
                    label = "Visibility",
                    selected = activeComponent is AxesComponent.Child.VisibilityChild,
                    icon = { Icon(painter = painterResource("visibility.xml"), contentDescription = "Visibility Options") },
                    onClick = component::onVisibilityTabClicked
                )

                BottomBarItems(
                    label = "Guidelines",
                    selected = activeComponent is AxesComponent.Child.GuidelinesChild,
                    icon = { Icon(painter = painterResource("grid_on.xml"), contentDescription = "Guidelines") },
                    onClick = component::onGuidelinesTabClicked
                )

                BottomBarItems(
                    label = "Axis Lines",
                    selected = activeComponent is AxesComponent.Child.AxisLinesChild,
                    icon = { Icon(painter = painterResource("line_axis.xml"), contentDescription = "Axis Lines") },
                    onClick = component::onAxisLinesTabClicked
                )

                BottomBarItems(
                    label = "Labels",
                    selected = activeComponent is AxesComponent.Child.LabelsChild,
                    icon = { Icon(painter = painterResource("label.xml"), contentDescription = "Labels") },
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
                val data = mapOf(
                    "Independent" to dataStates.data.map { it.x },
                    "Dependent" to dataStates.data.map { it.y }
                )
                var plot = composePlot(data = data) {
                    x = "Independent"
                    y = "Dependent"
                }
                if (xVisibilityStates.showAxis) plot = plot.plus(xAxis(
                        showGuidelines = xVisibilityStates.showGuidelines,
                        showLabels = xVisibilityStates.showLabels,
                        showAxisLine = xVisibilityStates.showAxisLine,
                        labelConfigs = xLabelConfigs,
                        guidelinesConfigs = xGuidelineConfigs,
                        axisLineConfigs = xAxisLineConfigs,
                        breaks = xBreakStates.breaks,
                        labels = xBreakStates.labels
                    ))
                if (yVisibilityStates.showAxis) plot = plot.plus(yAxis(
                        showGuidelines = yVisibilityStates.showGuidelines,
                        showLabels = yVisibilityStates.showLabels,
                        showAxisLine = yVisibilityStates.showAxisLine,
                        labelConfigs = yLabelConfigs,
                        guidelinesConfigs = yGuidelineConfigs,
                        axisLineConfigs = yAxisLineConfigs,
                        breaks = yBreakStates.breaks,
                        labels = yBreakStates.labels
                    ))

                plot.show(
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
                            AxisLineContent(
                                component = child.component,
                                xEnabled = !(!xVisibilityStates.showAxis || !xVisibilityStates.showAxisLine),
                                yEnabled = !(!yVisibilityStates.showAxis || !yVisibilityStates.showAxisLine),
                                modifier = Modifier.fillMaxSize()
                            )
                        is AxesComponent.Child.GuidelinesChild ->
                            GuidelinesContent(
                                component = child.component,
                                xEnabled = !(!xVisibilityStates.showAxis || !xVisibilityStates.showGuidelines),
                                yEnabled = !(!yVisibilityStates.showAxis || !yVisibilityStates.showGuidelines),
                                modifier = Modifier.fillMaxSize()
                            )
                        is AxesComponent.Child.LabelsChild ->
                            LabelsContent(
                                labelsComponent = child.labelComponent,
                                breaksComponent = child.breaksComponent,
                                data = component.dataValueStates,
                                xEnabled = !(!xVisibilityStates.showAxis || !xVisibilityStates.showLabels),
                                yEnabled = !(!yVisibilityStates.showAxis || !yVisibilityStates.showLabels),
                                modifier = Modifier.fillMaxSize()
                            )
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
                    GenerateDataButton {
                        component.updateData(
                            data = List(5) {
                                NumberData(
                                    x = (-10..100).random().toFloat(),
                                    y = (-10..100).random().toFloat()
                                )
                            }
                        )
                    }
                    ResetButton(onClick = component::resetAxis)
                }
            }
        }
    }
}