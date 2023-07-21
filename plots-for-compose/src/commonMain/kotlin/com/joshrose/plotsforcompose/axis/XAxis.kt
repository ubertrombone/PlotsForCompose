package com.joshrose.plotsforcompose.axis

import com.joshrose.plotsforcompose.axis.config.axisline.AxisLineConfiguration.XConfiguration
import com.joshrose.plotsforcompose.axis.config.guidelines.GuidelinesConfiguration
import com.joshrose.plotsforcompose.axis.config.labels.LabelsConfiguration
import com.joshrose.plotsforcompose.internals.Scale
import com.joshrose.plotsforcompose.internals.ScaleKind
import com.joshrose.plotsforcompose.util.Proportional

fun xAxis(
    showGuidelines: Boolean = true,
    showLabels: Boolean = true,
    showAxisLine: Boolean = true,
    labelConfigs: LabelsConfiguration = LabelsConfiguration(),
    guidelinesConfigs: GuidelinesConfiguration = GuidelinesConfiguration(),
    axisLineConfigs: XConfiguration = XConfiguration(),
    breaks: Proportional? = null,
    labels: Proportional? = null
) = Scale(
    showGuidelines = showGuidelines,
    showLabels = showLabels,
    showAxisLine = showAxisLine,
    labelConfigs = labelConfigs,
    guidelinesConfigs = guidelinesConfigs,
    axisLineConfigs = axisLineConfigs,
    scale = ScaleKind.X,
    breaks = breaks,
    labels = labels
)