package com.joshrose.plotsforcompose.internals

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.joshrose.plotsforcompose.ShowPlot
import com.joshrose.plotsforcompose.axis.config.axisline.AxisLineConfiguration
import com.joshrose.plotsforcompose.axis.config.guidelines.GuidelinesConfiguration
import com.joshrose.plotsforcompose.axis.config.labels.LabelsConfiguration
import com.joshrose.plotsforcompose.axis.util.AxisPosition
import com.joshrose.plotsforcompose.util.Proportional

class Plot internal constructor(
    val data: Map<*, *>? = null,
    val mapping: Configs = GeneralAestheticMapping().seal(),
    val features: List<Feature> = emptyList()
) {
    operator fun plus(other: Feature): Plot {
        return when (other) {
            is FeatureList -> withFeatureList(this, other)
            else -> withFeature(this, other)
        }
    }

    //fun layers(): List<Layer> = features.filterIsInstance<Layer>()
    fun scales(): List<Scale> = features.filterIsInstance<Scale>()

    //fun otherFeatures(): List<ConfigsMap> = features.filterIsInstance<ConfigsMap>()

    @Composable
    fun show(modifier: Modifier = Modifier) = ShowPlot(this, modifier)

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

//abstract class Layer(
//    mapping: Configs,
//    val data: Map<*, *>? = null,
//    val layerConfigs: LayerConfigs? = null,
//    val plot: PlotConfigs,
//    val stat: StatConfigs,
//    val position: PosConfigs?,
//    val showLegend: Boolean,
//    val markers: Boolean? = null,
//    val orientation: AxisPosition.Orientation? = null
//) : ConfigCapsule, Feature() {
//    val mapping by lazy {
//        plot.mapping + stat.mapping + mapping
//    }
//
//    override fun toString() = """
//        Layer(
//            data=$data,
//            layerConfigs=${layerConfigs.toString()},
//            plot=${plot.kind}, stat=${stat.kind},
//            position=${position?.kind},
//            showLegend=$showLegend,
//            markers=$markers,
//            orientation=$orientation,
//            mapping=${mapping.map}
//        )
//    """.trimIndent()
//}

class Scale(
    val guidelinesConfigs: GuidelinesConfiguration?,
    val labelConfigs: LabelsConfiguration?,
    val axisLineConfigs: AxisLineConfiguration?,
    val scale: ScaleKind,
    val name: String? = null,
    val breaks: Proportional? = null,
    val labels: Proportional? = null,
    val naValue: Any? = null,
    val format: String? = null,
    val reverse: Boolean? = null,
    val position: AxisPosition? = null
) : Feature() {
    override fun toString() =
        "Scale(scale=$scale, name=$name, breaks=$breaks, labels=$labels, naValue=$naValue, format=$format, reverse=$reverse)"
}

//open class ConfigsMap internal constructor(
//    val kind: String,
//    val configs: Map<String, Any?>
//) : Feature() {
//    internal constructor(
//        kind: String,
//        name: String,
//        configs: Map<String, Any>
//    ) : this(
//        kind = kind,
//        configs = mapOf("name" to name) + configs
//    )
//    override fun toString() = "ConfigsMap(kind=$kind, configs=$configs)"
//}