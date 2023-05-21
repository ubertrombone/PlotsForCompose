package com.joshrose.common.components.axes

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
import com.joshrose.common.components.axes.AxesComponent.Child
import com.joshrose.common.components.axes.axisline.AxisLineComponent
import com.joshrose.common.components.axes.axisline.DefaultAxisLineComponent
import com.joshrose.common.components.axes.axisline.XAxisLineModelImpl
import com.joshrose.common.components.axes.axisline.YAxisLineModelImpl
import com.joshrose.common.components.axes.data.DataModelImpl
import com.joshrose.common.components.axes.guidelines.DefaultGuidelinesComponent
import com.joshrose.common.components.axes.guidelines.GuidelinesComponent
import com.joshrose.common.components.axes.guidelines.GuidelinesModelImpl
import com.joshrose.common.components.axes.labels.DefaultLabelsComponent
import com.joshrose.common.components.axes.labels.LabelsComponent
import com.joshrose.common.components.axes.labels.LabelsModelImpl
import com.joshrose.common.components.axes.loading.LoadingModelImp
import com.joshrose.common.components.axes.models.*
import com.joshrose.common.components.axes.models.LoadingState.Loading
import com.joshrose.common.components.axes.visibility.DefaultVisibilityComponent
import com.joshrose.common.components.axes.visibility.VisibilityComponent
import com.joshrose.common.components.axes.visibility.VisibilityModelImpl
import com.joshrose.common.util.ScreenNames
import com.joshrose.common.util.ScreenNames.AXES
import com.joshrose.plotsforcompose.axis.config.labels.LabelsConfiguration
import com.joshrose.plotsforcompose.linegraph.model.NumberData

