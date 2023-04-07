package com.joshrose.common.ui.root

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.joshrose.common.root.RootComponent
import com.joshrose.common.ui.home.HomeContent
import com.joshrose.common.ui.linegraph.LineGraphContent

@Composable
fun RootContent(
    component: RootComponent,
    modifier: Modifier = Modifier
) {
    val childStack by component.childStack.subscribeAsState()

    Column(modifier = modifier) {
        Children(
            stack = childStack,
            modifier = Modifier.weight(1f)
        ) {
            when (val child = it.instance) {
                is RootComponent.Child.HomeChild -> HomeContent(component = child.component, modifier = Modifier.fillMaxSize())
                is RootComponent.Child.LineGraphChild -> LineGraphContent(component = child.component, modifier = Modifier.fillMaxSize())
            }
        }
    }
}