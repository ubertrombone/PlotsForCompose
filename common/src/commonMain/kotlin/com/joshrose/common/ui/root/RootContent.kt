package com.joshrose.common.ui.root

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.joshrose.common.root.RootComponent
import com.joshrose.common.ui.home.HomeContent
import com.joshrose.common.ui.linegraph.LineGraphContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RootContent(
    component: RootComponent,
    modifier: Modifier = Modifier
) {
    val childStack by component.childStack.subscribeAsState()
    var title by remember { mutableStateOf("PlotsForCompose") }
    var isBackEnabled by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = title,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 20.sp
                    )
                },
                navigationIcon = {
                    if (isBackEnabled) {
                        IconButton(onClick = component::onBackPressed) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = null
                            )
                        }
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = modifier.padding(padding)) {
            Children(
                stack = childStack
            ) {
                when (val child = it.instance) {
                    is RootComponent.Child.HomeChild -> {
                        title = child.component.homeName
                        isBackEnabled = child.component.isBackEnabled
                        HomeContent(component = child.component, modifier = Modifier.fillMaxSize())
                    }
                    is RootComponent.Child.LineGraphChild -> {
                        title = child.component.lineGraphName
                        isBackEnabled = child.component.isBackEnabled
                        LineGraphContent(component = child.component, modifier = Modifier.fillMaxSize())
                    }
                }
            }
        }
    }
}