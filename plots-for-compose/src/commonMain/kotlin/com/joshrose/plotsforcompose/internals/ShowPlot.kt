package com.joshrose.plotsforcompose.internals

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.joshrose.plotsforcompose.exception.DataFrameSizeException
import com.joshrose.plotsforcompose.internals.standardizing.SeriesStandardizing
import com.joshrose.plotsforcompose.util.width

@Composable
fun ShowPlot(plot: Plot) {
    println("Data: ${plot.data}")
    println("Map: ${plot.mapping.map}")
    val data = plot.data?.let { asPlotData(it) }
    val dataFrameValueSizes = data?.values?.map { it.size }?.toSet()?.size
    if (dataFrameValueSizes != 1) {
        val dataAsString = data?.let { it.map { (key, value) -> "$key: ${value.size}" } }
        val message = dataAsString?.let {
            "All data values must have an equal size:\n${it.joinToString("\n")}"
        } ?: "Data must not be Null."
        throw DataFrameSizeException(message)
    }

    println("Scales: ${plot.scales()}")

    var scaleX: Scale? = plot.scales().lastOrNull { it.scale == ScaleKind.X }
    var scaleY: Scale? = plot.scales().lastOrNull { it.scale == ScaleKind.Y }

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