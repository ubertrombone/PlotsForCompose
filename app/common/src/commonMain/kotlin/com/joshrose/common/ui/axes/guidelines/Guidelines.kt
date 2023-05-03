package com.joshrose.common.ui.axes.guidelines

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joshrose.common.components.axes.AxesComponent

@Composable
fun Guidelines(
    component: AxesComponent,
    modifier: Modifier = Modifier
) {
    val xGuidelinesStates by component.showXGuidelines.collectAsState()
    val yGuidelinesStates by component.showYGuidelines.collectAsState()

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        GuidelinesColumn(
            label = "X Guidelines",
            strokeWidth = xGuidelinesStates.strokeWidth.value,
            alpha = xGuidelinesStates.alpha,
            incStrokeWidthClick = component::incGuidelinesStrokeWidthX,
            decStrokeWidthClick = component::decGuidelinesStrokeWidthX,
            incAlphaClick = component::incGuidelinesAlphaX,
            decAlphaClick = component::decGuidelinesAlphaX,
            modifier = Modifier
                .fillMaxHeight()
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(10.dp)
        )
        GuidelinesColumn(
            label = "Y Guidelines",
            strokeWidth = yGuidelinesStates.strokeWidth.value,
            alpha = yGuidelinesStates.alpha,
            incStrokeWidthClick = component::incGuidelinesStrokeWidthY,
            decStrokeWidthClick = component::decGuidelinesStrokeWidthY,
            incAlphaClick = component::incGuidelinesAlphaY,
            decAlphaClick = component::decGuidelinesAlphaY,
            modifier = Modifier
                .fillMaxHeight()
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(10.dp)
        )
    }
}
