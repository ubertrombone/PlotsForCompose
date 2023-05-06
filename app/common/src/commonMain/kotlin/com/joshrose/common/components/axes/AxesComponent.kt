package com.joshrose.common.components.axes

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.arkivanov.essenty.statekeeper.consume
import com.joshrose.common.components.axes.data.DataModelImpl
import com.joshrose.common.components.axes.guidelines.GuidelinesModelImpl
import com.joshrose.common.components.axes.loading.LoadingModelImp
import com.joshrose.common.components.axes.models.AxesShowStates
import com.joshrose.common.components.axes.models.DataValueStates
import com.joshrose.common.components.axes.models.GuidelinesStates
import com.joshrose.common.components.axes.models.LoadingState
import com.joshrose.common.components.axes.models.LoadingState.Loading
import com.joshrose.common.components.axes.showaxes.ShowAxesModelImpl
import com.joshrose.common.components.graph.GraphComponent
import com.joshrose.common.util.ScreenNames
import com.joshrose.common.util.ScreenNames.AXES
import com.joshrose.plotsforcompose.axis.config.labels.ContinuousLabelsConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AxesComponent(
    componentContext: ComponentContext
): GraphComponent, ComponentContext by componentContext {
    override val screenProperties: ScreenNames = AXES

    private val _dataValuesState = instanceKeeper.getOrCreate(KEY_DATA_VALUES) {
        DataModelImpl(
            initialState = stateKeeper.consume(KEY_DATA_VALUES) ?: DataValueStates()
        )
    }
    val dataValueStates: Value<DataValueStates> = _dataValuesState.dataValueStates

    private var _loadingState = instanceKeeper.getOrCreate(KEY_LOADING_STATE) {
        LoadingModelImp(
            initialState = stateKeeper.consume(KEY_LOADING_STATE) ?: Loading
        )
    }
    val loadingState: Value<LoadingState> = _loadingState.loadingState

    fun updateData(xList: List<Float>, yList: List<Float>) = _dataValuesState.updateData(xList, yList)

    fun calculateData(
        xConfig: ContinuousLabelsConfig,
        yConfig: ContinuousLabelsConfig
    ) {
        _dataValuesState.calculateData(xConfig, yConfig)
        _loadingState.updateState(LoadingState.Complete)
    }

    private val _xRotation = MutableStateFlow(0f)
    val xRotation = _xRotation.asStateFlow()
    fun updateXRotation(value: Float) = _xRotation.update { value }

    private val _yRotation = MutableStateFlow(0f)
    val yRotation = _yRotation.asStateFlow()
    fun updateYRotation(value: Float) = _yRotation.update { value }

    private val _xShowAxesState = instanceKeeper.getOrCreate(KEY_X_SHOW_AXES) {
        ShowAxesModelImpl(
            initialState = stateKeeper.consume(KEY_X_SHOW_AXES) ?: AxesShowStates()
        )
    }
    val xShowAxesState: Value<AxesShowStates> = _xShowAxesState.showAxesState

    fun updateShowXAxis() = _xShowAxesState.showAxis()
    fun updateShowXAxisLine() = _xShowAxesState.showAxisLine()
    fun updateShowXGuidelines() = _xShowAxesState.showGuidelines()
    fun updateShowXLabels() = _xShowAxesState.showLabels()

    private val _yShowAxesState = instanceKeeper.getOrCreate(KEY_Y_SHOW_AXES) {
        ShowAxesModelImpl(
            initialState = stateKeeper.consume(KEY_Y_SHOW_AXES) ?: AxesShowStates()
        )
    }
    val yShowAxesState: Value<AxesShowStates> = _yShowAxesState.showAxesState

    fun updateShowYAxis() = _yShowAxesState.showAxis()
    fun updateShowYAxisLine() = _yShowAxesState.showAxisLine()
    fun updateShowYGuidelines() = _yShowAxesState.showGuidelines()
    fun updateShowYLabels() = _yShowAxesState.showLabels()

    private val _xGuidelinesState = instanceKeeper.getOrCreate(KEY_X_GUIDELINES) {
        GuidelinesModelImpl(
            initialState = stateKeeper.consume(KEY_X_GUIDELINES) ?: GuidelinesStates()
        )
    }
    val xGuidelinesState: Value<GuidelinesStates> = _xGuidelinesState.guidelinesState

    fun incGuidelinesStrokeWidthX() = _xGuidelinesState.incStrokeWidth()
    fun decGuidelinesStrokeWidthX() = _xGuidelinesState.decStrokeWidth()
    fun incGuidelinesAlphaX() = _xGuidelinesState.incAlpha()
    fun decGuidelinesAlphaX() = _xGuidelinesState.decAlpha()
    fun incGuidelinesPaddingX() = _xGuidelinesState.incPadding()
    fun decGuidelinesPaddingX() = _xGuidelinesState.decPadding()

    private val _yGuidelinesState = instanceKeeper.getOrCreate(KEY_Y_GUIDELINES) {
        GuidelinesModelImpl(
            initialState = stateKeeper.consume(KEY_Y_GUIDELINES) ?: GuidelinesStates()
        )
    }
    val yGuidelinesState: Value<GuidelinesStates> = _yGuidelinesState.guidelinesState

    fun incGuidelinesStrokeWidthY() = _yGuidelinesState.incStrokeWidth()
    fun decGuidelinesStrokeWidthY() = _yGuidelinesState.decStrokeWidth()
    fun incGuidelinesAlphaY() = _yGuidelinesState.incAlpha()
    fun decGuidelinesAlphaY() = _yGuidelinesState.decAlpha()
    fun incGuidelinesPaddingY() = _yGuidelinesState.incPadding()
    fun decGuidelinesPaddingY() = _yGuidelinesState.decPadding()

    init {
        stateKeeper.register(KEY_DATA_VALUES) { _dataValuesState.dataValueStates.value }
        stateKeeper.register(KEY_LOADING_STATE) { _loadingState.loadingState.value }
        stateKeeper.register(KEY_X_SHOW_AXES) { _xShowAxesState.showAxesState.value }
        stateKeeper.register(KEY_Y_SHOW_AXES) { _yShowAxesState.showAxesState.value }
        stateKeeper.register(KEY_X_GUIDELINES) { _xGuidelinesState.guidelinesState.value }
        stateKeeper.register(KEY_Y_GUIDELINES) { _yGuidelinesState.guidelinesState.value }
    }

    private companion object {
        private const val KEY_DATA_VALUES = "DATA_VALUES"
        private const val KEY_LOADING_STATE = "LOADING_STATE"
        private const val KEY_X_SHOW_AXES = "X_SHOW_AXES"
        private const val KEY_Y_SHOW_AXES = "Y_SHOW_AXES"
        private const val KEY_X_GUIDELINES = "X_GUIDELINES"
        private const val KEY_Y_GUIDELINES = "Y_GUIDELINES"
    }
}