package com.joshrose.plotsforcompose.internals

import com.joshrose.plotsforcompose.internals.standardizing.SeriesStandardizing.toList

// TODO: Maybe return an encoded JSON string
fun Plot.toSpec(): Specifications.Specs {
    var spec = Specifications.Specs()
    val plot = this

    plot.data?.let {
        spec = spec.copy(plotBase = Specifications.PlotBase(data = asPlotData(plot.data)))
    }

    spec = spec.copy(plotBase = spec.plotBase?.copy(mapping = plot.mapping.map))

//    spec[Specifications.PlotBase.MAPPING] = plot.mapping.map
//    spec[Specifications.Plot.LAYERS] = plot.layers().map(Layer::toSpec)
//    spec[Specifications.Plot.SCALES] = plot.scales().map(Scale::toSpec)
//
//    plot.otherFeatures().forEach {
//        spec[it.kind] = it.toSpec()
//    }

    return spec.also(::println)
}

fun Layer.toSpec(): Specifications.Layer {
    var spec = Specifications.Layer()

//    data?.let { spec[Specifications.PlotBase.DATA] = asPlotData(it) }
//    spec[Specifications.PlotBase.MAPPING] = mapping.map
//    spec[Specifications.Layer.PLOT] = plot.kind.optionName()
//    spec[Specifications.Layer.STAT] = stat.kind.optionName()
//    spec[Specifications.Layer.SHOW_LEGEND] = showLegend
//
//    layerConfigs?.let { spec[Specifications.Layer.CONFIGS] = it }
//    markers?.let { spec[Specifications.Layer.MARKERS] = it }
//    orientation?.let { spec[Specifications.Layer.ORIENTATION] = it }
//    position?.let { spec[Specifications.Layer.POS] = it.kind.optionName() }

    return spec
}

fun Scale.toSpec(): Map<String, Any> {
    val spec = HashMap<String, Any>()

//    spec[SCALE] = scale.optionName()
//
//    guidelinesConfigs?.let { spec[GUIDELINE_CONFIGS] = it }
//    labelConfigs?.let { spec[LABEL_CONFIGS] = it }
//    axisLineConfigs?.let { spec[AXIS_LINE_CONFIGS] = it }
//    name?.let { spec[NAME] = it }
//    breaks?.let { spec[BREAKS] = toList(it, BREAKS) }
//    labels?.let { spec[LABELS] = it }
//    limits?.let { spec[LIMITS] = toList(it, LIMITS) }
//    naValue?.let { spec[NA_VALUE] = it }
//    format?.let { spec[FORMAT] = it }
//    position?.let { spec[POSITION] = it }
//    reverse?.let { spec[REVERSE] = it }

    return spec
}

//fun ConfigsMap.toSpec(): MutableMap<String, Any> = HashMap(MapStandardizing.standardize(configs))

internal fun asPlotData(rawData: Map<*, *>): Map<String, List<Any?>> {
    val standardizedData = HashMap<String, List<Any?>>()
    rawData.forEach { (rawKey, rawValue) ->
        val key = rawKey.toString()
        standardizedData[key] = toList(rawValue!!, key)
    }
    return standardizedData
}