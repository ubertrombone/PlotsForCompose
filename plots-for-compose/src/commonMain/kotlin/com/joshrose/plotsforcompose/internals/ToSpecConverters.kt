package com.joshrose.plotsforcompose.internals

import com.joshrose.plotsforcompose.internals.Specs.Scale.AXIS_LINE_CONFIGS
import com.joshrose.plotsforcompose.internals.Specs.Scale.BREAKS
import com.joshrose.plotsforcompose.internals.Specs.Scale.FORMAT
import com.joshrose.plotsforcompose.internals.Specs.Scale.GUIDELINE_CONFIGS
import com.joshrose.plotsforcompose.internals.Specs.Scale.LABELS
import com.joshrose.plotsforcompose.internals.Specs.Scale.LABEL_CONFIGS
import com.joshrose.plotsforcompose.internals.Specs.Scale.LIMITS
import com.joshrose.plotsforcompose.internals.Specs.Scale.NAME
import com.joshrose.plotsforcompose.internals.Specs.Scale.NA_VALUE
import com.joshrose.plotsforcompose.internals.Specs.Scale.POSITION
import com.joshrose.plotsforcompose.internals.Specs.Scale.REVERSE
import com.joshrose.plotsforcompose.internals.Specs.Scale.SCALE
import com.joshrose.plotsforcompose.internals.standardizing.MapStandardizing
import com.joshrose.plotsforcompose.internals.standardizing.SeriesStandardizing.toList

// TODO: Maybe return an encoded JSON string
fun Plot.toSpec(): MutableMap<String, Any> {
    val spec = HashMap<String, Any>()
    val plot = this

    plot.data?.let {
        spec[Specs.PlotBase.DATA] = asPlotData(plot.data)
    }

    spec[Specs.PlotBase.MAPPING] = plot.mapping.map
    spec[Specs.Plot.LAYERS] = plot.layers().map(Layer::toSpec)
    spec[Specs.Plot.SCALES] = plot.scales().map(Scale::toSpec)

    plot.otherFeatures().forEach {
        spec[it.kind] = it.toSpec()
    }

    return spec.also(::println)
}

fun Layer.toSpec(): MutableMap<String, Any> {
    val spec = HashMap<String, Any>()

    data?.let { spec[Specs.PlotBase.DATA] = asPlotData(it) }
    spec[Specs.PlotBase.MAPPING] = mapping.map
    spec[Specs.Layer.PLOT] = plot.kind.optionName()
    spec[Specs.Layer.STAT] = stat.kind.optionName()
    spec[Specs.Layer.SHOW_LEGEND] = showLegend

    layerConfigs?.let { spec[Specs.Layer.CONFIGS] = it }
    markers?.let { spec[Specs.Layer.MARKERS] = it }
    orientation?.let { spec[Specs.Layer.ORIENTATION] = it }
    position?.let { spec[Specs.Layer.POS] = it.kind.optionName() }

    return spec
}

fun Scale.toSpec(): Map<String, Any> {
    val spec = HashMap<String, Any>()

    spec[SCALE] = scale.optionName()

    guidelinesConfigs?.let { spec[GUIDELINE_CONFIGS] = it }
    labelConfigs?.let { spec[LABEL_CONFIGS] = it }
    axisLineConfigs?.let { spec[AXIS_LINE_CONFIGS] = it }
    name?.let { spec[NAME] = it }
    breaks?.let { spec[BREAKS] = toList(it, BREAKS) }
    labels?.let { spec[LABELS] = it }
    limits?.let { spec[LIMITS] = toList(it, LIMITS) }
    naValue?.let { spec[NA_VALUE] = it }
    format?.let { spec[FORMAT] = it }
    position?.let { spec[POSITION] = it }
    reverse?.let { spec[REVERSE] = it }

    return spec
}

fun ConfigsMap.toSpec(): MutableMap<String, Any> = HashMap(MapStandardizing.standardize(configs))

internal fun asPlotData(rawData: Map<*, *>): Map<String, List<Any?>> {
    val standardizedData = HashMap<String, List<Any?>>()
    rawData.forEach { (rawKey, rawValue) ->
        val key = rawKey.toString()
        standardizedData[key] = toList(rawValue!!, key)
    }
    return standardizedData
}