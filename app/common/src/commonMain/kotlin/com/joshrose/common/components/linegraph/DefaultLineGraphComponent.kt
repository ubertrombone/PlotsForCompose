package com.joshrose.common.components.linegraph

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.arkivanov.essenty.statekeeper.consume
import com.joshrose.common.components.linegraph.LineGraphComponent.Child
import com.joshrose.common.components.linegraph.data.DataModelImpl
import com.joshrose.common.components.linegraph.label.DefaultLabelComponent
import com.joshrose.common.components.linegraph.label.LabelComponent
import com.joshrose.common.components.linegraph.label.LabelModelImpl
import com.joshrose.common.components.linegraph.label_line.DefaultLabelLineComponent
import com.joshrose.common.components.linegraph.label_line.LabelLineComponent
import com.joshrose.common.components.linegraph.label_line.LabelLineModelImpl
import com.joshrose.common.components.linegraph.label_marker.DefaultLabelMarkerComponent
import com.joshrose.common.components.linegraph.label_marker.LabelMarkerComponent
import com.joshrose.common.components.linegraph.label_marker.LabelMarkerModelImpl
import com.joshrose.common.components.linegraph.line.DefaultLineComponent
import com.joshrose.common.components.linegraph.line.LineComponent
import com.joshrose.common.components.linegraph.line.LineModelImpl
import com.joshrose.common.components.linegraph.marker.DefaultMarkerComponent
import com.joshrose.common.components.linegraph.marker.MarkerComponent
import com.joshrose.common.components.linegraph.marker.MarkerModelImpl
import com.joshrose.common.components.linegraph.models.*
import com.joshrose.common.util.ScreenNames.LINE_GRAPH

