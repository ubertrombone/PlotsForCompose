package com.joshrose.common.ui.linegraph

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joshrose.common.components.graph.LineGraphComponent
import com.joshrose.common.util.ScrollLazyColumn
import com.joshrose.plotsforcompose.axis.config.ContinuousAxisConfigDefaults
import com.joshrose.plotsforcompose.axis.config.axisline.AxisLineConfigDefaults
import com.joshrose.plotsforcompose.axis.config.guidelines.GuidelinesConfigDefaults
import com.joshrose.plotsforcompose.axis.config.labels.ContinuousLabelsConfigDefaults
import com.joshrose.plotsforcompose.axis.config.util.Multiplier
import com.joshrose.plotsforcompose.linegraph.LineGraph
import com.joshrose.plotsforcompose.linegraph.model.Coordinates.IntData

@Composable
fun LineGraphContent(
    component: LineGraphComponent,
    modifier: Modifier = Modifier
) {

    val yConfig = ContinuousAxisConfigDefaults.continuousAxisConfigDefaults()
        .copy(
            showGuidelines = true,
            guidelines = GuidelinesConfigDefaults.guidelinesConfigDefaults().copy(
                lineColor = MaterialTheme.colorScheme.onBackground,
                alpha = Multiplier(.5f),
                padding = 0f
            ),
            labels = ContinuousLabelsConfigDefaults.continuousLabelsConfigDefaults().copy(
                axisOffset = 20.dp,
                fontColor = MaterialTheme.colorScheme.primary
            ),
            axisLine = AxisLineConfigDefaults.axisLineConfigDefaults().copy(
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
                    data = listOf(IntData(1, 1f), IntData(4, 4f), IntData(3, 3f), IntData(11, 11f)),
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