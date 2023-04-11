package com.joshrose.plotsforcompose.axis

import com.joshrose.plotsforcompose.axis.guidelines.GuidelinesConfig
import com.joshrose.plotsforcompose.axis.guidelines.GuidelinesConfigDefaults
import com.joshrose.plotsforcompose.axis.labels.ContinuousLabelsConfig
import com.joshrose.plotsforcompose.axis.labels.DiscreteLabelsConfig
import com.joshrose.plotsforcompose.axis.labels.DiscreteLabelsConfigDefaults

/**
 * This class defines all available discrete axis properties.
 *
 * @property showAxis if true, the axis will be drawn.
 * @property showLabels if true, labels for the axis will be drawn.
 * @property showGuidelines if true, guidelines will be drawn.
 * @property guidelines provides [GuidelinesConfig].
 * @property labels provides [ContinuousLabelsConfig].
 * @constructor Creates a configuration for a continuous axis.
 */
data class DiscreteAxisConfig(
    val showAxis: Boolean,
    val showLabels: Boolean,
    val showGuidelines: Boolean,
    val guidelines: GuidelinesConfig,
    val labels: DiscreteLabelsConfig
)

/** Contains default values used for implementations of [DiscreteAxisConfig] */
object DiscreteAxisConfigDefaults {

    /**
     * Creates a [DiscreteAxisConfig] for basic discrete axis implementations.
     *
     * @param showAxis true if axis should be drawn. Defaults to true.
     * @param showLabels true if labels should be drawn. Defaults to true.
     * @param showGuidelines true if guidelines should be drawn. Defaults to false.
     * @param guidelines provides [GuidelinesConfig].
     * @param labels provides [DiscreteLabelsConfig].
     * @return the resulting [DiscreteAxisConfig] to be applied to a chart.
     */
    fun discreteAxisConfigDefaults(
        showAxis: Boolean = true,
        showLabels: Boolean = true,
        showGuidelines: Boolean = false,
        guidelines: GuidelinesConfig = GuidelinesConfigDefaults.guidelinesConfigDefaults(),
        labels: DiscreteLabelsConfig = DiscreteLabelsConfigDefaults.discreteLabelsConfigDefaults()
    ): DiscreteAxisConfig =
        DiscreteAxisConfig(
            showAxis = showAxis,
            showLabels = showLabels,
            showGuidelines = showGuidelines,
            guidelines = guidelines,
            labels = labels
        )
}
