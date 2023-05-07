@file:Suppress("DuplicatedCode")

package com.joshrose.common.ui.axes

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.joshrose.common.components.axes.AxesComponent
import com.joshrose.common.ui.axes.guidelines.GuidelinesContent
import com.joshrose.common.ui.axes.visibility.VisibilityContent
import com.joshrose.common.util.ScrollLazyColumn
import com.joshrose.common.util.paddingBottomBar
import com.joshrose.plotsforcompose.axis.config.ContinuousAxisConfigDefaults
import com.joshrose.plotsforcompose.axis.config.axisline.AxisLineConfigDefaults
import com.joshrose.plotsforcompose.axis.config.guidelines.GuidelinesConfigDefaults
import com.joshrose.plotsforcompose.axis.config.labels.ContinuousLabelsConfigDefaults
import com.joshrose.plotsforcompose.axis.config.util.Multiplier
import com.joshrose.plotsforcompose.axis.config.util.toMultiplier

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
                alpha = xGuidelinesStates.alpha.toMultiplier(),
                padding = xGuidelinesStates.padding
            ),
            labels = ContinuousLabelsConfigDefaults.continuousLabelsConfigDefaults().copy(
                rotation = 0f,
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
                alpha = yGuidelinesStates.alpha.toMultiplier(),
                padding = yGuidelinesStates.padding
            ),
            labels = ContinuousLabelsConfigDefaults.continuousLabelsConfigDefaults().copy(
                rotation = 0f,
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

    Scaffold(
        modifier = modifier,
        bottomBar = {
            NavigationBar(containerColor = colorScheme.primary) {
                NavigationBarItem(
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = colorScheme.onPrimary,
                        unselectedTextColor = colorScheme.onPrimary,
                        selectedIconColor = colorScheme.onPrimaryContainer,
                        selectedTextColor = colorScheme.primaryContainer,
                        indicatorColor = colorScheme.primaryContainer
                    ),
                    selected = activeComponent is AxesComponent.Child.VisibilityChild,
                    onClick = component::onVisibilityTabClicked,
                    label = { Text(text = "Visibility", softWrap = false) },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Face,
                            contentDescription = "Visibility Options"
                        )
                    }
                )

                NavigationBarItem(
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = colorScheme.onPrimary,
                        unselectedTextColor = colorScheme.onPrimary,
                        selectedIconColor = colorScheme.onPrimaryContainer,
                        selectedTextColor = colorScheme.primaryContainer,
                        indicatorColor = colorScheme.primaryContainer
                    ),
                    selected = activeComponent is AxesComponent.Child.GuidelinesChild,
                    onClick = component::onGuidelinesTabClicked,
                    label = { Text(text = "Guidelines", softWrap = false) },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Guidelines"
                        )
                    }
                )
            }
        }
    ) { padding ->
        ScrollLazyColumn(modifier = Modifier
            .fillMaxSize()
            .paddingBottomBar(paddingValues = padding, start = 20.dp, end = 20.dp)
        ) {
            item {
                Button(
                    onClick = {
                        component.updateData(
                            xList = List(2) { (-10_000..10_000).random().toFloat() },
                            yList = List(2) { (-10_000..10_000).random().toFloat() }
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorScheme.primaryContainer,
                        contentColor = colorScheme.onPrimaryContainer
                    ),
                    modifier = Modifier.padding(top = 10.dp)
                ) {
                    Text(
                        text = "Generate New Axes",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
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
                        is AxesComponent.Child.AxisLinesChild -> TODO()
                        is AxesComponent.Child.GuidelinesChild ->
                            GuidelinesContent(component = child.component, modifier = Modifier.fillMaxSize())
                        is AxesComponent.Child.LabelsChild -> TODO()
                        is AxesComponent.Child.VisibilityChild ->
                            VisibilityContent(component = child.component, modifier = Modifier.fillMaxSize())
                    }
                }
            }
        }
    }
}