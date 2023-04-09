package com.joshrose.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import com.arkivanov.decompose.defaultComponentContext
import com.joshrose.common.App
import com.joshrose.common.components.root.DefaultRootComponent
import com.joshrose.plotsforcompose.theme.PlotsForComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val root = DefaultRootComponent(componentContext = defaultComponentContext())

        setContent {
            PlotsForComposeTheme(
                darkTheme = isSystemInDarkTheme(),
                dynamicColor = true
            ) {
                App(root)
            }
        }
    }
}