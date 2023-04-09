package com.joshrose.common.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.joshrose.common.home.HomeComponent

@Composable
internal fun HomeContent(component: HomeComponent, modifier: Modifier = Modifier) {
    val childrenList: HomeComponent.Names by component.children.subscribeAsState()

    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(childrenList.name) { child ->
            Text(
                text = child.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { component.onChildClicked(name = child) }
                    .padding(16.dp)
            )

            Divider()
        }
    }
}