package com.joshrose.common.ui.axes.axisline

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.joshrose.common.components.axes.axisline.AxisLineComponent
import com.joshrose.plotsforcompose.axis.util.AxisPosition

@Composable
fun AxisLineContent(
    component: AxisLineComponent,
    xEnabled: Boolean = true,
    yEnabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    val xAxisLineStates by component.xAxisLineState.subscribeAsState()
    val yAxisLineStates by component.yAxisLineState.subscribeAsState()

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        AxisLineCard(
            label = "X Axis Lines",
            alpha = xAxisLineStates.alpha,
            strokeWidth = xAxisLineStates.strokeWidth,
            checked = xAxisLineStates.ticks,
            orientation = AxisPosition.Companion.Orientation.X,
            axisPosition = xAxisLineStates.axisPosition,
            enabled = xEnabled,
            incAlphaClick = component::incAlphaX,
            decAlphaClick = component::decAlphaX,
            incStrokeWidthClick = component::incStrokeWidthX,
            decStrokeWidthClick = component::decStrokeWidthX,
            onCheckClick = component::updateShowTicksX,
            onAlignmentClick = component::updateAxisPositionX,
            modifier = Modifier
                .fillMaxHeight()
                .width(IntrinsicSize.Max)
                .padding(10.dp)
        )
        AxisLineCard(
            label = "Y Axis Lines",
            alpha = yAxisLineStates.alpha,
            strokeWidth = yAxisLineStates.strokeWidth,
            checked = yAxisLineStates.ticks,
            orientation = AxisPosition.Companion.Orientation.Y,
            axisPosition = yAxisLineStates.axisPosition,
            enabled = yEnabled,
            incAlphaClick = component::incAlphaY,
            decAlphaClick = component::decAlphaY,
            incStrokeWidthClick = component::incStrokeWidthY,
            decStrokeWidthClick = component::decStrokeWidthY,
            onCheckClick = component::updateShowTicksY,
            onAlignmentClick = component::updateAxisPositionY,
            modifier = Modifier
                .fillMaxHeight()
                .width(IntrinsicSize.Max)
                .padding(10.dp)
        )
    }
}