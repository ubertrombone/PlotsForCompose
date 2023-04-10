package com.joshrose.plotsforcompose.axis.continuous

import com.joshrose.plotsforcompose.axis.guidelines.GuidelinesConfig
import com.joshrose.plotsforcompose.axis.guidelines.GuidelinesConfigDefaults
import com.joshrose.plotsforcompose.axis.labels.LabelsConfig
import com.joshrose.plotsforcompose.axis.labels.LabelsConfigDefaults

/**
 * This class defines all available continuous axis properties.
 *
 * @property showAxis if true, the axis will be drawn.
 * @property showLabels if true, labels for the axis will be drawn.
 * @property showGuidelines if true, guidelines will be drawn.
 * @property guidelines provides [GuidelinesConfig].
 * @property labels provides [LabelsConfig].
 * @constructor Creates a configuration for a continuous axis.
 */
data class ContinuousAxisConfig(
    val showAxis: Boolean,
    val showLabels: Boolean,
    val showGuidelines: Boolean,
    val guidelines: GuidelinesConfig,
    val labels: LabelsConfig
)

/** Contains default values used for implementations of [ContinuousAxisConfig] */
object ContinuousAxisConfigDefaults {

    /**
     * Creates a [ContinuousAxisConfig] for basic continuous axis implementations.
     *
     * @param isDarkTheme true if axis should be a dark theme.
     * @param showAxis true if axis should be drawn. Defaults to true.
     * @param showLabels true if labels should be drawn. Defaults to true.
     * @param showGuidelines true if guidelines should be drawn. Defaults to false.
     * @return the resulting [ContinuousAxisConfig] to be applied to a chart.
     */
    fun continuousAxisConfigDefaults(
        isDarkTheme: Boolean,
        showAxis: Boolean = true,
        showLabels: Boolean = true,
        showGuidelines: Boolean = false,
    ): ContinuousAxisConfig =
        ContinuousAxisConfig(
            showAxis = showAxis,
            showLabels = showLabels,
            showGuidelines = showGuidelines,
            guidelines = GuidelinesConfigDefaults.guidelinesConfigDefaults(isDarkTheme),
            labels = LabelsConfigDefaults.labelsConfigDefaults(isDarkTheme)
        )
}
