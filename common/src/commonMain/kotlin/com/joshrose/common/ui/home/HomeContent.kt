package com.joshrose.common.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.joshrose.common.home.HomeComponent

@Composable
internal fun HomeContent(component: HomeComponent, modifier: Modifier = Modifier) {
    val childrenList: HomeComponent.Children by component.children.subscribeAsState()

    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(childrenList.children) { child ->
            val isSelected = child.id == childrenList.selectedChildId

            Text(
                text = child.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = isSelected,
                        onClick = { component.onChildClicked(id = child.id) }
                    )
                    .run { if (isSelected) background(color = Color.LightGray) else this}
                    .padding(16.dp)
            )

            Divider()
        }
    }
}