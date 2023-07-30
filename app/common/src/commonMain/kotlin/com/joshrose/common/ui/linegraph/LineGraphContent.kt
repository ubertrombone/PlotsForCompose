package com.joshrose.common.ui.linegraph

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.joshrose.common.components.linegraph.DefaultLineGraphComponent
import com.joshrose.common.components.linegraph.LineGraphComponent
import com.joshrose.common.ui.linegraph.line.LineContent
import com.joshrose.common.ui.linegraph.marker.MarkerContent
import com.joshrose.common.util.*
import com.joshrose.common.util.ImageResources.PUSH_PIN
import com.joshrose.common.util.ImageResources.SHOW_CHART
import com.joshrose.plotsforcompose.Stats
import com.joshrose.plotsforcompose.axis.config.axisline.xConfiguration
import com.joshrose.plotsforcompose.axis.config.axisline.yConfiguration
import com.joshrose.plotsforcompose.axis.config.guidelines.guidelinesConfiguration
import com.joshrose.plotsforcompose.axis.config.labels.labelsConfiguration
import com.joshrose.plotsforcompose.axis.xAxis
import com.joshrose.plotsforcompose.axis.yAxis
import com.joshrose.plotsforcompose.composePlot
import com.joshrose.plotsforcompose.figures.LineFigure
import com.joshrose.plotsforcompose.linegraph.config.lineGraphConfiguration
import com.joshrose.plotsforcompose.util.Proportional

@Composable
fun LineGraphContent(
    component: DefaultLineGraphComponent,
    modifier: Modifier = Modifier
) {
    val childStack by component.childStack.subscribeAsState()
    val activeComponent = childStack.active.instance

    val lineStates by component.lineStates.subscribeAsState()
    val markerStates by component.markerStates.subscribeAsState()
    val data by component.dataValues.subscribeAsState()

    // TODO: this is ugly in light theme for some reason (tertiary probably)
    val color = MaterialTheme.colorScheme.primary
    val xAxisLineConfigs = xConfiguration { lineColor = color }
    val yAxisLineConfigs = yConfiguration { lineColor = color }

    val guidesColor = MaterialTheme.colorScheme.onBackground
    val guidelinesConfigs = guidelinesConfiguration {
        lineColor = guidesColor
        padding = 0f
    }

    val labelConfigs = labelsConfiguration {
        fontColor = color
        axisOffset = 20.dp
        rotation = 45f
    }

    // TODO: make this all configurable in app
    val lineGraphColor = MaterialTheme.colorScheme.onTertiary
    val markColor = MaterialTheme.colorScheme.tertiary
    val lineGraphConfigs = lineGraphConfiguration {
        lineType = lineStates.lineType
        lineColor = lineGraphColor
        strokeWidth = lineStates.strokeWidth.dp
        markers = markerStates.markers
        markerSize = markerStates.markerSize.dp
        markerColor = markColor
    }

    Scaffold(
        modifier = modifier,
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.primary) {
                BottomBarItems(
                    label = "Line",
                    selected = activeComponent is LineGraphComponent.Child.LineChild,
                    icon = { Icon(painter = createPainter(SHOW_CHART), contentDescription = "Line Options") },
                    onClick = component::onLineTabClicked
                )

                BottomBarItems(
                    label = "Marker",
                    selected = activeComponent is LineGraphComponent.Child.MarkerChild,
                    icon = { Icon(painter = createPainter(PUSH_PIN), contentDescription = "Marker Options") },
                    onClick = component::onMarkerTabClicked
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
                val sortedData = data.data["Independent"]!!.zip(data.data["Dependent"]!!).sortedBy { it.first }.unzip()
                val cleanedData = mapOf(
                    "Independent" to sortedData.first,
                    "Dependent" to sortedData.second
                )

                val plot = composePlot(data = cleanedData) {
                    figure = LineFigure(stat = Stats.identity, configs = lineGraphConfigs)
                    x = "Independent"
                    y = "Dependent"
                }
                    .plus(
                        xAxis(
                            axisLineConfigs = xAxisLineConfigs,
                            guidelinesConfigs = guidelinesConfigs,
                            labelConfigs = labelConfigs,
                            //breaks = Proportional(.25f),
                            labels = Proportional(.5f)
                        )
                    )
                    .plus(
                        yAxis(
                            axisLineConfigs = yAxisLineConfigs,
                            guidelinesConfigs = guidelinesConfigs,
                            labelConfigs = labelConfigs.copy(rotation = -(labelConfigs.rotation)),
                            breaks = Proportional(.5f)
                        )
                    )
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
                        is LineGraphComponent.Child.LineChild ->
                            LineContent(component = child.component, modifier = Modifier.fillMaxSize())
                        is LineGraphComponent.Child.MarkerChild ->
                            MarkerContent(component = child.component, modifier = Modifier.fillMaxSize())
                    }
                }
            }
            item {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    GenerateDataButton(onClick = component::updateData)
                    ResetButton(onClick = component::resetGraph)
                }
            }
        }
    }
}