package com.joshrose.common

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.joshrose.common.components.root.RootComponent
import com.joshrose.common.ui.root.RootContent

@Composable
fun PlotsForComposeApp(root: RootComponent) {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        RootContent(component = root, modifier = Modifier.fillMaxSize())
    }
}
