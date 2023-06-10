package com.joshrose.plotsforcompose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.joshrose.plotsforcompose.figures.BarFigure
import com.joshrose.plotsforcompose.internals.Plot
import com.joshrose.plotsforcompose.internals.plots.AxisPlot

@Composable
fun ShowPlot(plot: Plot, modifier: Modifier = Modifier) {

    // TODO: These should point to composables where the plots are drawn
    when (plot.mapping.map["figure"]) {
        is BarFigure -> println("BAR")
        null -> AxisPlot(plot, modifier) // TODO: Just draw axes.
    }
}