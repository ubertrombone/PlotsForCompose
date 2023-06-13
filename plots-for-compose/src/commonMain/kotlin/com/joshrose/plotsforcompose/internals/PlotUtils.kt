package com.joshrose.plotsforcompose.internals

import com.joshrose.plotsforcompose.axis.config.axisline.AxisLineConfiguration.*
import com.joshrose.plotsforcompose.axis.config.util.Multiplier
import com.joshrose.plotsforcompose.axis.util.AxisPosition.*
import com.joshrose.plotsforcompose.internals.exception.DataFrameSizeException
import com.joshrose.plotsforcompose.internals.standardizing.SeriesStandardizing
import com.joshrose.plotsforcompose.internals.util.AxisData
import com.joshrose.plotsforcompose.internals.util.maxValue
import com.joshrose.plotsforcompose.internals.util.minValue
import com.joshrose.plotsforcompose.internals.util.range

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

internal fun getAxisData(
    data: List<Any?>?,
    minValueAdjustment: Multiplier?,
    maxValueAdjustment: Multiplier?,
    rangeAdjustment: Multiplier?
): AxisData {
    val max = maxValue(
        maxValue = data?.mapNotNull { it.toFloatOrNull() }?.maxOrNull() ?: 100f,
        maxValueAdjustment = maxValueAdjustment
    )

    val min = minValue(
        minValue = data?.mapNotNull { it.toFloatOrNull() }?.minOrNull() ?: 0f,
        maxValue = max,
        minValueAdjustment = minValueAdjustment
    )

    val range = range(
        minValue = min,
        maxValue = max,
        rangeAdjustment = rangeAdjustment
    )

    return AxisData(min = min, max = max, range = range)
}

internal fun xConfigurationOrNull(scaleX: Scale?) = when (scaleX?.axisLineConfigs) {
    is XConfiguration -> scaleX.axisLineConfigs
    is YConfiguration ->
        throw IllegalStateException("Axis Line Configurations on the X scale should be of type XConfiguration.")
    null -> null
}

internal fun yConfigurationOrNull(scaleY: Scale?) = when (scaleY?.axisLineConfigs) {
    is XConfiguration ->
        throw IllegalStateException("Axis Line Configurations on the Y scale should be of type YConfiguration.")
    is YConfiguration -> scaleY.axisLineConfigs
    null -> null
}

internal fun getXAxisPosition(config: XConfiguration?, yAxisData: AxisData) = config?.axisPosition ?: when {
    yAxisData.max <= 0 -> Top
    yAxisData.min < 0 -> Center
    else -> Bottom
}

internal fun getYAxisPosition(config: YConfiguration?, xAxisData: AxisData) = config?.axisPosition ?: when {
    xAxisData.max <= 0 -> End
    xAxisData.min < 0 -> Center
    else -> Start
}

internal fun drawZero(
    scaleX: Scale?,
    scaleY: Scale?,
    xAxisData: AxisData,
    yAxisData: AxisData,
    xAxisPosition: XAxis,
    yAxisPosition: YAxis,
    xLabels: List<Float>,
    yLabels: List<Float>
) = when {
    yAxisData.min == 0f && xAxisData.min == 0f &&
            xAxisPosition == Bottom && yAxisPosition == Start &&
            scaleX.isNotNull() && scaleY.isNotNull() &&
            scaleX?.labelConfigs?.showLabels == true && scaleY?.labelConfigs?.showLabels == true -> false
    yAxisData.max == 0f && xAxisData.min == 0f &&
            xAxisPosition == Top && yAxisPosition == Start &&
            scaleX.isNotNull() && scaleY.isNotNull() &&
            scaleX?.labelConfigs?.showLabels == true && scaleY?.labelConfigs?.showLabels == true -> false
    yAxisData.min == 0f && xAxisData.max == 0f &&
            xAxisPosition == Bottom && yAxisPosition == End &&
            scaleX.isNotNull() && scaleY.isNotNull() &&
            scaleX?.labelConfigs?.showLabels == true && scaleY?.labelConfigs?.showLabels == true -> false
    yAxisData.max == 0f && xAxisData.max == 0f &&
            xAxisPosition == Top && yAxisPosition == End &&
            scaleX.isNotNull() && scaleY.isNotNull() &&
            scaleX?.labelConfigs?.showLabels == true && scaleY?.labelConfigs?.showLabels == true -> false
    (xLabels.min() != 0f && xLabels.max() != 0f && xLabels.contains(0f)) &&
            (yLabels.min() != 0f && yLabels.max() != 0f && yLabels.contains(0f)) &&
            xAxisPosition == Center && yAxisPosition == Center &&
            scaleX.isNotNull() && scaleY.isNotNull() &&
            (scaleX?.labelConfigs?.showLabels == true || scaleY?.labelConfigs?.showLabels == true) -> false
    else -> true
}

internal fun Any?.toFloatOrNull() = this.toString().toDoubleOrNull()?.toFloat()
internal fun Scale?.isNotNull() = this != null