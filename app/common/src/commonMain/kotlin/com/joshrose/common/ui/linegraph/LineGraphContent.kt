package com.joshrose.common.ui.linegraph

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joshrose.common.components.graph.LineGraphComponent
import com.joshrose.common.util.ScrollLazyColumn
import com.joshrose.plotsforcompose.Stats
import com.joshrose.plotsforcompose.axis.config.labels.LabelsConfiguration
import com.joshrose.plotsforcompose.axis.config.util.Multiplier
import com.joshrose.plotsforcompose.axis.xAxis
import com.joshrose.plotsforcompose.axis.yAxis
import com.joshrose.plotsforcompose.composePlot
import com.joshrose.plotsforcompose.figures.LineFigure
import com.joshrose.plotsforcompose.util.Proportional

@Composable
fun LineGraphContent(
    component: LineGraphComponent,
    modifier: Modifier = Modifier
) {
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
                    figure = LineFigure(stat = Stats.identity)
                    x = "Independent"
                    y = "Dependent"
                }
                    .plus(
                        xAxis(
                            //axisLineConfigs = AxisLineConfiguration.xConfiguration { axisAlignment = AxisAlignment.Start },
                            //guidelinesConfigs = GuidelinesConfiguration.guidelinesConfiguration { showGuidelines = false },
                            //breaks = Proportional(.25f),
                            labels = Proportional(.5f)
                        ))
                    .plus(
                        yAxis(
                            labelConfigs = LabelsConfiguration.labelsConfiguration {
                                rangeAdjustment = Multiplier(.1f)
                                minValueAdjustment = Multiplier(.1f)
                                maxValueAdjustment = Multiplier(.1f)
                            },
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