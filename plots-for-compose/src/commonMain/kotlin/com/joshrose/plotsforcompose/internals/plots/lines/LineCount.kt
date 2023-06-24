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
import com.joshrose.plotsforcompose.internals.Scale
import com.joshrose.plotsforcompose.internals.aesthetics.axis.boundYAxis
import com.joshrose.plotsforcompose.internals.isNotNull
import kotlin.math.roundToInt

@OptIn(ExperimentalTextApi::class)
internal fun DrawScope.lineCountAxis(
    x: List<Any?>,
    xAxisPosition: AxisPosition.XAxis,
    yAxisPosition: AxisPosition.YAxis,
    scaleX: Scale?,
    scaleY: Scale,
    xAxisLineConfigs: AxisLineConfiguration.XConfiguration?,
    yAxisLineConfigs: AxisLineConfiguration.YConfiguration?,
    yTextMeasurer: TextMeasurer
) {
    val y = x.groupingBy { it }.eachCount().values.toSet().sorted()

    // Consider:
        // --> Case1: scaleY?.breaks && scaleY?.labels == null -- yBreaks = y && yLabels = y
        // --> Case2: scaleY?.breaks?.factor is null -- yBreaks = yLabels
        // --> Case3: scaleY?.guidelinesConfigs?.showGuidelines = false -- yBreaks = null; do not draw
        // --> Case4: yLabels.size < yBreaks.size
        // --> Case5: yLabels.size > yBreaks.size -- scaleY?.labels?.factor = scaleY?.breaks?.factor
    val yBreaks = y.filterIndexed { index, _ -> index % (1.div(scaleY.breaks?.factor ?: 1f)).roundToInt() == 0 }
    val yLabels = y.filterIndexed { index, _ -> index % (1.div(scaleY.labels?.factor ?: 1f)).roundToInt() == 0 }

    val yLabelFactor =
        if (yLabels.size == 1 && (yAxisLineConfigs?.axisAlignment == AxisAlignment.SpaceBetween))
            size.height.div(yLabels.size.toFloat())
        else size.height.div(yLabels.size.plus((yAxisLineConfigs?.axisAlignment ?: AxisAlignment.SpaceBetween).offset).toFloat())

    boundYAxis(
        labelConfigs = scaleY.labelConfigs ?: LabelsConfiguration(),
        guidelinesConfigs = scaleY.guidelinesConfigs ?: GuidelinesConfiguration(),
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