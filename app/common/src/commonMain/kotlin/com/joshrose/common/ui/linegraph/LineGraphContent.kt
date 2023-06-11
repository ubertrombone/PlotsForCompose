package com.joshrose.common.ui.linegraph

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.unit.dp
import com.joshrose.common.components.graph.LineGraphComponent
import com.joshrose.common.util.ScrollLazyColumn
import com.joshrose.plotsforcompose.axis.config.AxisConfiguration
import com.joshrose.plotsforcompose.linegraph.LineGraph
import com.joshrose.plotsforcompose.linegraph.model.NumberData

@OptIn(ExperimentalTextApi::class)
@Composable
fun LineGraphContent(
    component: LineGraphComponent,
    modifier: Modifier = Modifier
) {
    val xConfig = AxisConfiguration.xAxisConfigurationDefaults()
        .copy(
            showGuidelines = true,
//            guidelines = GuidelinesConfigurationDefaults.guidelinesConfigurationDefaults().copy(
//                lineColor = MaterialTheme.colorScheme.onBackground,
//                alpha = Multiplier(.5f),
//                padding = 0f
//            ),
//            labels = LabelsConfigurationDefaults.labelsConfigurationDefault().copy(
//                axisOffset = 20.dp,
//                fontColor = MaterialTheme.colorScheme.primary
//            ),
//            axisLine = AxisLineConfiguration.xAxisLineConfigurationDefaults().copy(
//                lineColor = MaterialTheme.colorScheme.primary,
//            )
        )
    val yConfig = AxisConfiguration.yAxisConfigurationDefaults()
        .copy(
            showGuidelines = true,
//            guidelines = GuidelinesConfigurationDefaults.guidelinesConfigurationDefaults().copy(
//                lineColor = MaterialTheme.colorScheme.onBackground,
//                alpha = Multiplier(.5f),
//                padding = 0f
//            ),
//            labels = LabelsConfigurationDefaults.labelsConfigurationDefault().copy(
//                axisOffset = 20.dp,
//                fontColor = MaterialTheme.colorScheme.primary
//            ),
//            axisLine = AxisLineConfiguration.yAxisLineConfigurationDefaults().copy(
//                lineColor = MaterialTheme.colorScheme.primary,
//            )
        )

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val rawData = mapOf(
            "X" to listOf(1, 2, 3, 4, 5),
            "Y" to listOf(6, 7, 8, 9, 10)
        )
        ScrollLazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {
            item {
                LineGraph(
                    realData = rawData,
                    data = listOf(NumberData(1, 2f), NumberData(4, 5f), NumberData(3, 4f), NumberData(11, 12f)).sortedBy { it.x.toFloat() },
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