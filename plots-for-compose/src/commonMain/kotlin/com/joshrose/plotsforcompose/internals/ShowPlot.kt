package com.joshrose.plotsforcompose.internals

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joshrose.plotsforcompose.util.width

@Composable
fun ShowPlot(specs: Specifications.Specs) {

    Canvas(
        modifier = Modifier
            .height(specs.plot?.size?.height ?: 500.dp)
            .width(specs.plot?.size?.width)
    ) {

    }
}