package com.joshrose.common.ui.axes

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joshrose.common.components.axes.AxesComponent
import com.joshrose.common.util.ScrollLazyColumn

@Composable
fun AxesContent(
    component: AxesComponent,
    modifier: Modifier = Modifier
) {
    val data = mapOf<Float, Float>(1f to 1f, 2f to 2f, 3f to 3f)
    ScrollLazyColumn(modifier = modifier.fillMaxSize()) {
        item {
            Canvas(
                modifier = Modifier.fillMaxWidth().height(300.dp)
            ) {

            }
        }
    }
}