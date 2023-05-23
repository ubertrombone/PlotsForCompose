package com.joshrose.plotsforcompose.internals

class Plot internal constructor(
    val data: Map<*, *>? = null,
    val mapping: Configs = GenericMapping().seal(),
    val features: List<Feature> = emptyList()
) {
    operator fun plus(other: Feature): Plot {
        return when (other) {
            is FeatureList -> withFeatureList(this, other)
            else -> withFeature(this, other)
        }
    }

    fun scales(): List<Scale> = features.filterIsInstance<Scale>()
    fun otherFeatures(): List<ConfigsMap> = features.filterIsInstance<ConfigsMap>()

    fun show() = println("Show") // TODO

    override fun toString() = "Plot(data=$data, mapping=${mapping.map}, features=$features)"

    companion object {
        fun withFeature(plot: Plot, feature: Feature): Plot {
            return Plot(
                data = plot.data,
                mapping = plot.mapping,
                features = plot.features + listOf(feature)
            )
        }

        private fun withFeatureList(plot: Plot, featureList: FeatureList): Plot {
            return Plot(
                data = plot.data,
                mapping = plot.mapping,
                features = plot.features + featureList.elements
            )
        }
    }
}

sealed class Feature {
    open operator fun plus(other: Feature): Feature {
        return when (other) {
            is FeatureList -> FeatureList(listOf(this) + other.elements)
            else -> FeatureList(listOf(this, other))
        }
    }
}

class FeatureList(val elements: List<Feature>) : Feature() {
    override operator fun plus(other: Feature): Feature {
        return when (other) {
            is FeatureList -> FeatureList(this.elements + other.elements)
            else -> FeatureList(this.elements + listOf(other))
        }
    }
}

class Scale(
    val name: String? = null,
    val breaks: List<Any>? = null, // TODO -> These are where ticks and guidelines should be drawn
    val labels: List<String>? = null, // TODO -> These are the labels that will be drawn, should be at correct position
    val limits: Any? = null, // TODO -> Value to limit data. E.g., columnData = listOf("a", "b", "c"), limit = "b", "c" will not be shown.
    val naValue: Any? = null,
    val format: String? = null,
    val reverse: Boolean? = null
) : Feature() {
    override fun toString() =
        "Scale(name=$name, breaks=$breaks, labels=$labels, limits=$limits, naValue=$naValue, format=$format, reverse=$reverse)"
}

open class ConfigsMap internal constructor(
    val kind: String,
    val configs: Map<String, Any>
) : Feature() {
    internal constructor(
        kind: String,
        name: String,
        configs: Map<String, Any>
    ) : this(
        kind = kind,
        configs = mapOf("name" to name) + configs
    )
}