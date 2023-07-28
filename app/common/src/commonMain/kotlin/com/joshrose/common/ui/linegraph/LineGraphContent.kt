package com.joshrose.common.ui.linegraph

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joshrose.common.components.graph.LineGraphComponent
import com.joshrose.common.util.ScrollLazyColumn
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
import com.joshrose.plotsforcompose.linegraph.util.LineType.CURVED
import com.joshrose.plotsforcompose.util.Proportional

@Composable
fun LineGraphContent(
    component: LineGraphComponent,
    modifier: Modifier = Modifier
) {

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
        lineType = CURVED
        lineColor = lineGraphColor
        strokeWidth = 5.dp
        markers = true
        markerSize = 4.dp
        markerColor = markColor
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val rawData = mapOf(
            "Independent" to List(20) { (-5..5).random() },
            "Dependent" to List(20) { (-5..5).random() }
        )
        ScrollLazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {
            item {
                val plot = composePlot(data = rawData) {
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
                        ))
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
        }
    }
}