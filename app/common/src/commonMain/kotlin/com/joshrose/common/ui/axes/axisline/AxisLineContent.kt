package com.joshrose.common.ui.axes.axisline

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.joshrose.common.components.axes.axisline.AxisLineComponent

@Composable
fun AxisLineContent(
    component: AxisLineComponent,
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
            incAlphaClick = component::incAlphaX,
            decAlphaClick = component::decAlphaX,
            incStrokeWidthClick = component::incStrokeWidthX,
            decStrokeWidthClick = component::decStrokeWidthX,
            onCheckClick = component::updateShowTicksX,
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
            incAlphaClick = component::incAlphaY,
            decAlphaClick = component::decAlphaY,
            incStrokeWidthClick = component::incStrokeWidthY,
            decStrokeWidthClick = component::decStrokeWidthY,
            onCheckClick = component::updateShowTicksY,
            modifier = Modifier
                .fillMaxHeight()
                .width(IntrinsicSize.Max)
                .padding(10.dp)
        )
    }
}