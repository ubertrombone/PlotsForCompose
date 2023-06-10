package com.joshrose.plotsforcompose.internals

import com.joshrose.plotsforcompose.exception.DataFrameSizeException
import com.joshrose.plotsforcompose.internals.standardizing.SeriesStandardizing

@Throws(DataFrameSizeException::class)
internal fun getData(data: Map<*, *>?): Map<String, List<Any?>> {
    val typedData = data?.let { asPlotData(it) }
    val dataFrameValueSizes = typedData?.values?.map { it.size }?.toSet()?.size
    if (dataFrameValueSizes != 1) {
        val dataAsString = typedData?.let { it.map { (key, value) -> "$key: ${value.size}" } }
        val message = dataAsString?.let {
            "All data values must have an equal size:\n${it.joinToString("\n")}"
        } ?: "Data must not be Null."
        throw DataFrameSizeException(message)
    }
    return typedData
}

internal fun asPlotData(rawData: Map<*, *>): Map<String, List<Any?>> {
    val standardizedData = HashMap<String, List<Any?>>()
    rawData.forEach { (rawKey, rawValue) ->
        val key = rawKey.toString()
        standardizedData[key] = SeriesStandardizing.toList(rawValue!!, key)
    }
    return standardizedData
}

@Throws(IllegalArgumentException::class, DataFrameSizeException::class)
internal fun asMappingData(
    data: Map<String, List<Any?>>,
    mapping: Map<String, Any>,
    key: String
): List<Any?>? =
    mapping[key]?.let {
        when (it) {
            is String -> {
                data[it] ?:
                throw IllegalArgumentException("Variable not found: $it. Variables in data frame: ${data.keys}")
            }
            is List<Any?> -> {
                val len =  data.values.first().size
                if (it.size == len) it
                else {
                    val dataAsString = data.map { (k, v) -> "$k: ${v.size}" }.plus("$key: ${it.size}")
                    throw DataFrameSizeException("All data values must have an equal size:\n${dataAsString.joinToString("\n")}")
                }
            }
            else -> throw IllegalArgumentException("Argument must be String or List<Any?>. Got: $it")
        }
    }

fun Any?.toFloatOrNull() = this.toString().toDoubleOrNull()?.toFloat()