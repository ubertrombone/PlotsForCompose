package com.joshrose.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.arkivanov.decompose.defaultComponentContext
import com.joshrose.common.App
import com.joshrose.common.root.DefaultRootComponent
import com.roseFinancials.lenafx.ui.theme.PlotsForComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val root = DefaultRootComponent(componentContext = defaultComponentContext())

        setContent {
            PlotsForComposeTheme {
                App(root)
            }
        }
    }
}