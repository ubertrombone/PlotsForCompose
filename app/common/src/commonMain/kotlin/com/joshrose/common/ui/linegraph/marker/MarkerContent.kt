package com.joshrose.common.ui.linegraph.marker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.joshrose.common.components.linegraph.marker.MarkerComponent

@Composable
fun MarkerContent(component: MarkerComponent, modifier: Modifier) {
    val marker by component.markerStates.subscribeAsState()


}