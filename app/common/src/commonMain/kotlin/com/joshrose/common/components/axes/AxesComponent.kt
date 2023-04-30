package com.joshrose.common.components.axes

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
}