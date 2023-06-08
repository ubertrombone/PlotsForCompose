package com.joshrose.plotsforcompose.internals

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.joshrose.plotsforcompose.axis.util.AxisPosition
import com.joshrose.plotsforcompose.exception.DataFrameSizeException
import com.joshrose.plotsforcompose.internals.standardizing.SeriesStandardizing
import com.joshrose.plotsforcompose.util.width
import java.io.InvalidObjectException

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
    // TODO: mapping should take the values and first map them against data or second see if they are a list of length = data[some]
    // TODO: If not, throw an error.
    // TODO: Above should only apply to mappings that require that. Some might be single values (i.e., color).
    //val mapping by remember { mutableStateOf(plot.mapping.map.toMutableMap()) }

    println("Scales: ${plot.scales()}")

    var scaleTopX: Scale? = null
    var scaleCenterX: Scale? = null
    var scaleBottomX: Scale? = null

    var scaleStartY: Scale? = null
    var scaleCenterY: Scale? = null
    var scaleEndY: Scale? = null

    plot.scales().filter { it.scale == ScaleKind.X }.forEach { scale ->
        when (scale.position) {
            AxisPosition.Bottom -> scaleBottomX = scale
            AxisPosition.Top -> scaleTopX = scale
            AxisPosition.Center -> scaleCenterX = scale
            AxisPosition.Both -> {
                scaleBottomX = scale
                scaleTopX = scale
            }
            null -> scaleBottomX = scale
            else -> throw InvalidObjectException("AxisPosition for ${scale.scale.name} must extend XAxis or XOrYAxis.")
        }
    }

    plot.scales().filter { it.scale == ScaleKind.Y }.forEach { scale ->
        when (scale.position) {
            AxisPosition.Start -> scaleStartY = scale
            AxisPosition.End -> scaleEndY = scale
            AxisPosition.Center -> scaleCenterY = scale
            AxisPosition.Both -> {
                scaleStartY = scale
                scaleEndY = scale
            }
            null -> scaleStartY = scale
            else -> throw InvalidObjectException("AxisPosition for ${scale.scale.name} must extend YAxis or XOrYAxis.")
        }
    }

    println("ScaleX - Top: $scaleTopX, Center: $scaleCenterX, Bottom: $scaleBottomX")
    println("ScaleY - Start: $scaleStartY, Center: $scaleCenterY, End: $scaleEndY")

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

internal fun duplicateKeys(keys: MutableSet<String>, key: String, count: Int = 1): String =
    if (keys.contains(key)) duplicateKeys(keys, "$key$count", count = count + 1) else key