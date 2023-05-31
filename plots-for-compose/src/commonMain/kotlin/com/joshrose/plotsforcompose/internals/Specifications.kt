package com.joshrose.plotsforcompose.internals

import androidx.compose.ui.unit.Dp
import com.joshrose.plotsforcompose.LayerConfigs
import com.joshrose.plotsforcompose.axis.config.axisline.AxisLineConfiguration
import com.joshrose.plotsforcompose.axis.config.guidelines.GuidelinesConfiguration
import com.joshrose.plotsforcompose.axis.config.labels.LabelsConfiguration
import com.joshrose.plotsforcompose.axis.util.AxisPosition

object Specifications {
    data class Specs(
        val plotBase: PlotBase? = null,
        val plot: Plot? = null
    )

    data class PlotBase(
        val data: Map<String, List<Any?>>? = null,
        val mapping: Map<String, Any>? = null
    )

    data class Plot(
        val layers: List<Layer>? = null,
        val scales: List<Scale>? = null,
        val title: String? = null,
        val subTitle: String? = null,
        val caption: String? = null,
        val size: Size? = null
    )

    data class Layer(
        val data: Map<String, List<Any?>>? = null,
        val mapping: Map<String, Any>? = null,
        val plot: String? = null,
        val stat: String? = null,
        val pos: String? = null,
        val orientation: AxisPosition.Orientation? = null,
        val showLegend: Boolean? = null,
        val markers: Boolean? = null,
        val configs: LayerConfigs? = null
    )

    data class Scale(
        val scale: String? = null,
        val guidelineConfigs: GuidelinesConfiguration? = null,
        val labelConfigs: LabelsConfiguration? = null,
        val axisLineConfigs: AxisLineConfiguration? = null,
        val name: String? = null,
        val breaks: List<Any>? = null,
        val labels: List<String>? = null,
        val limits: Any? = null,
        val naValue: Any? = null,
        val format: String? = null,
        val reverse: Boolean? = null,
        val position: AxisPosition? = null
    )

    data class Size(
        val width: Dp? = null,
        val height: Dp? = null
    )
}
