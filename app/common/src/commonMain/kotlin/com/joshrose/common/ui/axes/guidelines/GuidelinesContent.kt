package com.joshrose.common.ui.axes.guidelines

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.joshrose.common.components.axes.guidelines.GuidelinesComponent

@Composable
fun GuidelinesContent(
    component: GuidelinesComponent,
    xEnabled: Boolean = true,
    yEnabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    val xGuidelinesStates by component.xGuidelinesStates.subscribeAsState()
    val yGuidelinesStates by component.yGuidelinesState.subscribeAsState()

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        GuidelinesCard(
            label = "X Guidelines",
            alpha = xGuidelinesStates.alpha,
            strokeWidth = xGuidelinesStates.strokeWidth,
            padding = xGuidelinesStates.padding,
            enabled = xEnabled,
            incAlphaClick = component::incGuidelinesAlphaX,
            decAlphaClick = component::decGuidelinesAlphaX,
            incStrokeWidthClick = component::incGuidelinesStrokeWidthX,
            decStrokeWidthClick = component::decGuidelinesStrokeWidthX,
            incPaddingClick = component::incGuidelinesPaddingX,
            decPaddingClick = component::decGuidelinesPaddingX,
            modifier = Modifier
                .fillMaxHeight()
                .width(IntrinsicSize.Max)
                .padding(10.dp)
        )
        GuidelinesCard(
            label = "Y Guidelines",
            alpha = yGuidelinesStates.alpha,
            strokeWidth = yGuidelinesStates.strokeWidth,
            padding = yGuidelinesStates.padding,
            enabled = yEnabled,
            incAlphaClick = component::incGuidelinesAlphaY,
            decAlphaClick = component::decGuidelinesAlphaY,
            incStrokeWidthClick = component::incGuidelinesStrokeWidthY,
            decStrokeWidthClick = component::decGuidelinesStrokeWidthY,
            incPaddingClick = component::incGuidelinesPaddingY,
            decPaddingClick = component::decGuidelinesPaddingY,
            modifier = Modifier
                .fillMaxHeight()
                .width(IntrinsicSize.Max)
                .padding(10.dp)
        )
    }
}