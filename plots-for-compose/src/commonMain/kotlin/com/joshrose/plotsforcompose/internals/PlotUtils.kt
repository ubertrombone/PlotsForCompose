package com.joshrose.plotsforcompose.internals

import com.joshrose.plotsforcompose.axis.config.axisline.AxisLineConfiguration.*
import com.joshrose.plotsforcompose.axis.config.util.Multiplier
import com.joshrose.plotsforcompose.axis.util.AxisAlignment
import com.joshrose.plotsforcompose.axis.util.AxisPosition.*
import com.joshrose.plotsforcompose.axis.util.floatLabelsAndBreaks
import com.joshrose.plotsforcompose.internals.standardizing.SeriesStandardizing
import com.joshrose.plotsforcompose.internals.util.AxisData
import com.joshrose.plotsforcompose.internals.util.maxValue
import com.joshrose.plotsforcompose.internals.util.minValue
import com.joshrose.plotsforcompose.internals.util.range
import kotlin.math.abs
import kotlin.math.roundToInt

@Throws(IllegalStateException::class)
internal fun getData(data: Map<*, *>?): Map<String, List<Any?>> {
    val typedData = data?.let { asPlotData(it) }
    val dataFrameValueSizes = typedData?.values?.map { it.size }?.toSet()?.size
    check(dataFrameValueSizes == 1) {
        val dataAsString = typedData?.let { it.map { (key, value) -> "$key: ${value.size}" } }
        dataAsString?.let { "All data values must have an equal size:\n${it.joinToString("\n")}" }
            ?: "Data must not be Null."
    }
    return typedData!!
}

internal fun asPlotData(rawData: Map<*, *>): Map<String, List<Any?>> {
    val standardizedData = HashMap<String, List<Any?>>()
    rawData.forEach { (rawKey, rawValue) ->
        val key = rawKey.toString()
        standardizedData[key] = SeriesStandardizing.toList(rawValue!!, key)
    }
    return standardizedData
}

@Throws(IllegalStateException::class)
internal fun asMappingData(
    data: Map<String, List<Any?>>,
    mapping: Map<String, Any>,
    key: String
): List<Any?>? =
    mapping[key]?.let {
        when (it) {
            is String -> {
                checkNotNull(data[it]) { "Variable not found: $it. Variables in data frame: ${data.keys}" }
                data[it]!!
            }
            is List<Any?> -> {
                check(it.size == data.values.first().size) {
                    val dataAsString = data.map { (k, v) -> "$k: ${v.size}" }.plus("$key: ${it.size}")
                    "All data values must have an equal size:\n${dataAsString.joinToString("\n")}"
                }
                it
            }
            else -> throw IllegalStateException("Argument must be String or List<Any?>.")
        }
    }

