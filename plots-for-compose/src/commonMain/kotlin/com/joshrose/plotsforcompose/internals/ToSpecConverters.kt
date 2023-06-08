package com.joshrose.plotsforcompose.internals

fun Plot.toSpec() = Specifications.Specs(
        plotBase = Specifications.PlotBase(
            data = data?.let { asPlotData(it) },
            mapping = mapping.map
        ),
        plot = Specifications.Plot(
            //layers = layers().map(Layer::toSpec),
            scales = scales().map(Scale::toSpec),
            size = size()?.size
        )
    ).also(::println)

//fun Layer.toSpec() = Specifications.Layer(
//    data = data?.let { asPlotData(it) },
//    mapping = mapping.map,
//    plot = plot.kind.optionName(),
//    stat = stat.kind.optionName(),
//    showLegend = showLegend,
//    configs = layerConfigs,
//    markers = markers,
//    orientation = orientation,
//    pos = position?.kind?.optionName()
//)

fun Scale.toSpec() = Specifications.Scale(
    scale = scale.optionName(),
    guidelineConfigs = guidelinesConfigs,
    labelConfigs = labelConfigs,
    axisLineConfigs = axisLineConfigs,
    name = name,
    breaks = breaks,
    labels = labels,
    limits = limits,
    naValue = naValue,
    format = format,
    reverse = reverse,
    position = position
)

//internal fun asPlotData(rawData: Map<*, *>): Map<String, List<Any?>> {
//    val standardizedData = HashMap<String, List<Any?>>()
//    rawData.forEach { (rawKey, rawValue) ->
//        val key = rawKey.toString()
//        standardizedData[key] = toList(rawValue!!, key)
//    }
//    return standardizedData
//}