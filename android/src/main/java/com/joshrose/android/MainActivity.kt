package com.joshrose.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.joshrose.common.App
import com.roseFinancials.lenafx.ui.theme.PlotsForComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlotsForComposeTheme {
                App()
            }
        }
    }
}