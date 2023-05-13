package com.joshrose.common.ui.axes.labels

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.arkivanov.decompose.value.Value
import com.joshrose.common.components.axes.labels.LabelsComponent
import com.joshrose.common.components.axes.models.DataValueStates
import com.joshrose.plotsforcompose.axis.config.util.Multiplier

@Composable
fun LabelsContent(
    component: LabelsComponent,
    data: Value<DataValueStates>,
    xEnabled: Boolean = true,
    yEnabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    val xLabelsStates by component.xLabelsState.subscribeAsState()
    val yLabelsStates by component.yLabelsState.subscribeAsState()
    val allData by data.subscribeAsState()

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        LabelsCard(
            label = "X Axis Labels",
            rotation = xLabelsStates.rotation,
            axisOffset = xLabelsStates.axisOffset,
            breaks = xLabelsStates.breaks,
            rangeAdjustment =
                if (allData.minXValue == 0f || allData.maxXValue == 0f) Multiplier(0f)
                else xLabelsStates.rangeAdjustment,
            rangeEnabled = allData.minXValue != 0f && allData.maxXValue != 0f,
            maxAdjustment = xLabelsStates.maxValueAdjustment,
            minAdjustment = xLabelsStates.minValueAdjustment,
            enabled = xEnabled,
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
            label = "Y Axis Labels",
            rotation = yLabelsStates.rotation,
            axisOffset = yLabelsStates.axisOffset,
            breaks = yLabelsStates.breaks,
            rangeAdjustment =
                if (allData.minYValue == 0f || allData.maxYValue == 0f) Multiplier(0f)
                else yLabelsStates.rangeAdjustment,
            rangeEnabled = allData.minYValue != 0f && allData.maxYValue != 0f,
            maxAdjustment = yLabelsStates.maxValueAdjustment,
            minAdjustment = yLabelsStates.minValueAdjustment,
            enabled = yEnabled,
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