class DefaultLineGraphComponent(
    componentContext: ComponentContext
) : LineGraphComponent, ComponentContext by componentContext {
    override val screenProperties = LINE_GRAPH

    private val navigation = StackNavigation<Config>()

    private val _dataValues = instanceKeeper.getOrCreate(KEY_DATA) {
        DataModelImpl(
            initialState = stateKeeper.consume(KEY_DATA) ?: DataValues(mapOf(
                "Independent" to List(20) { (-5..5).random() },
                "Dependent" to List(20) { (-5..5).random() }
            ))
        )
    }

    override val dataValues: Value<DataValues> = _dataValues.dataValues

    fun updateData() = _dataValues.updateData()

    private val _lineStates = instanceKeeper.getOrCreate(KEY_LINE) {
        LineModelImpl(
            initialState = stateKeeper.consume(KEY_LINE) ?: LineStates()
        )
    }

    override val lineStates: Value<LineStates> = _lineStates.lineStates

    private val _markerStates = instanceKeeper.getOrCreate(KEY_MARKER) {
        MarkerModelImpl(
            initialState = stateKeeper.consume(KEY_MARKER) ?: MarkerStates()
        )
    }

    override val markerStates: Value<MarkerStates> = _markerStates.markerStates

    private val _labelStates = instanceKeeper.getOrCreate(KEY_LABEL) {
        LabelModelImpl(
            initialState = stateKeeper.consume(KEY_LABEL) ?: LabelStates()
        )
    }

    override val labelStates: Value<LabelStates> = _labelStates.labelStates

    private val _labelLineStates = instanceKeeper.getOrCreate(KEY_LABEL_LINE) {
        LabelLineModelImpl(
            initialState = stateKeeper.consume(KEY_LABEL_LINE) ?: LabelLineStates()
        )
    }

    override val labelLineStates: Value<LabelLineStates> = _labelLineStates.labelLineStates

    private val _labelMarkerStates = instanceKeeper.getOrCreate(KEY_LABEL_MARKER) {
        LabelMarkerModelImpl(
            initialState = stateKeeper.consume(KEY_LABEL_MARKER) ?: LabelMarkerStates()
        )
    }

    override val labelMarkerStates: Value<LabelMarkerStates> = _labelMarkerStates.labelMarkerStates

    private val _childStack = childStack(
        source = navigation,
        initialConfiguration = Config.Line,
        handleBackButton = false,
        childFactory = ::createChild
    )

    override val childStack: Value<ChildStack<*, Child>> get() = _childStack

    private fun createChild(
        config: Config,
        componentContext: ComponentContext
    ): Child = when (config) {
        Config.Line -> Child.LineChild(line(componentContext))
        Config.Marker -> Child.MarkerChild(marker(componentContext))
        Config.Label -> Child.LabelChild(label(componentContext))
        Config.LabelLine -> Child.LabelLineChild(labelLine(componentContext))
        Config.LabelMarker -> Child.LabelMarkerChild(labelMarker(componentContext))
    }

    private fun line(componentContext: ComponentContext): LineComponent =
        DefaultLineComponent(
            componentContext = componentContext,
            lineValues = _lineStates
        )

    private fun marker(componentContext: ComponentContext): MarkerComponent =
        DefaultMarkerComponent(
            componentContext = componentContext,
            markerValues = _markerStates
        )

    private fun label(componentContext: ComponentContext): LabelComponent =
        DefaultLabelComponent(
            componentContext = componentContext,
            labelValues = _labelStates
        )

    private fun labelLine(componentContext: ComponentContext): LabelLineComponent =
        DefaultLabelLineComponent(
            componentContext = componentContext,
            labelLineValues = _labelLineStates
        )

    private fun labelMarker(componentContext: ComponentContext): LabelMarkerComponent =
        DefaultLabelMarkerComponent(
            componentContext = componentContext,
            labelMarkerValues = _labelMarkerStates
        )

    override fun onLineTabClicked() { navigation.bringToFront(Config.Line) }

    override fun onMarkerTabClicked() { navigation.bringToFront(Config.Marker) }

    override fun onLabelTabClicked() { navigation.bringToFront(Config.Label) }

    override fun onLabelLineTabClicked() { navigation.bringToFront(Config.LabelLine) }

    override fun onLabelMarkerTabClicked() { navigation.bringToFront(Config.LabelMarker) }

    override fun resetGraph() {
        _lineStates.resetLine()
        _markerStates.resetMarker()
        _dataValues.resetData()
        _labelStates.resetLabels()
        _labelLineStates.resetLabelLine()
        _labelMarkerStates.resetLabelMarkers()
    }

    init {
        stateKeeper.register(KEY_LINE) { _lineStates.lineStates.value }
        stateKeeper.register(KEY_MARKER) { _markerStates.markerStates.value }
        stateKeeper.register(KEY_DATA) { _dataValues.dataValues.value }
        stateKeeper.register(KEY_LABEL) { _labelStates.labelStates.value }
        stateKeeper.register(KEY_LABEL_LINE) { _labelLineStates.labelLineStates.value }
        stateKeeper.register(KEY_LABEL_MARKER) { _labelMarkerStates.labelMarkerStates.value }
    }

    private companion object {
        private const val KEY_LINE = "LINE"
        private const val KEY_MARKER = "MARKER"
        private const val KEY_DATA = "DATA"
        private const val KEY_LABEL = "LABEL"
        private const val KEY_LABEL_LINE = "LABEL_LINE"
        private const val KEY_LABEL_MARKER = "LABEL_MARKER"
    }

    private sealed class Config : Parcelable {
        @Parcelize
        data object Line: Config() {
            private fun readResolve(): Any = Line

        }

        @Parcelize
        data object Marker: Config() {
            private fun readResolve(): Any = Marker
        }

        @Parcelize
        data object Label: Config() {
            private fun readResolve(): Any = Label
        }

        @Parcelize
        data object LabelLine: Config() {
            private fun readResolve(): Any = LabelLine
        }

        @Parcelize
        data object LabelMarker: Config() {
            private fun readResolve(): Any = LabelMarker
        }
    }
}