package com.joshrose.plotsforcompose.internals.plots.lines

import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import com.joshrose.plotsforcompose.axis.config.axisline.AxisLineConfiguration
import com.joshrose.plotsforcompose.axis.config.guidelines.GuidelinesConfiguration
import com.joshrose.plotsforcompose.axis.config.labels.LabelsConfiguration
import com.joshrose.plotsforcompose.axis.util.AxisPosition
import com.joshrose.plotsforcompose.internals.*
import com.joshrose.plotsforcompose.internals.aesthetics.axis.unboundYAxis
import com.joshrose.plotsforcompose.internals.util.AxisData

@OptIn(ExperimentalTextApi::class)
internal fun DrawScope.lineIdentityFigure(
    y: List<Any?>,
    yAxisData: AxisData,
    xAxisPosition: AxisPosition.XAxis,
    yAxisPosition: AxisPosition.YAxis,
    scaleX: Scale?,
    scaleY: Scale?,
    yAxisLineConfigs: AxisLineConfiguration.YConfiguration?,
    yTextMeasurer: TextMeasurer
) {
    val yBreaks = getUnboundBreaks(scale = scaleY, rawData = y, axisData = yAxisData)
    val yLabels = getUnboundLabels(scale = scaleY, rawData = y, breaksData = yBreaks, axisData = yAxisData)
    val yLabelIndices = getIndices(scale = scaleY, rawData = y, breaksData = yBreaks)

    scaleY?.let {
        unboundYAxis(
            labelConfigs = scaleY.labelConfigs ?: LabelsConfiguration(),
            guidelinesConfigs = scaleY.guidelinesConfigs ?: GuidelinesConfiguration(),
            axisLineConfigs = yAxisLineConfigs ?: AxisLineConfiguration.YConfiguration(),
            labels = yLabels,
            labelIndices = yLabelIndices,
            guidelines = yBreaks,
            yAxisPosition = yAxisPosition,
            xAxisPosition = xAxisPosition,
            drawXAxis = scaleX.isNotNull() && scaleX?.showAxisLine ?: AxisLineConfiguration.XConfiguration().ticks,
            textMeasurer = yTextMeasurer,
            scale = scaleY
        )
    }
}