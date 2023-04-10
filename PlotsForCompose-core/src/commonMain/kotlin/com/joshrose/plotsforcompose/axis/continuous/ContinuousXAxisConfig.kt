package com.joshrose.plotsforcompose.axis.continuous

import com.joshrose.plotsforcompose.axis.guidelines.GuidelinesConfig
import com.joshrose.plotsforcompose.axis.guidelines.GuidelinesConfigDefaults
import com.joshrose.plotsforcompose.axis.labels.LabelsConfig
import com.joshrose.plotsforcompose.axis.labels.LabelsConfigDefaults

/**
 * This class defines all available continuous x-axis properties.
 *
 * @property showAxis if true, the axis will be drawn.
 * @property showLabels if true, labels for the axis will be drawn.
 * @property showGuidelines if true, guidelines will be drawn.
 * @property guidelines provides [GuidelinesConfig].
 * @property labels provides [LabelsConfig].
 * @constructor Creates a configuration for a continuous x-axis.
 */
data class ContinuousXAxisConfig(
    val showAxis: Boolean,
    val showLabels: Boolean,
    val showGuidelines: Boolean,
    val guidelines: GuidelinesConfig,
    val labels: LabelsConfig
)

/** Contains default values used for implementations of [ContinuousXAxisConfig] */
object ContinuousXAxisConfigDefaults {

    /**
     * Creates a [ContinuousXAxisConfig] for basic continuous x-axis implementations using a light theme.
     *
     * @param showAxis true if axis should be drawn. Defaults to true.
     * @param showLabels true if labels should be drawn. Defaults to true.
     * @param showGuidelines true if guidelines should be drawn. Defaults to false.
     * @param guidelines provides [GuidelinesConfig].
     * @param labels provides [LabelsConfig].
     * @return the resulting [ContinuousXAxisConfig] to be applied to a chart.
     */
    fun lightThemeContinuousXAxisConfigDefaults(
        showAxis: Boolean = true,
        showLabels: Boolean = true,
        showGuidelines: Boolean = false,
        guidelines: GuidelinesConfig = GuidelinesConfigDefaults.lightThemeGuidelinesConfigDefaults(),
        labels: LabelsConfig = LabelsConfigDefaults.lightThemeLabelsConfigDefaults()
    ): ContinuousXAxisConfig =
        ContinuousXAxisConfig(
            showAxis = showAxis,
            showLabels = showLabels,
            showGuidelines = showGuidelines,
            guidelines = guidelines,
            labels = labels
        )

    /**
     * Creates a [ContinuousXAxisConfig] for basic continuous x-axis implementations using a dark theme.
     *
     * @param showAxis true if axis should be drawn. Defaults to true.
     * @param showLabels true if labels should be drawn. Defaults to true.
     * @param showGuidelines true if guidelines should be drawn. Defaults to false.
     * @param guidelines provides [GuidelinesConfig].
     * @param labels provides [LabelsConfig].
     * @return the resulting [ContinuousXAxisConfig] to be applied to a chart.
     */
    fun darkThemeContinuousXAxisConfigDefaults(
        showAxis: Boolean = true,
        showLabels: Boolean = true,
        showGuidelines: Boolean = false,
        guidelines: GuidelinesConfig = GuidelinesConfigDefaults.darkThemeGuidelinesConfigDefaults(),
        labels: LabelsConfig = LabelsConfigDefaults.darkThemeLabelsConfigDefaults()
    ): ContinuousXAxisConfig =
        ContinuousXAxisConfig(
            showAxis = showAxis,
            showLabels = showLabels,
            showGuidelines = showGuidelines,
            guidelines = guidelines,
            labels = labels
        )
}
