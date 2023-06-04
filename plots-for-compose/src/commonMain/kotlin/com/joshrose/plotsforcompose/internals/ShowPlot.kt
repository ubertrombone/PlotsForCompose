package com.joshrose.plotsforcompose.internals

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.joshrose.plotsforcompose.internals.standardizing.SeriesStandardizing
import com.joshrose.plotsforcompose.util.width

@Composable
fun ShowPlot(plot: Plot) {
    println("Data: ${plot.data}")
    val data by remember { mutableStateOf(plot.data?.let { asPlotData(it) } ?: mutableMapOf()) }

    println("Layers: ${plot.layers()}")

    // TODO: Test this...
    plot.layers().filter { it.data != null }.forEach { layer ->
        val layerData = asPlotData(layer.data!!)
        layerData.keys.forEach { key ->
            if (!data.containsKey(key)) data[key] = layerData[key]!!
            else data[duplicateKeys(data.keys, key)] = layerData[key]!!
        }
    }

    println("Scales: ${plot.scales()}")

    var xScaleOne: Scale? = null
    var xScaleTwo: Scale? = null
    var yScaleOne: Scale? = null
    var yScaleTwo: Scale? = null

    plot.scales().filter { it.scale == ScaleKind.X }.forEach { scale ->
        if (xScaleOne == null) xScaleOne = scale else xScaleTwo = scale
    }

    plot.scales().filter { it.scale == ScaleKind.Y }.forEach { scale ->
        if (yScaleOne == null) yScaleOne = scale else yScaleTwo = scale
    }

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

internal fun asPlotData(rawData: Map<*, *>): MutableMap<String, List<Any?>> {
    val standardizedData = HashMap<String, List<Any?>>()
    rawData.forEach { (rawKey, rawValue) ->
        val key = rawKey.toString()
        standardizedData[key] = SeriesStandardizing.toList(rawValue!!, key)
    }
    return standardizedData
}

internal fun duplicateKeys(keys: MutableSet<String>, key: String, count: Int = 1): String =
    if (keys.contains(key)) duplicateKeys(keys, "$key$count", count = count + 1) else key