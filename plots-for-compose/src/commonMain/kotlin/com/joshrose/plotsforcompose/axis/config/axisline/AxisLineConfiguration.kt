package com.joshrose.plotsforcompose.axis.config.axisline

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import com.joshrose.plotsforcompose.axis.config.util.Multiplier
import com.joshrose.plotsforcompose.axis.util.AxisAlignment
import com.joshrose.plotsforcompose.axis.util.AxisAlignment.SpaceBetween
import com.joshrose.plotsforcompose.axis.util.AxisPosition
import com.joshrose.plotsforcompose.theme.md_theme_dark_onBackground
import com.joshrose.plotsforcompose.theme.md_theme_dark_primary

sealed interface AxisLineConfiguration {
    // TODO: use builder syntax for this and other configs
//    interface Configuration : AxisLineConfiguration {
//        var showAxisLine: Boolean
//        var lineColor: Color
//        var alpha: Multiplier
//        var strokeWidth: Float
//        var pathEffect: PathEffect?
//        var ticks: Boolean
//    }

    class XConfiguration(
        var showAxisLine: Boolean = true,
        var lineColor: Color = md_theme_dark_primary,
        var alpha: Multiplier = Multiplier(1f),
        var strokeWidth: Float = 2f,
        var pathEffect: PathEffect? = null,
        var ticks: Boolean = true,
        var axisPosition: AxisPosition.XAxis? = null, // todo
        var axisAlignment: AxisAlignment.XAxis = SpaceBetween // todo
    ) : AxisLineConfiguration {
        override fun toString() = "AxisLineConfiguration#XConfiguration"

        companion object {
            fun buildConfig(config: XConfiguration.() -> Unit = {} ) = XConfiguration().apply(config)
        }
    }

    class YConfiguration(
        var showAxisLine: Boolean = true,
        var lineColor: Color = md_theme_dark_primary,
        var alpha: Multiplier = Multiplier(1f),
        var strokeWidth: Float = 2f,
        var pathEffect: PathEffect? = null,
        var ticks: Boolean = true,
        var axisPosition: AxisPosition.YAxis? = null,
        var axisAlignment: AxisAlignment.YAxis = SpaceBetween
    ) : AxisLineConfiguration {
        override fun toString() = "AxisLineConfiguration#YConfiguration"

        companion object {
            fun buildConfig(config: YConfiguration.() -> Unit = {} ) = YConfiguration().apply(config)
        }
    }

    companion object {
        fun xAxisLineConfigurationDefaults(
            showAxisLine: Boolean = true,
            lineColor: Color = md_theme_dark_onBackground,
            alpha: Multiplier = Multiplier(1f),
            strokeWidth: Float = 2f,
            pathEffect: PathEffect? = null,
            ticks: Boolean = true,
            axisPosition: AxisPosition.XAxis? = null,
            axisAlignment: AxisAlignment.XAxis = SpaceBetween
        ) = XConfiguration(
            showAxisLine = showAxisLine,
            lineColor = lineColor,
            alpha = alpha,
            strokeWidth = strokeWidth,
            pathEffect = pathEffect,
            ticks = ticks,
            axisPosition = axisPosition,
            axisAlignment = axisAlignment
        )

        fun yAxisLineConfigurationDefaults(
            showAxisLine: Boolean = true,
            lineColor: Color = md_theme_dark_onBackground,
            alpha: Multiplier = Multiplier(1f),
            strokeWidth: Float = 2f,
            pathEffect: PathEffect? = null,
            ticks: Boolean = true,
            axisPosition: AxisPosition.YAxis? = null,
            axisAlignment: AxisAlignment.YAxis = SpaceBetween
        ) = YConfiguration(
            showAxisLine = showAxisLine,
            lineColor = lineColor,
            alpha = alpha,
            strokeWidth = strokeWidth,
            pathEffect = pathEffect,
            ticks = ticks,
            axisPosition = axisPosition,
            axisAlignment = axisAlignment
        )
    }
}

fun something(config: AxisLineConfiguration.XConfiguration.() -> Unit = {}) =
    AxisLineConfiguration.XConfiguration().apply(config)

val some = something {
    ticks = false
}

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
    val strokeWidth: Float,
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
        strokeWidth: Float = 2f,
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
