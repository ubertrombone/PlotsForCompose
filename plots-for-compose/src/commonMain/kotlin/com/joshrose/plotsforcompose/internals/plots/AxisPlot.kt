package com.joshrose.plotsforcompose.internals.plots

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.joshrose.plotsforcompose.common.maxValue
import com.joshrose.plotsforcompose.common.minValue
import com.joshrose.plotsforcompose.common.range
import com.joshrose.plotsforcompose.internals.*
import com.joshrose.plotsforcompose.util.width

@Composable
internal fun AxisPlot(plot: Plot, modifier: Modifier = Modifier) {
    val data = getData(plot.data)

    val scaleX: Scale? = plot.scales().lastOrNull { it.scale == ScaleKind.X }
    val scaleY: Scale? = plot.scales().lastOrNull { it.scale == ScaleKind.Y }

    val size: Map<String, Dp?>? =
        plot.otherFeatures().lastOrNull { it.kind == "size" }?.configs?.mapValues { it.value as Dp? }

    val mappings = plot.mapping.map
    val x = asMappingData(data = data, mapping = mappings, key = "x")
    val y = asMappingData(data = data, mapping = mappings, key = "y")

    val rawXMax = x?.mapNotNull { it.toFloatOrNull() }?.maxOrNull() ?: 100f
    val xMax = maxValue(
        maxValue = rawXMax,
        maxValueAdjustment = scaleX?.labelConfigs?.maxValueAdjustment
    )
    val rawYMax = y?.mapNotNull { it.toFloatOrNull() }?.maxOrNull() ?: 100f
    val yMax = maxValue(
        maxValue = rawYMax,
        maxValueAdjustment = scaleY?.labelConfigs?.maxValueAdjustment
    )
    val rawXMin = x?.mapNotNull { it.toFloatOrNull() }?.minOrNull() ?: 0f
    val xMin = minValue(
        minValue = rawXMin,
        maxValue = xMax,
        minValueAdjustment = scaleX?.labelConfigs?.minValueAdjustment
    )
    val rawYMin = y?.mapNotNull { it.toFloatOrNull() }?.minOrNull() ?: 0f
    val yMin = minValue(
        minValue = rawYMin,
        maxValue = yMax,
        minValueAdjustment = scaleY?.labelConfigs?.minValueAdjustment
    )
    val xRange = range(
        minValue = xMin,
        maxValue = xMax,
        rangeAdjustment = scaleX?.labelConfigs?.rangeAdjustment
    )
    val yRange = range(
        minValue = yMin,
        maxValue = yMax,
        rangeAdjustment = scaleY?.labelConfigs?.rangeAdjustment
    )

    Canvas(
        modifier = Modifier
            .height(size?.get("height") ?: 500.dp)
            .width(size?.get("width"))
    ) {
        scaleX?.let {

        }
        scaleY?.let {

        }
    }
}