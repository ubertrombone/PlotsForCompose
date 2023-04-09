package com.joshrose.common.ui.root

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.joshrose.common.components.root.RootComponent
import com.joshrose.common.ui.home.HomeContent
import com.joshrose.common.ui.linegraph.LineGraphContent
import com.joshrose.common.util.ScreenNames.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RootContent(
    component: RootComponent,
    modifier: Modifier = Modifier
) {
    val childStack by component.childStack.subscribeAsState()
    var title by remember { mutableStateOf(HOME.title) }
    var isBackEnabled by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.headlineMedium
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
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { padding ->
        Column(modifier = modifier.padding(padding)) {
            Children(
                stack = childStack
            ) {
                when (val child = it.instance) {
                    is RootComponent.Child.HomeChild -> {
                        title = child.component.screenProperties.title
                        isBackEnabled = child.component.screenProperties.isBackEnabled
                        HomeContent(component = child.component, modifier = Modifier.fillMaxSize())
                    }

                    is RootComponent.Child.LineGraphChild -> {
                        title = child.component.screenProperties.title
                        isBackEnabled = child.component.screenProperties.isBackEnabled
                        LineGraphContent(component = child.component, modifier = Modifier.fillMaxSize())
                    }
                }
            }
        }
    }
}