package com.joshrose.common.ui.axes.visibility

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.joshrose.common.components.axes.visibility.VisibilityComponent

@Composable
fun VisibilityContent(
    component: VisibilityComponent,
    modifier: Modifier = Modifier
) {
    val xVisibilityStates by component.xVisibilityStates.subscribeAsState()
    val yVisibilityStates by component.yVisibilityStates.subscribeAsState()

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        VisibilityCard(
            label = "X Visibility",
            axisSelected = xVisibilityStates.showAxis,
            axisLineSelected = xVisibilityStates.showAxisLine,
            guidelinesSelected = xVisibilityStates.showGuidelines,
            labelsSelected = xVisibilityStates.showLabels,
            axisOnClick = component::updateShowXAxis,
            axisLineOnClick = component::updateShowXAxisLine,
            guidelinesOnClick = component::updateShowXGuidelines,
            labelsOnClick = component::updateShowXLabels,
            modifier = Modifier
                .fillMaxHeight()
                .width(IntrinsicSize.Max)
                .padding(10.dp)
        )
        VisibilityCard(
            label = "Y Visibility",
            axisSelected = yVisibilityStates.showAxis,
            axisLineSelected = yVisibilityStates.showAxisLine,
            guidelinesSelected = yVisibilityStates.showGuidelines,
            labelsSelected = yVisibilityStates.showLabels,
            axisOnClick = component::updateShowYAxis,
            axisLineOnClick = component::updateShowYAxisLine,
            guidelinesOnClick = component::updateShowYGuidelines,
            labelsOnClick = component::updateShowYLabels,
            modifier = Modifier
                .fillMaxHeight()
                .width(IntrinsicSize.Max)
                .padding(10.dp)
        )
    }
}