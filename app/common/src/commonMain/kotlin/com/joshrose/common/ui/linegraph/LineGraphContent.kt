package com.joshrose.common.ui.linegraph

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.joshrose.common.components.linegraph.DefaultLineGraphComponent
import com.joshrose.common.components.linegraph.LineGraphComponent
import com.joshrose.common.ui.linegraph.label.LabelContent
import com.joshrose.common.ui.linegraph.label_line.LabelLineContent
import com.joshrose.common.ui.linegraph.label_marker.LabelMarkerContent
import com.joshrose.common.ui.linegraph.line.LineContent
import com.joshrose.common.ui.linegraph.marker.MarkerContent
import com.joshrose.common.util.*
import com.joshrose.common.util.ImageResources.*
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
import com.joshrose.plotsforcompose.util.Markers
import com.joshrose.plotsforcompose.util.Proportional

@Suppress("DuplicatedCode")
@Composable
fun LineGraphContent(
    component: DefaultLineGraphComponent,
    modifier: Modifier = Modifier
) {
    val childStack by component.childStack.subscribeAsState()
    val activeComponent = childStack.active.instance

    val lineStates by component.lineStates.subscribeAsState()
    val markerStates by component.markerStates.subscribeAsState()
    val labelStates by component.labelStates.subscribeAsState()
    val labelLineStates by component.labelLineStates.subscribeAsState()
    val labelMarkerStates by component.labelMarkerStates.subscribeAsState()
    val data by component.dataValues.subscribeAsState()

    val color = colorScheme.primary
    val xAxisLineConfigs = xConfiguration { lineColor = color }
    val yAxisLineConfigs = yConfiguration { lineColor = color }

    val guidesColor = colorScheme.onBackground
    val guidelinesConfigs = guidelinesConfiguration {
        lineColor = guidesColor
        padding = 0f
    }

    val labelConfigs = labelsConfiguration {
        fontColor = color
        axisOffset = 20.dp
        rotation = 45f
    }

    val lineGraphColor = colorScheme.tertiaryContainer
    val markColor = colorScheme.tertiary
    val fontColor = colorScheme.secondary
    val labelColor = colorScheme.onBackground
    val lineGraphConfigs = lineGraphConfiguration {
        lineType = lineStates.lineType
        lineColor = lineGraphColor
        strokeWidth = lineStates.strokeWidth.dp
        strokeJoin = lineStates.strokeJoin.join
        markers = markerStates.markers
        markerSize = markerStates.markerSize.dp
        markerColor = markColor
        markerShape = Markers.SNOWFLAKE//markerStates.markerShape
        labelFontSize = labelStates.fontSize.sp
        labelFontColor = fontColor
        boxColor = labelColor
        boxAlpha = labelStates.boxAlpha
        rectCornerRadius = CornerRadius(x = labelStates.xCornerRadius, y = labelStates.yCornerRadius)
        labelMarkerColor = labelColor
        labelMarkerRadius = labelMarkerStates.radius
        labelMarkerStyle = labelMarkerStates.style
        labelLineColor = labelColor
        labelLineAlpha = labelLineStates.alpha
        labelLineStrokeWidth = labelLineStates.strokeWidth
        labelLineStrokeCap = labelLineStates.strokeCap.strokeCap
    }

    Scaffold(
        modifier = modifier,
        bottomBar = {
            NavigationBar(containerColor = colorScheme.primary) {
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

                BottomBarItems(
                    label = "Label",
                    selected = activeComponent is LineGraphComponent.Child.LabelChild,
                    icon = { Icon(painter = createPainter(LABELS), contentDescription = "Label Options") },
                    onClick = component::onLabelTabClicked
                )

                BottomBarItems(
                    label = "Label Line",
                    selected = activeComponent is LineGraphComponent.Child.LabelLineChild,
                    icon = { Icon(painter = createPainter(LABEL_LINE), contentDescription = "Label Line Options") },
                    onClick = component::onLabelLineTabClicked
                )

                BottomBarItems(
                    label = "Label Marker",
                    selected = activeComponent is LineGraphComponent.Child.LabelMarkerChild,
                    icon = { Icon(painter = createPainter(LABEL_MARKER), contentDescription = "Label Marker Options") },
                    onClick = component::onLabelMarkerTabClicked
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
                    "Dependent" to sortedData.second,
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
                        )
                    )
                    .plus(
                        yAxis(
                            axisLineConfigs = yAxisLineConfigs,
                            guidelinesConfigs = guidelinesConfigs,
                            labelConfigs = labelConfigs.copy(rotation = -(labelConfigs.rotation)),
                            labels = Proportional(.5f)
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
                        is LineGraphComponent.Child.LabelChild ->
                            LabelContent(component = child.component, modifier = Modifier.fillMaxSize())
                        is LineGraphComponent.Child.LabelLineChild ->
                            LabelLineContent(component = child.component, modifier = Modifier.fillMaxSize())
                        is LineGraphComponent.Child.LabelMarkerChild ->
                            LabelMarkerContent(
                                component = child.component,
                                markerSize = lineGraphConfigs.markerSize?.value ?: 10f,
                                modifier = Modifier.fillMaxSize()
                            )
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