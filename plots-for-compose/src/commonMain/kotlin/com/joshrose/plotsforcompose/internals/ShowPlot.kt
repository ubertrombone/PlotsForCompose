package com.joshrose.plotsforcompose.internals

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.joshrose.plotsforcompose.util.height
import com.joshrose.plotsforcompose.util.width
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Composable
fun ShowPlot(specs: Map<String, Any>) {

    val format = Json {
        isLenient = true
    }

    val width = specs[Specs.Plot.WIDTH]?.toString()?.toFloat()?.dp
    val height = specs[Specs.Plot.HEIGHT]?.toString()?.toFloat()?.dp
    println("Scales: ${specs[Specs.Plot.SCALES]}")
    println("Width: ${specs[Specs.Plot.WIDTH]}")
    println("Height: ${specs[Specs.Plot.HEIGHT]}")
    println("Size: ${specs[Specs.Plot.SIZE]}")
    val size = try {
        format.decodeFromString<Size>(specs[Specs.Plot.SIZE].toString().replace('=', ':'))
    } catch (e: Exception) {
        Size(null, null)
    }
    println(size)

    Canvas(
        modifier = Modifier
            .background(color = Color.Green)
            .height(size.height?.dp)
            .width(size.width?.dp)
            .background(color = Color.Red)
    ) {

    }
}

@Serializable
data class Size(val width: Double?, val height: Double?)