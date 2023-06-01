@file:OptIn(ExperimentalTextApi::class)

package com.joshrose.plotsforcompose.internals

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.joshrose.plotsforcompose.internals.standardizing.SeriesStandardizing
import com.joshrose.plotsforcompose.util.width

@Composable
fun ShowPlot(plot: Plot) {
    val data by remember { mutableStateOf(plot.data?.let { asPlotData(it) }) }

    println("Data: ${plot.data}")
    println("Layers: ${plot.layers()}")
    println("Scales: ${plot.scales()}")
    println("Other: ${plot.otherFeatures()}")

    var size: Map<String, Dp?>? = null
    plot.otherFeatures().forEach { feature ->
        when (feature.kind) {
            "size" -> size = feature.configs.mapValues { it.value as Dp? }
            else -> println(feature.kind)
        }
    }

    Canvas(
        modifier = Modifier
            .height(size?.get("height") ?: 500.dp)
            .width(size?.get("width"))
    ) {

    }
}

internal fun asPlotData(rawData: Map<*, *>): Map<String, List<Any?>> {
    val standardizedData = HashMap<String, List<Any?>>()
    rawData.forEach { (rawKey, rawValue) ->
        val key = rawKey.toString()
        standardizedData[key] = SeriesStandardizing.toList(rawValue!!, key)
    }
    return standardizedData
}