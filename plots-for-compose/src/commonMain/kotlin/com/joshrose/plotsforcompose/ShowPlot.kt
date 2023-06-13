package com.joshrose.plotsforcompose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.joshrose.plotsforcompose.figures.BarFigure
import com.joshrose.plotsforcompose.figures.LineFigure
import com.joshrose.plotsforcompose.internals.Plot
import com.joshrose.plotsforcompose.internals.plots.AxisPlot

@Composable
fun ShowPlot(plot: Plot, modifier: Modifier = Modifier) {
    when (plot.mapping.map["figure"]) {
        is BarFigure -> println("BAR")
        is LineFigure -> println("Line")
        null -> AxisPlot(plot, modifier)
    }
}