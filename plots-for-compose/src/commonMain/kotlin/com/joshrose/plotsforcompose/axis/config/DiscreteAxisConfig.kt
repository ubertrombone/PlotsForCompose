package com.joshrose.plotsforcompose.axis.config

import com.joshrose.plotsforcompose.axis.config.axisline.AxisLineConfig
import com.joshrose.plotsforcompose.axis.config.axisline.AxisLineConfigDefaults
import com.joshrose.plotsforcompose.axis.config.guidelines.GuidelinesConfig
import com.joshrose.plotsforcompose.axis.config.guidelines.GuidelinesConfigDefaults
import com.joshrose.plotsforcompose.axis.config.labels.ContinuousLabelsConfig
import com.joshrose.plotsforcompose.axis.config.labels.DiscreteLabelsConfig
import com.joshrose.plotsforcompose.axis.config.labels.DiscreteLabelsConfigDefaults

/**
 * This class defines all available discrete axis properties.
 *
 * @property showAxis if true, the axis will be drawn.
 * @property showLabels if true, labels for the axis will be drawn.
 * @property showGuidelines if true, guidelines will be drawn.
 * @property showAxisLine if true, a line will be drawn along the axis.
 * @property guidelines provides [GuidelinesConfig].
 * @property labels provides [ContinuousLabelsConfig].
 * @property axisLine provides [AxisLineConfig].
 * @constructor Creates a configuration for a continuous axis.
 */
data class DiscreteAxisConfig(
    val showAxis: Boolean,
    val showLabels: Boolean,
    val showGuidelines: Boolean,
    val showAxisLine: Boolean,
    val guidelines: GuidelinesConfig,
    val labels: DiscreteLabelsConfig,
    val axisLine: AxisLineConfig
)

/** Contains default values used for implementations of [DiscreteAxisConfig] */
object DiscreteAxisConfigDefaults {

    /**
     * Creates a [DiscreteAxisConfig] for basic discrete axis implementations.
     *
     * @param showAxis true if axis should be drawn. Defaults to true.
     * @param showLabels true if labels should be drawn. Defaults to true.
     * @param showGuidelines true if guidelines should be drawn. Defaults to false.
     * @param showAxisLine true if a line should be drawn along the axis. Defaults to true.
     * @param guidelines provides [GuidelinesConfig].
     * @param labels provides [DiscreteLabelsConfig].
     * @param axisLine provides [AxisLineConfig].
     * @return the resulting [DiscreteAxisConfig] to be applied to a chart.
     */
    fun discreteAxisConfigDefaults(
        showAxis: Boolean = true,
        showLabels: Boolean = true,
        showGuidelines: Boolean = false,
        showAxisLine: Boolean = true,
        guidelines: GuidelinesConfig = GuidelinesConfigDefaults.guidelinesConfigDefaults(),
        labels: DiscreteLabelsConfig = DiscreteLabelsConfigDefaults.discreteLabelsConfigDefaults(),
        axisLine: AxisLineConfig = AxisLineConfigDefaults.axisLineConfigDefaults()
    ): DiscreteAxisConfig =
        DiscreteAxisConfig(
            showAxis = showAxis,
            showLabels = showLabels,
            showGuidelines = showGuidelines,
            showAxisLine = showAxisLine,
            guidelines = guidelines,
            labels = labels,
            axisLine = axisLine
        )
}
