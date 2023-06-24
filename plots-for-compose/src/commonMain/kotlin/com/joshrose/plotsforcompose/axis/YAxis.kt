package com.joshrose.plotsforcompose.axis

import com.joshrose.plotsforcompose.axis.config.axisline.AxisLineConfiguration.YConfiguration
import com.joshrose.plotsforcompose.axis.config.guidelines.GuidelinesConfiguration
import com.joshrose.plotsforcompose.axis.config.labels.LabelsConfiguration
import com.joshrose.plotsforcompose.internals.Scale
import com.joshrose.plotsforcompose.internals.ScaleKind
import com.joshrose.plotsforcompose.util.Proportional

fun unboundYAxis(
    labelConfigs: LabelsConfiguration = LabelsConfiguration(),
    guidelinesConfigs: GuidelinesConfiguration = GuidelinesConfiguration(),
    axisLineConfigs: YConfiguration = YConfiguration(), // If this is ever nullable, update LineCount
    breaks: Proportional? = null,
    labels: Proportional? = null,
    naValue: Number? = null,
    reverse: Boolean? = null
) = Scale(
    labelConfigs = labelConfigs,
    guidelinesConfigs = guidelinesConfigs,
    axisLineConfigs = axisLineConfigs,
    scale = ScaleKind.Y,
    breaks = breaks,
    labels = labels,
    naValue = naValue,
    reverse = reverse
)