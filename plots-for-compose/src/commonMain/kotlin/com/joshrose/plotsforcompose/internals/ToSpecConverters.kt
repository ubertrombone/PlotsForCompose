package com.joshrose.plotsforcompose.internals

import com.joshrose.plotsforcompose.internals.standardizing.SeriesStandardizing.toList

fun Plot.toSpec(): MutableMap<String, Any> {
    val spec = HashMap<String, Any>()
    val plot = this

    plot.data?.let {
        spec[Specs.PlotBase.DATA] = asPlotData(plot.data)
    }



    return spec
}

internal fun asPlotData(rawData: Map<*, *>): Map<String, List<Any?>> {
    val standardizedData = HashMap<String, List<Any?>>()
    rawData.forEach { (rawKey, rawValue) ->
        val key = rawKey.toString()
        standardizedData[key] = toList(rawValue!!, key)
    }
    return standardizedData
}