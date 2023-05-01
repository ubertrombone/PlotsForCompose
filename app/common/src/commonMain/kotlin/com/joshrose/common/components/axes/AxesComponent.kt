package com.joshrose.common.components.axes

import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.ComponentContext
import com.joshrose.common.components.graph.GraphComponent
import com.joshrose.common.util.ScreenNames
import com.joshrose.common.util.ScreenNames.AXES
import com.joshrose.plotsforcompose.axis.config.labels.ContinuousLabelsConfig
import com.joshrose.plotsforcompose.axis.config.util.Multiplier
import com.joshrose.plotsforcompose.util.*
import com.joshrose.plotsforcompose.util.LoadingState.COMPLETE
import com.joshrose.plotsforcompose.util.LoadingState.LOADING
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.math.abs

class AxesComponent(
    componentContext: ComponentContext
): GraphComponent, ComponentContext by componentContext {
    override val screenProperties: ScreenNames = AXES

    private val _minYValue = MutableStateFlow<Float?>(null)
    val minYValue = _minYValue.asStateFlow()

    private val _maxYValue = MutableStateFlow<Float?>(null)
    val maxYValue = _maxYValue.asStateFlow()

    private val _minXValue = MutableStateFlow<Float?>(null)
    val minXValue = _minXValue.asStateFlow()

    private val _maxXValue = MutableStateFlow<Float?>(null)
    val maxXValue = _maxXValue.asStateFlow()

    private val _xRange = MutableStateFlow<Float?>(null)
    val xRange = _xRange.asStateFlow()

    private val _yRange = MutableStateFlow<Float?>(null)
    val yRange = _yRange.asStateFlow()

    private val _loading = MutableStateFlow(LOADING)
    val loading = _loading.asStateFlow()

    private fun maxValues(
        yMaxValue: Float,
        yMaxValueAdjustment: Multiplier,
        xMaxValue: Float,
        xMaxValueAdjustment: Multiplier
    ) {
        _maxYValue.update { yMaxValue.plus(yMaxValue.times(yMaxValueAdjustment.factor)) }
        _maxXValue.update { xMaxValue.plus(xMaxValue.times(xMaxValueAdjustment.factor)) }
    }

    private fun minValues(
        yMinValue: Float,
        yMinValueAdjustment: Multiplier,
        xMinValue: Float,
        xMinValueAdjustment: Multiplier,
    ) {
        val adjustedMinYValue = yMinValue.minus(abs(yMinValue.times(yMinValueAdjustment.factor)))
        val finalYValue = if (yMinValue < 0 && _maxYValue.value!! > 0) _maxYValue.value?.times(-1) ?: 100f else adjustedMinYValue
        _minYValue.update { finalYValue }

        val adjustedMinXValue = xMinValue.minus(abs(xMinValue.times(xMinValueAdjustment.factor)))
        val finalXValue = if (xMinValue < 0 && _maxXValue.value!! > 0) _maxXValue.value?.times(-1) ?: 100f else adjustedMinXValue
        _minXValue.update { finalXValue }
    }

    private fun ranges(
        yRangeAdjustment: Multiplier,
        xRangeAdjustment: Multiplier
    ) {
        val xRange = _maxXValue.value?.minus(_minXValue.value ?: 0f) ?: 0f
        _xRange.update {
            when {
                _minXValue.value!! <= 0 && _maxXValue.value!! >= 0 -> xRange
                _minXValue.value!! == 0f || _maxXValue.value == 0f -> xRange
                else -> xRange.plus(xRange.times(xRangeAdjustment.factor))
            }
        }

        val yRange = _maxYValue.value?.minus(_minYValue.value ?: 0f) ?: 0f
        _yRange.update {
            when {
                _minYValue.value!! <= 0 && _maxYValue.value!! >= 0 -> yRange
                _minYValue.value!! == 0f || _maxYValue.value!! == 0f -> yRange
                else -> yRange.plus(yRange.times(yRangeAdjustment.factor))
            }
        }
    }

    fun calculateData(
        xConfig: ContinuousLabelsConfig,
        yConfig: ContinuousLabelsConfig,
        data: List<Coordinates>
    ) {
        maxValues(
            yMaxValue = data.maxYValue(),
            yMaxValueAdjustment = yConfig.maxValueAdjustment,
            xMaxValue = data.maxXValue(),
            xMaxValueAdjustment = xConfig.maxValueAdjustment
        )

        minValues(
            yMinValue = data.minYValue(),
            yMinValueAdjustment = yConfig.minValueAdjustment,
            xMinValue = data.minXValue(),
            xMinValueAdjustment = xConfig.minValueAdjustment
        )

        ranges(yRangeAdjustment = yConfig.rangeAdjustment, xRangeAdjustment = xConfig.rangeAdjustment)

        _loading.update { COMPLETE }
    }

    private val _showXAxis = MutableStateFlow(true)
    val showXAxis = _showXAxis.asStateFlow()
    fun updateShowXAxis() = _showXAxis.update { !it }

    private val _showYAxis = MutableStateFlow(true)
    val showYAxis = _showYAxis.asStateFlow()
    fun updateShowYAxis() = _showYAxis.update { !it }

    private val _showXAxisLine = MutableStateFlow(true)
    val showXAxisLine = _showXAxisLine.asStateFlow()
    fun updateShowXAxisLine() = _showXAxisLine.update { !it }

    private val _showYAxisLine = MutableStateFlow(true)
    val showYAxisLine = _showYAxisLine.asStateFlow()
    fun updateShowYAxisLine() = _showYAxisLine.update { !it }

    private val _showXGuidelines = MutableStateFlow(true)
    val showXGuidelines = _showXGuidelines.asStateFlow()
    fun updateShowXGuidelines() = _showXGuidelines.update { !it }

    private val _showYGuidelines = MutableStateFlow(true)
    val showYGuidelines = _showYGuidelines.asStateFlow()
    fun updateShowYGuidelines() = _showYGuidelines.update { !it }

    private val _showXLabels = MutableStateFlow(true)
    val showXLabels = _showXLabels.asStateFlow()
    fun updateShowXLabels() = _showXLabels.update { !it }

    private val _showYLabels = MutableStateFlow(true)
    val showYLabels = _showYLabels.asStateFlow()
    fun updateShowYLabels() = _showYLabels.update { !it }

    private val _guidelinesStrokeWidthX = MutableStateFlow(1.dp)
    val guidelinesStrokeWidthX = _guidelinesStrokeWidthX.asStateFlow()
    fun incGuidelinesStrokeWidthX() = _guidelinesStrokeWidthX.update { it.plus(1.dp) }
    fun decGuidelinesStrokeWidthX() = _guidelinesStrokeWidthX.update { it.minus(1.dp) }

    private val _guidelinesStrokeWidthY = MutableStateFlow(1.dp)
    val guidelinesStrokeWidthY = _guidelinesStrokeWidthY.asStateFlow()
    fun incGuidelinesStrokeWidthY() = _guidelinesStrokeWidthY.update { it.plus(1.dp) }
    fun decGuidelinesStrokeWidthY() = _guidelinesStrokeWidthY.update { it.minus(1.dp) }

    private val _guidelinesAlphaX = MutableStateFlow(Multiplier(0.5f))
    val guidelinesAlphaX = _guidelinesAlphaX.asStateFlow()
    private val _guidelinesAlphaY = MutableStateFlow(Multiplier(0.5f))
    val guidelinesAlphaY = _guidelinesAlphaY.asStateFlow()
    private val _guidelinesPaddingX = MutableStateFlow(0.dp)
    val guidelinesPaddingX = _guidelinesPaddingX.asStateFlow()
    private val _guidelinesPaddingY = MutableStateFlow(0.dp)
    val guidelinesPaddingY = _guidelinesPaddingY.asStateFlow()

    private val _xRotation = MutableStateFlow(0f)
    val xRotation = _xRotation.asStateFlow()
    private val _yRotation = MutableStateFlow(0f)
    val yRotation = _yRotation.asStateFlow()
}