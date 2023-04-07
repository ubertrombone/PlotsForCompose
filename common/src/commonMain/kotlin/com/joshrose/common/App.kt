package com.joshrose.common

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.joshrose.common.root.RootComponent
import com.joshrose.common.ui.root.RootContent

@Composable
fun App(root: RootComponent) {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.LightGray) {
        RootContent(component = root, modifier = Modifier.fillMaxSize())
    }
}
