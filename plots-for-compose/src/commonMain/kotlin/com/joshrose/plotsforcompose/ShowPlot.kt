package com.joshrose.plotsforcompose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.joshrose.plotsforcompose.figures.BarFigure
import com.joshrose.plotsforcompose.figures.LineFigure
import com.joshrose.plotsforcompose.internals.Plot
import com.joshrose.plotsforcompose.internals.plots.AxisPlot
import com.joshrose.plotsforcompose.internals.plots.LinePlot

@Composable
fun ShowPlot(plot: Plot, modifier: Modifier = Modifier) {
    if (plot.mapping.map["figure"] is LineFigure) {
        println((plot.mapping.map["figure"] as LineFigure).stat.kind)
    }
    println(plot.mapping.map["figure"])
    when (plot.mapping.map["figure"]) {
        is BarFigure -> println("BAR")
        is LineFigure -> LinePlot(plot, modifier)
        null -> AxisPlot(plot, modifier)
    }
}