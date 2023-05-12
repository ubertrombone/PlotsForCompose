package com.joshrose.common.ui.axes.labels

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.joshrose.common.components.axes.labels.LabelsComponent

@Composable
fun LabelsContent(
    component: LabelsComponent,
    modifier: Modifier = Modifier
) {
    val xLabelsStates by component.xLabelsState.subscribeAsState()
    val yLabelsStates by component.yLabelsState.subscribeAsState()

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        LabelsCard(
            label = "X Labels",
            rotation = xLabelsStates.rotation,
            axisOffset = xLabelsStates.axisOffset,
            breaks = xLabelsStates.breaks,
            rangeAdjustment = xLabelsStates.rangeAdjustment,
            maxAdjustment = xLabelsStates.maxValueAdjustment,
            minAdjustment = xLabelsStates.minValueAdjustment,
            incRotationClick = component::incRotationX,
            decRotationClick = component::decRotationX,
            incAxisOffsetClick = component::incOffsetX,
            decAxisOffsetClick = component::decOffsetX,
            incBreaksClick = component::incBreaksX,
            decBreaksClick = component::decBreaksX,
            incRangeAdjClick = component::incRangeAdjustmentX,
            decRangeAdjClick = component::decRangeAdjustmentX,
            incMaxAdjClick = component::incMaxAdjustmentX,
            decMaxAdjClick = component::decMaxAdjustmentX,
            incMinAdjClick = component::incMinAdjustmentX,
            decMinAdjClick = component::decMinAdjustmentX,
            modifier = Modifier
                .fillMaxHeight()
                .width(IntrinsicSize.Max)
                .padding(10.dp)
        )
        LabelsCard(
            label = "Y Labels",
            rotation = yLabelsStates.rotation,
            axisOffset = yLabelsStates.axisOffset,
            breaks = yLabelsStates.breaks,
            rangeAdjustment = yLabelsStates.rangeAdjustment,
            maxAdjustment = yLabelsStates.maxValueAdjustment,
            minAdjustment = yLabelsStates.minValueAdjustment,
            incRotationClick = component::incRotationY,
            decRotationClick = component::decRotationY,
            incAxisOffsetClick = component::incOffsetY,
            decAxisOffsetClick = component::decOffsetY,
            incBreaksClick = component::incBreaksY,
            decBreaksClick = component::decBreaksY,
            incRangeAdjClick = component::incRangeAdjustmentY,
            decRangeAdjClick = component::decRangeAdjustmentY,
            incMaxAdjClick = component::incMaxAdjustmentY,
            decMaxAdjClick = component::decMaxAdjustmentY,
            incMinAdjClick = component::incMinAdjustmentY,
            decMinAdjClick = component::decMinAdjustmentY,
            modifier = Modifier
                .fillMaxHeight()
                .width(IntrinsicSize.Max)
                .padding(10.dp)
        )
    }
}