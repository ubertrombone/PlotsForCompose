package com.joshrose.common.components.axes

import com.arkivanov.decompose.ComponentContext
import com.joshrose.common.components.graph.GraphComponent
import com.joshrose.common.util.ScreenNames
import com.joshrose.common.util.ScreenNames.AXES
import com.joshrose.plotsforcompose.axis.config.labels.ContinuousLabelsConfig
import com.joshrose.plotsforcompose.axis.config.util.Multiplier
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

    fun maxValues(
        yValue: Float,
        yValueAdjustment: Multiplier,
        xValue: Float,
        xValueAdjustment: Multiplier
    ) {
        _maxYValue.update { yValue.plus(yValue.times(yValueAdjustment.factor)) }
        _maxXValue.update { xValue.plus(xValue.times(xValueAdjustment.factor)) }
    }

    fun minValues(
        xValue: Float,
        xValueAdjustment: Multiplier,
        isXBaseZero: Boolean,
        yValue: Float,
        yValueAdjustment: Multiplier,
        isYBaseZero: Boolean
    ) {
        val adjustedMinYValue = if (isYBaseZero) 0f else yValue.minus(abs(yValue.times(yValueAdjustment.factor)))
        val finalYValue = if (yValue < 0) _maxYValue.value?.times(-1) ?: 100f else adjustedMinYValue
        _minYValue.update { finalYValue }

        val adjustedMinXValue = if (isXBaseZero) 0f else xValue.minus(abs(xValue.times(xValueAdjustment.factor)))
        val finalXValue = if (xValue < 0) _maxXValue.value?.times(-1) ?: 100f else adjustedMinXValue
        _minXValue.update { finalXValue }
    }

    fun ranges() {
        _xRange.update { _maxXValue.value?.minus(_minXValue.value ?: 0f) ?: 0f }
        _yRange.update { _maxYValue.value?.minus(_minYValue.value ?: 0f) ?: 0f }
    }
}