package com.joshrose.plotsforcompose.axis.config.axisline

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.joshrose.plotsforcompose.axis.config.util.Multiplier
import com.joshrose.plotsforcompose.axis.util.AxisPosition
import com.joshrose.plotsforcompose.theme.md_theme_dark_onBackground

/**
 * This class defines all available axis line properties.
 *
 *  @property lineColor the color of the axis line.
 *  @property alpha the alpha to apply to the axis line. This property must be of type [Multiplier].
 *  To hide the axis line, set the axis config property showAxisLine to false.
 *  @property strokeWidth the width of the line.
 *  @property pathEffect the pathEffect to apply to the line.
 *  @property ticks if true, draw tick labels.
 *  @property axisPosition the position of the axis in the chart.
 *  @constructor Creates a configuration for the axis line.
 */
data class AxisLineConfig(
    val lineColor: Color,
    val alpha: Multiplier,
    val strokeWidth: Dp,
    val pathEffect: PathEffect?,
    val ticks: Boolean,
    val axisPosition: AxisPosition?
)

/** Contains default values used for implementations of [AxisLineConfig] */
object AxisLineConfigDefaults {

    /**
     * Creates an [AxisLineConfig] for basic axis line implementations.
     *
     * @param lineColor the axis line color.
     * @param alpha the axis line alpha.
     * @param strokeWidth the axis line width.
     * @param pathEffect the pathEffect to apply to the axis line.
     * @param ticks if true, draw ticks along relevant axis.
     * @param axisPosition where to draw the axis on the chart. Defaults to null - chart will draw the axis in the
     * most appropriate spot.
     * @return the resulting [AxisLineConfig] to be used with an axis configuration.
     */
    fun axisLineConfigDefaults(
        lineColor: Color = md_theme_dark_onBackground,
        alpha: Multiplier = Multiplier(1f),
        strokeWidth: Dp = 2.dp,
        pathEffect: PathEffect? = null,
        ticks: Boolean = true,
        axisPosition: AxisPosition? = null
    ): AxisLineConfig =
        AxisLineConfig(
            lineColor = lineColor,
            alpha = alpha,
            strokeWidth = strokeWidth,
            pathEffect = pathEffect,
            ticks = ticks,
            axisPosition = axisPosition
        )
}
