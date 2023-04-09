package com.joshrose.common.ui.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.joshrose.common.home.HomeComponent

@Composable
internal fun HomeContent(component: HomeComponent, modifier: Modifier = Modifier) {
    val childrenList: HomeComponent.Names by component.children.subscribeAsState()

    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(childrenList.name) { child ->
            ChildCard(child = child) { component.onChildClicked(name = child) }
        }
    }
}