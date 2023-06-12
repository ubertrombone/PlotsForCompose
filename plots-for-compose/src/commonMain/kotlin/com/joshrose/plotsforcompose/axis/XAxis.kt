package com.joshrose.plotsforcompose.axis

import com.joshrose.plotsforcompose.axis.config.axisline.AxisLineConfiguration.XConfiguration
import com.joshrose.plotsforcompose.axis.config.guidelines.GuidelinesConfiguration
import com.joshrose.plotsforcompose.axis.config.labels.LabelsConfiguration
import com.joshrose.plotsforcompose.internals.Scale
import com.joshrose.plotsforcompose.internals.ScaleKind

fun unboundXAxis(
    labelConfigs: LabelsConfiguration = LabelsConfiguration(),
    guidelinesConfigs: GuidelinesConfiguration = GuidelinesConfiguration(),
    axisLineConfigs: XConfiguration = XConfiguration(),
    breaks: List<Number>? = null,
    labels: List<String>? = null,
    naValue: Number? = null,
    reverse: Boolean? = null
) = Scale(
    labelConfigs = labelConfigs,
    guidelinesConfigs = guidelinesConfigs,
    axisLineConfigs = axisLineConfigs,
    scale = ScaleKind.X,
    breaks = breaks,
    labels = labels,
    naValue = naValue,
    reverse = reverse
)