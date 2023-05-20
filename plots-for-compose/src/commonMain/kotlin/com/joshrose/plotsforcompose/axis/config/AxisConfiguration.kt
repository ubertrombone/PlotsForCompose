package com.joshrose.plotsforcompose.axis.config

import com.joshrose.plotsforcompose.axis.config.axisline.AxisLineConfiguration
import com.joshrose.plotsforcompose.axis.config.guidelines.GuidelinesConfiguration
import com.joshrose.plotsforcompose.axis.config.guidelines.GuidelinesConfigurationDefaults
import com.joshrose.plotsforcompose.axis.config.labels.LabelsConfiguration
import com.joshrose.plotsforcompose.axis.config.labels.LabelsConfigurationDefaults

object AxisConfiguration {
    interface Configuration {
        val showAxis: Boolean
        val showLabels: Boolean
        val showGuidelines: Boolean
        val showAxisLine: Boolean
        val guidelines: GuidelinesConfiguration
        val labels: LabelsConfiguration
        val axisLine: AxisLineConfiguration
    }

    data class XConfiguration(
        override val showAxis: Boolean,
        override val showLabels: Boolean,
        override val showGuidelines: Boolean,
        override val showAxisLine: Boolean,
        override val guidelines: GuidelinesConfiguration,
        override val labels: LabelsConfiguration,
        override val axisLine: AxisLineConfiguration.XConfiguration
    ) : Configuration {
        override fun toString() = "AxisConfiguration#XConfiguration"
    }

    data class YConfiguration(
        override val showAxis: Boolean,
        override val showLabels: Boolean,
        override val showGuidelines: Boolean,
        override val showAxisLine: Boolean,
        override val guidelines: GuidelinesConfiguration,
        override val labels: LabelsConfiguration,
        override val axisLine: AxisLineConfiguration.YConfiguration
    ) : Configuration {
        override fun toString() = "AxisConfiguration#YConfiguration"
    }

    fun xAxisConfigurationDefaults(
        showAxis: Boolean = true,
        showLabels: Boolean = true,
        showGuidelines: Boolean = true,
        showAxisLine: Boolean = true,
        guidelines: GuidelinesConfiguration = GuidelinesConfigurationDefaults.guidelinesConfigurationDefaults(),
        labels: LabelsConfiguration = LabelsConfigurationDefaults.labelsConfigurationDefault(),
        axisLine: AxisLineConfiguration.XConfiguration = AxisLineConfiguration.xAxisLineConfigurationDefaults()
    ) = XConfiguration(
        showAxis = showAxis,
        showLabels = showLabels,
        showGuidelines = showGuidelines,
        showAxisLine = showAxisLine,
        guidelines = guidelines,
        labels = labels,
        axisLine = axisLine
    )

    fun yAxisConfigurationDefaults(
        showAxis: Boolean = true,
        showLabels: Boolean = true,
        showGuidelines: Boolean = true,
        showAxisLine: Boolean = true,
        guidelines: GuidelinesConfiguration = GuidelinesConfigurationDefaults.guidelinesConfigurationDefaults(),
        labels: LabelsConfiguration = LabelsConfigurationDefaults.labelsConfigurationDefault(),
        axisLine: AxisLineConfiguration.YConfiguration = AxisLineConfiguration.yAxisLineConfigurationDefaults()
    ) = YConfiguration(
        showAxis = showAxis,
        showLabels = showLabels,
        showGuidelines = showGuidelines,
        showAxisLine = showAxisLine,
        guidelines = guidelines,
        labels = labels,
        axisLine = axisLine
    )
}

///**
// * This class defines all available continuous axis properties.
// *
// * @property showAxis if true, the axis will be drawn.
// * @property showLabels if true, labels for the axis will be drawn.
// * @property showGuidelines if true, guidelines will be drawn.
// * @property showAxisLine if true, a line will be drawn along the axis.
// * @property guidelines provides [GuidelinesConfiguration].
// * @property labels provides [LabelsConfiguration].
// * @property axisLine provides [AxisLineConfig].
// * @constructor Creates a configuration for a continuous axis.
// */
//
//data class ContinuousAxisConfig(
//    val showAxis: Boolean,
//    val showLabels: Boolean,
//    val showGuidelines: Boolean,
//    val showAxisLine: Boolean,
//    val guidelines: GuidelinesConfiguration,
//    val labels: LabelsConfiguration,
//    val axisLine: AxisLineConfig
//)
//
///** Contains default values used for implementations of [ContinuousAxisConfig] */
//object ContinuousAxisConfigDefaults {
//
//    /**
//     * Creates a [ContinuousAxisConfig] for basic continuous axis implementations.
//     *
//     * @param showAxis true if axis should be drawn. Defaults to true.
//     * @param showLabels true if labels should be drawn. Defaults to true.
//     * @param showGuidelines true if guidelines should be drawn. Defaults to false.
//     * @param showAxisLine true if a line should be drawn along the axis. Defaults to true.
//     * @param guidelines provides [GuidelinesConfiguration].
//     * @param labels provides [LabelsConfiguration].
//     * @param axisLine provides [AxisLineConfig].
//     * @return the resulting [ContinuousAxisConfig] to be applied to a chart.
//     */
//    fun continuousAxisConfigDefaults(
//        showAxis: Boolean = true,
//        showLabels: Boolean = true,
//        showGuidelines: Boolean = false,
//        showAxisLine: Boolean = true,
//        guidelines: GuidelinesConfiguration = GuidelinesConfigurationDefaults.guidelinesConfigurationDefaults(),
//        labels: LabelsConfiguration = LabelsConfigurationDefaults.labelsConfigurationDefault(),
//        axisLine: AxisLineConfig = AxisLineConfigDefaults.axisLineConfigDefaults()
//    ): AxisConfiguration.XConfiguration =
//        AxisConfiguration.XConfiguration(
//            showAxis = showAxis,
//            showLabels = showLabels,
//            showGuidelines = showGuidelines,
//            showAxisLine = showAxisLine,
//            guidelines = guidelines,
//            labels = labels,
//            axisLine = axisLine
//        )
//}
