@file:Suppress("DuplicatedCode")

package com.joshrose.plotsforcompose.internals.plots.lines

import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import com.joshrose.plotsforcompose.axis.config.axisline.AxisLineConfiguration
import com.joshrose.plotsforcompose.axis.config.guidelines.GuidelinesConfiguration
import com.joshrose.plotsforcompose.axis.config.labels.LabelsConfiguration
import com.joshrose.plotsforcompose.axis.util.AxisAlignment
import com.joshrose.plotsforcompose.axis.util.AxisPosition
import com.joshrose.plotsforcompose.internals.Plot
import com.joshrose.plotsforcompose.internals.Scale
import com.joshrose.plotsforcompose.internals.ScaleKind
import com.joshrose.plotsforcompose.internals.aesthetics.axis.boundYAxis
import com.joshrose.plotsforcompose.internals.isNotNull
import kotlin.math.roundToInt

@OptIn(ExperimentalTextApi::class)
internal fun DrawScope.lineCountAxis(
    plot: Plot,
    x: List<Any?>,
    xAxisPosition: AxisPosition.XAxis,
    yAxisPosition: AxisPosition.YAxis,
    scaleX: Scale?,
    xAxisLineConfigs: AxisLineConfiguration.XConfiguration?,
    yAxisLineConfigs: AxisLineConfiguration.YConfiguration?,
    yTextMeasurer: TextMeasurer
) {
    val scaleY = plot.scales().lastOrNull { it.scale == ScaleKind.Y }
    val y = x.groupingBy { it }.eachCount().values.toSet()
    val yBreaks = y.filterIndexed { index, _ -> index % (1.div(scaleY?.breaks?.factor ?: 1f)).roundToInt() == 0 }
    val yLabels = y.filterIndexed { index, _ -> index % (1.div(scaleY?.labels?.factor ?: 1f)).roundToInt() == 0 }

    val yLabelFactor =
        if (yLabels.size == 1 && (yAxisLineConfigs?.axisAlignment == AxisAlignment.SpaceBetween || yAxisLineConfigs?.axisAlignment == null))
            size.height.div(yLabels.size.toFloat())
        else size.height.div(yLabels.size.plus((yAxisLineConfigs?.axisAlignment ?: AxisAlignment.SpaceBetween).offset).toFloat())

    scaleY?.let {
        boundYAxis(
            labelConfigs = it.labelConfigs ?: LabelsConfiguration(),
            guidelinesConfigs = it.guidelinesConfigs ?: GuidelinesConfiguration(),
            axisLineConfigs = yAxisLineConfigs ?: AxisLineConfiguration.YConfiguration(),
            factor = yLabelFactor,
            labels = yLabels,
            yAxisPosition = yAxisPosition,
            xAxisPosition = xAxisPosition,
            drawXAxis = scaleX.isNotNull() && xAxisLineConfigs?.showAxisLine ?: AxisLineConfiguration.XConfiguration().ticks,
            axisAlignment = yAxisLineConfigs?.axisAlignment ?: AxisAlignment.SpaceBetween,
            textMeasurer = yTextMeasurer
        )
    }
}