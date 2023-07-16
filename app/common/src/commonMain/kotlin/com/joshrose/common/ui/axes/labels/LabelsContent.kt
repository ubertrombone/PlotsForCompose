package com.joshrose.common.ui.axes.labels

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.arkivanov.decompose.value.Value
import com.joshrose.common.components.axes.breaks.BreaksComponent
import com.joshrose.common.components.axes.labels.LabelsComponent
import com.joshrose.common.components.axes.models.DataValueStates
import com.joshrose.plotsforcompose.axis.config.util.Multiplier

@Composable
fun LabelsContent(
    labelsComponent: LabelsComponent,
    breaksComponent: BreaksComponent,
    data: Value<DataValueStates>,
    xEnabled: Boolean = true,
    yEnabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    val xLabelsStates by labelsComponent.xLabelsState.subscribeAsState()
    val yLabelsStates by labelsComponent.yLabelsState.subscribeAsState()
    val xBreakStates by breaksComponent.xBreaksState.subscribeAsState()
    val yBreakStates by breaksComponent.yBreakState.subscribeAsState()
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
            breaks = xBreakStates.breaks,
            labels = xBreakStates.labels,
            rangeAdjustment =
                if (allData.minXValue == 0f || allData.maxXValue == 0f) Multiplier(0f)
                else xLabelsStates.rangeAdjustment,
            rangeEnabled = allData.minXValue != 0f && allData.maxXValue != 0f,
            maxAdjustment = xLabelsStates.maxValueAdjustment,
            minAdjustment = xLabelsStates.minValueAdjustment,
            enabled = xEnabled,
            incRotationClick = labelsComponent::incRotationX,
            decRotationClick = labelsComponent::decRotationX,
            incAxisOffsetClick = labelsComponent::incOffsetX,
            decAxisOffsetClick = labelsComponent::decOffsetX,
            incBreaksClick = breaksComponent::incBreaksX,
            decBreaksClick = breaksComponent::decBreaksX,
            incLabelsClick = breaksComponent::incLabelsX,
            decLabelsClick = breaksComponent::decLabelsX,
            incRangeAdjClick = labelsComponent::incRangeAdjustmentX,
            decRangeAdjClick = labelsComponent::decRangeAdjustmentX,
            incMaxAdjClick = labelsComponent::incMaxAdjustmentX,
            decMaxAdjClick = labelsComponent::decMaxAdjustmentX,
            incMinAdjClick = labelsComponent::incMinAdjustmentX,
            decMinAdjClick = labelsComponent::decMinAdjustmentX,
            modifier = Modifier
                .fillMaxHeight()
                .width(IntrinsicSize.Max)
                .padding(10.dp)
        )
        LabelsCard(
            label = "Y Axis Labels",
            rotation = yLabelsStates.rotation,
            axisOffset = yLabelsStates.axisOffset,
            breaks = yBreakStates.breaks,
            labels = yBreakStates.labels,
            rangeAdjustment =
                if (allData.minYValue == 0f || allData.maxYValue == 0f) Multiplier(0f)
                else yLabelsStates.rangeAdjustment,
            rangeEnabled = allData.minYValue != 0f && allData.maxYValue != 0f,
            maxAdjustment = yLabelsStates.maxValueAdjustment,
            minAdjustment = yLabelsStates.minValueAdjustment,
            enabled = yEnabled,
            incRotationClick = labelsComponent::incRotationY,
            decRotationClick = labelsComponent::decRotationY,
            incAxisOffsetClick = labelsComponent::incOffsetY,
            decAxisOffsetClick = labelsComponent::decOffsetY,
            incBreaksClick = breaksComponent::incBreaksY,
            decBreaksClick = breaksComponent::decBreaksY,
            incLabelsClick = breaksComponent::incLabelsY,
            decLabelsClick = breaksComponent::decLabelsY,
            incRangeAdjClick = labelsComponent::incRangeAdjustmentY,
            decRangeAdjClick = labelsComponent::decRangeAdjustmentY,
            incMaxAdjClick = labelsComponent::incMaxAdjustmentY,
            decMaxAdjClick = labelsComponent::decMaxAdjustmentY,
            incMinAdjClick = labelsComponent::incMinAdjustmentY,
            decMinAdjClick = labelsComponent::decMinAdjustmentY,
            modifier = Modifier
                .fillMaxHeight()
                .width(IntrinsicSize.Max)
                .padding(10.dp)
        )
    }
}