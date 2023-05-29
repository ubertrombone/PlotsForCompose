package com.joshrose.plotsforcompose.internals

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ShowPlot(specs: Specifications.Specs) {

    Canvas(
        modifier = Modifier
            .background(color = Color.Green)
            //.height(size.height?.dp)
            //.width(size.width?.dp)
            .background(color = Color.Red)
    ) {

    }
}