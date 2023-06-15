package com.joshrose.common.ui.linegraph

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.unit.dp
import com.joshrose.common.components.graph.LineGraphComponent
import com.joshrose.common.util.ScrollLazyColumn
import com.joshrose.plotsforcompose.axis.unboundXAxis
import com.joshrose.plotsforcompose.axis.unboundYAxis
import com.joshrose.plotsforcompose.composePlot
import com.joshrose.plotsforcompose.figures.LineFigure

@OptIn(ExperimentalTextApi::class)
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
            "Independent" to listOf(1, 2, 2, 3, 4, 5),
            "Dependent" to listOf(6, 7, 8, 9, 10, 11)
        )
        ScrollLazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {
            item {
                val plot = composePlot(data = rawData) {
                    figure = LineFigure()
                    x = "Independent"
                    y = "Dependent"
                }
                    .plus(
                        unboundXAxis(
                        reverse = false
                    ))
                    .plus(
                        unboundYAxis(
                        reverse = false
                    ))
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