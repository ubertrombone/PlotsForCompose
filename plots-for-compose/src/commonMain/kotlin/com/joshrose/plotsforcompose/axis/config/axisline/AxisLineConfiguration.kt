package com.joshrose.plotsforcompose.axis.config.axisline

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import com.joshrose.plotsforcompose.axis.config.util.Multiplier
import com.joshrose.plotsforcompose.axis.util.AxisAlignment
import com.joshrose.plotsforcompose.axis.util.AxisAlignment.SpaceBetween
import com.joshrose.plotsforcompose.axis.util.AxisPosition
import com.joshrose.plotsforcompose.theme.md_theme_dark_primary

sealed interface AxisLineConfiguration {
    class XConfiguration(
        var lineColor: Color = md_theme_dark_primary,
        var alpha: Multiplier = Multiplier(1f),
        var strokeWidth: Float = 2f,
        var pathEffect: PathEffect? = null,
        var ticks: Boolean = true,
        var axisPosition: AxisPosition.XAxis? = null,
        var axisAlignment: AxisAlignment.XAxis = SpaceBetween
    ) : AxisLineConfiguration {
        override fun toString() = "AxisLineConfiguration#XConfiguration"
    }

    class YConfiguration(
        var lineColor: Color = md_theme_dark_primary,
        var alpha: Multiplier = Multiplier(1f),
        var strokeWidth: Float = 2f,
        var pathEffect: PathEffect? = null,
        var ticks: Boolean = true,
        var axisPosition: AxisPosition.YAxis? = null,
        var axisAlignment: AxisAlignment.YAxis = SpaceBetween
    ) : AxisLineConfiguration {
        override fun toString() = "AxisLineConfiguration#YConfiguration"
    }

    companion object {
        fun xConfiguration(init: XConfiguration.() -> Unit = {}) = XConfiguration().apply(init)
        fun yConfiguration(init: YConfiguration.() -> Unit = {} ) = YConfiguration().apply(init)
    }
}