internal fun getAxisData(
    data: List<Any?>?,
    minValueAdjustment: Multiplier?,
    maxValueAdjustment: Multiplier?,
    rangeAdjustment: Multiplier?
): AxisData {
    val tentativeMax = data?.mapNotNull { it.toFloatOrNull() }?.maxOrNull() ?: 100f
    val tentativeMin = data?.mapNotNull { it.toFloatOrNull() }?.minOrNull() ?: 100f

    val max = maxValue(
        maxValue = if (abs(tentativeMin) > tentativeMax) abs(tentativeMin) else tentativeMax,
        maxValueAdjustment = maxValueAdjustment
    )

    val min = minValue(
        minValue = tentativeMin,
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

internal fun getBoundBreaks(scale: Scale?, rawData: Collection<Any?>): List<Any?>? = when {
    scale?.showGuidelines == false -> null
    scale?.breaks == null -> rawData.toList()
    else -> rawData.filterIndexed { index, _ -> index % (1.div(scale.breaks.factor)).roundToInt() == 0 }
}

internal fun getUnboundBreaks(
    scale: Scale?,
    rawData: Collection<Any?>,
    axisData: AxisData
): List<Float>? = when {
    scale?.showGuidelines == false -> null
    scale?.breaks == null -> floatLabelsAndBreaks(
        amount = rawData.size,
        minValue = axisData.min,
        maxValue = axisData.max
    )
    else -> floatLabelsAndBreaks(
        amount = (rawData.size.times((scale.breaks.factor))).roundToInt(),
        minValue = axisData.min,
        maxValue = axisData.max
    )
}

internal fun getBoundLabels(
    scale: Scale?,
    rawData: Collection<Any?>,
    breaksData: List<Any?>?
): List<Any?>? = when {
    scale?.showLabels == false -> null
    scale?.breaks == null && scale?.labels == null -> rawData.toList()
    scale.labels == null -> breaksData ?: rawData.toList()
    breaksData == null -> rawData.filterIndexed { index, _ -> index % (1.div(scale.labels.factor)).roundToInt() == 0 }
    else -> breaksData.filterIndexed { index, _ -> index % (1.div(scale.labels.factor)).roundToInt() == 0 }
}

internal fun getUnboundLabels(
    scale: Scale?,
    rawData: Collection<Any?>,
    breaksData: List<Float>?,
    axisData: AxisData
): List<Float>? = when {
    scale?.showLabels == false -> null
    scale?.breaks == null && scale?.labels == null -> floatLabelsAndBreaks(
        amount = rawData.size,
        minValue = axisData.min,
        maxValue = axisData.max
    )
    scale.labels == null -> breaksData ?: floatLabelsAndBreaks(
        amount = rawData.size,
        minValue = axisData.min,
        maxValue = axisData.max
    )
    breaksData == null -> floatLabelsAndBreaks(
        amount = (rawData.size.times((scale.labels.factor))).roundToInt(),
        minValue = axisData.min,
        maxValue = axisData.max
    )
    else -> floatLabelsAndBreaks(
        amount = (breaksData.size.times((scale.labels.factor))).roundToInt(),
        minValue = axisData.min,
        maxValue = axisData.max
    )
}

internal fun getIndices(
    scale: Scale?,
    rawData: Collection<Any?>,
    breaksData: List<Any?>?
): List<Int>? = when {
    scale?.showLabels == false -> null
    scale?.labels == null -> breaksData?.indices?.toList() ?: rawData.indices.toList()
    breaksData == null ->
        List(rawData.size) { index -> if (index % (1.div(scale.labels.factor)).roundToInt() == 0) index else null }.filterNotNull()
    else ->
        List(breaksData.size) { index -> if (index % (1.div(scale.labels.factor)).roundToInt() == 0) index else null }.filterNotNull()
}

internal fun getYFactor(height: Float, dataSize: Int?, axisAlignment: AxisAlignment.YAxis?): Float =
    if (dataSize == 1 && (axisAlignment == AxisAlignment.SpaceBetween)) height.div(dataSize.toFloat())
    else height.div(dataSize?.plus((axisAlignment ?: AxisAlignment.SpaceBetween).offset)?.toFloat() ?: 1f)

internal fun getXFactor(width: Float, dataSize: Int?, axisAlignment: AxisAlignment.XAxis?): Float =
    width.div(dataSize?.plus((axisAlignment ?: AxisAlignment.SpaceBetween).offset)?.toFloat() ?: 1f)

@Throws(IllegalStateException::class)
internal fun Scale?.xConfigurationOrNull(): XConfiguration? {
    check(this?.axisLineConfigs is XConfiguration?) { "Axis Line Configurations on the X scale should be of type XConfiguration." }
    return if (this?.axisLineConfigs is XConfiguration) this.axisLineConfigs else null
}

@Throws(IllegalStateException::class)
internal fun Scale?.yConfigurationOrNull(): YConfiguration? {
    check(this?.axisLineConfigs is YConfiguration?) { "Axis Line Configurations on the Y scale should be of type YConfiguration." }
    return if (this?.axisLineConfigs is YConfiguration) this.axisLineConfigs else null
}

internal fun XConfiguration?.getXAxisPosition(yAxisData: AxisData) = this?.axisPosition ?: when {
    yAxisData.max <= 0 -> Top
    yAxisData.min < 0 -> Center
    else -> Bottom
}

internal fun XConfiguration?.getXAxisPosition() = this?.axisPosition ?: Bottom

internal fun YConfiguration?.getYAxisPosition(xAxisData: AxisData) = this?.axisPosition ?: when {
    xAxisData.max <= 0 -> End
    xAxisData.min < 0 -> Center
    else -> Start
}

internal fun YConfiguration?.getYAxisPosition() = this?.axisPosition ?: Start

internal fun drawZero(
    scaleX: Scale?,
    scaleY: Scale?,
    xAxisData: AxisData,
    yAxisData: AxisData,
    xAxisPosition: XAxis,
    yAxisPosition: YAxis,
    xLabels: List<Float>?,
    yLabels: List<Float>?
) = when {
    yAxisData.min == 0f && xAxisData.min == 0f &&
            xAxisPosition == Bottom && yAxisPosition == Start &&
            scaleX.isNotNull() && scaleY.isNotNull() &&
            scaleX?.showLabels == true && scaleY?.showLabels == true -> false
    yAxisData.max == 0f && xAxisData.min == 0f &&
            xAxisPosition == Top && yAxisPosition == Start &&
            scaleX.isNotNull() && scaleY.isNotNull() &&
            scaleX?.showLabels == true && scaleY?.showLabels == true -> false
    yAxisData.min == 0f && xAxisData.max == 0f &&
            xAxisPosition == Bottom && yAxisPosition == End &&
            scaleX.isNotNull() && scaleY.isNotNull() &&
            scaleX?.showLabels == true && scaleY?.showLabels == true -> false
    yAxisData.max == 0f && xAxisData.max == 0f &&
            xAxisPosition == Top && yAxisPosition == End &&
            scaleX.isNotNull() && scaleY.isNotNull() &&
            scaleX?.showLabels == true && scaleY?.showLabels == true -> false
    (xLabels?.min() != 0f && xLabels?.max() != 0f && xLabels?.contains(0f) == true) &&
            (yLabels?.min() != 0f && yLabels?.max() != 0f && yLabels?.contains(0f) == true) &&
            xAxisPosition == Center && yAxisPosition == Center &&
            scaleX.isNotNull() && scaleY.isNotNull() &&
            (scaleX?.showLabels == true || scaleY?.showLabels == true) -> false
    else -> true
}

//internal fun Collection<Any?>.sortedNotNull(): List<Any> = filterNotNull().sortedWith { value1, value2 ->
//    when (value1) {
//        is String -> value1.toString().compareTo(value2.toString())
//        is Char -> value1.toString().compareTo(value2.toString())
//        is Number -> toDouble(value1)?.compareTo(toDouble(value2 as Number) ?: Double.NaN) ?: 0
//        is Boolean -> value1.toString().toBoolean().compareTo(value2 as Boolean)
//        is Duration -> value1.inWholeMilliseconds.compareTo((value2 as Duration).inWholeMilliseconds)
//        else -> throw Exception("Values must be primitive non-iterable type.")
//    }
//}

internal fun isCastAsNumber(value: List<Any?>) = value.all { (it ?: Float.NaN) is Number }

internal fun Any?.toFloatOrNull() = this.toString().toDoubleOrNull()?.toFloat()

internal fun Scale?.isNotNull() = this != null

internal fun List<Int>.countsRange() = (min()..max()).toList()