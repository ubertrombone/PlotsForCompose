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

    fun otherFeatures(): List<ConfigsMap> = features.filterIsInstance<ConfigsMap>()

    fun show() = println("Show") // TODO

    override fun toString() = "Plot(data=$data, mapping=$mapping, features=$features)"

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