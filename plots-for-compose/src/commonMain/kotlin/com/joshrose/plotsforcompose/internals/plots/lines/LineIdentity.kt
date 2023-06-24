package com.joshrose.plotsforcompose.internals.plots.lines

import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import com.joshrose.plotsforcompose.axis.config.axisline.AxisLineConfiguration
import com.joshrose.plotsforcompose.axis.config.guidelines.GuidelinesConfiguration
import com.joshrose.plotsforcompose.axis.config.labels.LabelsConfiguration
import com.joshrose.plotsforcompose.axis.util.AxisPosition
import com.joshrose.plotsforcompose.axis.util.floatLabelsAndBreaks
import com.joshrose.plotsforcompose.internals.*
import com.joshrose.plotsforcompose.internals.aesthetics.axis.unboundYAxis
import com.joshrose.plotsforcompose.internals.util.Range
import kotlin.math.roundToInt

@OptIn(ExperimentalTextApi::class)
internal fun DrawScope.lineIdentityAxis(
    plot: Plot,
    xAxisPosition: AxisPosition.XAxis,
    yAxisPosition: AxisPosition.YAxis,
    scaleX: Scale?,
    xAxisLineConfigs: AxisLineConfiguration.XConfiguration?,
    yAxisLineConfigs: AxisLineConfiguration.YConfiguration?,
    yTextMeasurer: TextMeasurer
) {
    val scaleY: Scale? = plot.scales().lastOrNull { it.scale == ScaleKind.Y }

    val y = asMappingData(data = getData(plot.data), mapping = plot.mapping.map, key = "y")
    requireNotNull(value = y) { "LinePlot must have values defined for Y." }
    require(value = isCastAsNumber(y)) { "LinePlot requires Y values be of type Number." }

    val yAxisData = getAxisData(
        data = y,
        minValueAdjustment = scaleY?.labelConfigs?.minValueAdjustment,
        maxValueAdjustment = scaleY?.labelConfigs?.maxValueAdjustment,
        rangeAdjustment = scaleY?.labelConfigs?.rangeAdjustment
    )

    // Consider:
        // --> Case1: scaleY?.breaks && scaleY?.labels == null -- yBreaks = y && yLabels = y
        // --> Case2: scaleY?.breaks?.factor is null -- yBreaks = yLabels
        // --> Case3: scaleY?.guidelinesConfigs?.showGuidelines = false -- yBreaks = null; do not draw
        // --> Case4: yLabels.size < yBreaks.size
        // --> Case5: yLabels.size > yBreaks.size -- scaleY?.labels?.factor = scaleY?.breaks?.factor

    val yBreaks = floatLabelsAndBreaks(
        amount = (y.size.times((scaleY?.breaks?.factor ?: 1f))).roundToInt(),
        minValue = yAxisData.min,
        maxValue = yAxisData.max
    )
    val yLabels = floatLabelsAndBreaks(
        amount = (y.size.times((scaleY?.labels?.factor ?: 1f))).roundToInt(),
        minValue = yAxisData.min,
        maxValue = yAxisData.max
    )

    scaleY?.let {
        unboundYAxis(
            labelConfigs = it.labelConfigs ?: LabelsConfiguration(),
            guidelinesConfigs = it.guidelinesConfigs ?: GuidelinesConfiguration(),
            axisLineConfigs = yAxisLineConfigs ?: AxisLineConfiguration.YConfiguration(),
            labels = yLabels,
            yRangeValues = Range(min = yAxisData.min, max = yAxisData.max),
            yAxisPosition = yAxisPosition,
            xAxisPosition = xAxisPosition,
            drawXAxis = scaleX.isNotNull() && xAxisLineConfigs?.showAxisLine ?: AxisLineConfiguration.XConfiguration().ticks,
            range = yAxisData.range,
            textMeasurer = yTextMeasurer
        )
    }
}