class DefaultAxesComponent(
    componentContext: ComponentContext
): AxesComponent, ComponentContext by componentContext {
    override val screenProperties: ScreenNames = AXES

    private val navigation = StackNavigation<Config>()

    private val _dataValuesState = instanceKeeper.getOrCreate(KEY_DATA_VALUES) {
        DataModelImpl(
            initialState = stateKeeper.consume(KEY_DATA_VALUES) ?: DataValueStates()
        )
    }
    override val dataValueStates: Value<DataValueStates> = _dataValuesState.dataValueStates

    private val _loadingState = instanceKeeper.getOrCreate(KEY_LOADING_STATE) {
        LoadingModelImp(
            initialState = stateKeeper.consume(KEY_LOADING_STATE) ?: Loading
        )
    }
    override val loadingState: Value<LoadingState> = _loadingState.loadingState

    override fun updateData(data: List<NumberData>) = _dataValuesState.updateData(data)

    override fun calculateData(xConfig: LabelsConfiguration, yConfig: LabelsConfiguration) {
        _dataValuesState.calculateData(xConfig, yConfig)
        _loadingState.updateState(LoadingState.Complete)
    }

    private val _xVisibilityState = instanceKeeper.getOrCreate(KEY_X_VISIBILITY) {
        VisibilityModelImpl(
            initialState = stateKeeper.consume(KEY_X_VISIBILITY) ?: VisibilityStates()
        )
    }
    override val xVisibilityState: Value<VisibilityStates> = _xVisibilityState.visibilityState

    private val _yVisibilityState = instanceKeeper.getOrCreate(KEY_Y_VISIBILITY) {
        VisibilityModelImpl(
            initialState = stateKeeper.consume(KEY_Y_VISIBILITY) ?: VisibilityStates()
        )
    }
    override val yVisibilityState: Value<VisibilityStates> = _yVisibilityState.visibilityState

    private val _xGuidelinesState = instanceKeeper.getOrCreate(KEY_X_GUIDELINES) {
        GuidelinesModelImpl(
            initialState = stateKeeper.consume(KEY_X_GUIDELINES) ?: GuidelinesStates()
        )
    }
    override val xGuidelinesState: Value<GuidelinesStates> = _xGuidelinesState.guidelinesState

    private val _yGuidelinesState = instanceKeeper.getOrCreate(KEY_Y_GUIDELINES) {
        GuidelinesModelImpl(
            initialState = stateKeeper.consume(KEY_Y_GUIDELINES) ?: GuidelinesStates()
        )
    }
    override val yGuidelinesState: Value<GuidelinesStates> = _yGuidelinesState.guidelinesState

    private val _xAxisLineState = instanceKeeper.getOrCreate(KEY_X_AXIS_LINE) {
        XAxisLineModelImpl(
            initialState = stateKeeper.consume(KEY_X_AXIS_LINE) ?: AxisLineStates.XState()
        )
    }
    override val xAxisLineState: Value<AxisLineStates.XState> = _xAxisLineState.axisLineState

    private val _yAxisLineState = instanceKeeper.getOrCreate(KEY_Y_AXIS_LINE) {
        YAxisLineModelImpl(
            initialState = stateKeeper.consume(KEY_Y_AXIS_LINE) ?: AxisLineStates.YState()
        )
    }
    override val yAxisLineState: Value<AxisLineStates.YState> = _yAxisLineState.axisLineState

    private val _xLabelsState = instanceKeeper.getOrCreate(KEY_X_LABELS) {
        LabelsModelImpl(
            initialState = stateKeeper.consume(KEY_X_LABELS) ?: LabelsStates()
        )
    }

    override val xLabelsState: Value<LabelsStates> = _xLabelsState.labelsState

    private val _yLabelsState = instanceKeeper.getOrCreate(KEY_Y_LABELS) {
        LabelsModelImpl(
            initialState = stateKeeper.consume(KEY_Y_LABELS) ?: LabelsStates()
        )
    }

    override val yLabelsState: Value<LabelsStates> = _yLabelsState.labelsState

    private val _childStack = childStack(
        source = navigation,
        initialConfiguration = Config.Visibility,
        handleBackButton = false,
        childFactory = ::createChild
    )

    override val childStack: Value<ChildStack<*, Child>> get() = _childStack

    private fun createChild(
        config: Config,
        componentContext: ComponentContext
    ): Child =
        when (config) {
            is Config.Visibility -> Child.VisibilityChild(visibility(componentContext))
            is Config.Guidelines -> Child.GuidelinesChild(guidelines(componentContext))
            is Config.AxisLine -> Child.AxisLinesChild(axisLine(componentContext))
            is Config.Labels -> Child.LabelsChild(labels(componentContext))
        }

    private fun visibility(componentContext: ComponentContext): VisibilityComponent =
        DefaultVisibilityComponent(
            componentContext = componentContext,
            xVisibility = _xVisibilityState,
            yVisibility = _yVisibilityState
        )

    private fun guidelines(componentContext: ComponentContext): GuidelinesComponent =
        DefaultGuidelinesComponent(
            componentContext = componentContext,
            xGuidelinesValues = _xGuidelinesState,
            yGuidelinesValues = _yGuidelinesState
        )

    private fun axisLine(componentContext: ComponentContext): AxisLineComponent =
        DefaultAxisLineComponent(
            componentContext = componentContext,
            xAxisLineValues = _xAxisLineState,
            yAxisLineValues = _yAxisLineState
        )

    private fun labels(componentContext: ComponentContext): LabelsComponent =
        DefaultLabelsComponent(
            componentContext = componentContext,
            xLabelsValues = _xLabelsState,
            yLabelsValues = _yLabelsState
        )

    override fun onVisibilityTabClicked() { navigation.bringToFront(Config.Visibility) }
    override fun onGuidelinesTabClicked() { navigation.bringToFront(Config.Guidelines) }
    override fun onAxisLinesTabClicked() { navigation.bringToFront(Config.AxisLine) }
    override fun onLabelsTabClicked() { navigation.bringToFront(Config.Labels) }
    override fun resetAxis() {
        _dataValuesState.resetData()
        _xVisibilityState.resetVisibility()
        _yVisibilityState.resetVisibility()
        _xGuidelinesState.resetGuidelines()
        _yGuidelinesState.resetGuidelines()
        _xAxisLineState.resetAxisLine()
        _yAxisLineState.resetAxisLine()
        _xLabelsState.resetLabels()
        _yLabelsState.resetLabels()
    }

    init {
        stateKeeper.register(KEY_DATA_VALUES) { _dataValuesState.dataValueStates.value }
        stateKeeper.register(KEY_LOADING_STATE) { _loadingState.loadingState.value }
        stateKeeper.register(KEY_X_VISIBILITY) { _xVisibilityState.visibilityState.value }
        stateKeeper.register(KEY_Y_VISIBILITY) { _yVisibilityState.visibilityState.value }
        stateKeeper.register(KEY_X_GUIDELINES) { _xGuidelinesState.guidelinesState.value }
        stateKeeper.register(KEY_Y_GUIDELINES) { _yGuidelinesState.guidelinesState.value }
        stateKeeper.register(KEY_X_AXIS_LINE) { _xAxisLineState.axisLineState.value }
        stateKeeper.register(KEY_Y_AXIS_LINE) { _yAxisLineState.axisLineState.value }
        stateKeeper.register(KEY_X_LABELS) { _xLabelsState.labelsState.value }
        stateKeeper.register(KEY_Y_LABELS) { _yLabelsState.labelsState.value }
    }

    private companion object {
        private const val KEY_DATA_VALUES = "DATA_VALUES"
        private const val KEY_LOADING_STATE = "LOADING_STATE"
        private const val KEY_X_VISIBILITY = "X_VISIBILITY"
        private const val KEY_Y_VISIBILITY = "Y_VISIBILITY"
        private const val KEY_X_GUIDELINES = "X_GUIDELINES"
        private const val KEY_Y_GUIDELINES = "Y_GUIDELINES"
        private const val KEY_X_AXIS_LINE = "X_AXIS_LINE"
        private const val KEY_Y_AXIS_LINE = "Y_AXIS_LINE"
        private const val KEY_X_LABELS = "X_LABELS"
        private const val KEY_Y_LABELS = "Y_LABELS"
    }

    private sealed class Config : Parcelable {
        @Parcelize
        object Visibility: Config()

        @Parcelize
        object Guidelines: Config()

        @Parcelize
        object AxisLine: Config()

        @Parcelize
        object Labels: Config()
    }
}