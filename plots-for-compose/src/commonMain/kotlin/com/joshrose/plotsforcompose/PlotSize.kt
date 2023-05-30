package com.joshrose.plotsforcompose

import androidx.compose.ui.unit.Dp
import com.joshrose.plotsforcompose.internals.PlotSize
import com.joshrose.plotsforcompose.internals.Specifications

fun plotSize(width: Dp?, height: Dp?) = PlotSize(Specifications.Size(width, height))