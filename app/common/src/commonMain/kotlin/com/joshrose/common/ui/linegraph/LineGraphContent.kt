package com.joshrose.common.ui.linegraph

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joshrose.common.components.graph.LineGraphComponent
import com.joshrose.common.util.ScrollLazyColumn
import com.joshrose.plotsforcompose.axis.config.AxisConfiguration
import com.joshrose.plotsforcompose.axis.config.axisline.AxisLineConfiguration
import com.joshrose.plotsforcompose.axis.config.guidelines.GuidelinesConfigurationDefaults
import com.joshrose.plotsforcompose.axis.config.labels.LabelsConfigurationDefaults
import com.joshrose.plotsforcompose.axis.config.util.Multiplier
import com.joshrose.plotsforcompose.linegraph.LineGraph
import com.joshrose.plotsforcompose.linegraph.model.NumberData

@Composable
fun LineGraphContent(
    component: LineGraphComponent,
    modifier: Modifier = Modifier
) {
    val xConfig = AxisConfiguration.xAxisConfigurationDefaults()
        .copy(
            showGuidelines = true,
            guidelines = GuidelinesConfigurationDefaults.guidelinesConfigurationDefaults().copy(
                lineColor = MaterialTheme.colorScheme.onBackground,
                alpha = Multiplier(.5f),
                padding = 0f
            ),
            labels = LabelsConfigurationDefaults.labelsConfigurationDefault().copy(
                axisOffset = 20.dp,
                fontColor = MaterialTheme.colorScheme.primary
            ),
            axisLine = AxisLineConfiguration.xAxisLineConfigurationDefaults().copy(
                lineColor = MaterialTheme.colorScheme.primary,
            )
        )
    val yConfig = AxisConfiguration.yAxisConfigurationDefaults()
        .copy(
            showGuidelines = true,
            guidelines = GuidelinesConfigurationDefaults.guidelinesConfigurationDefaults().copy(
                lineColor = MaterialTheme.colorScheme.onBackground,
                alpha = Multiplier(.5f),
                padding = 0f
            ),
            labels = LabelsConfigurationDefaults.labelsConfigurationDefault().copy(
                axisOffset = 20.dp,
                fontColor = MaterialTheme.colorScheme.primary
            ),
            axisLine = AxisLineConfiguration.yAxisLineConfigurationDefaults().copy(
                lineColor = MaterialTheme.colorScheme.primary,
            )
        )

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ScrollLazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {
            item {
                LineGraph(
                    data = listOf(NumberData(1, 1f), NumberData(4, 4f), NumberData(3, 3f), NumberData(11, 11f)).sortedBy { it.x.toFloat() },
                    xAxisConfig = xConfig,
                    yAxisConfig = yConfig,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .padding(50.dp)
                )
            }
        }
    }
}