package com.joshrose.common.components.axes.data

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.joshrose.common.components.axes.models.DataValueStates
import com.joshrose.plotsforcompose.axis.config.labels.LabelsConfiguration
import com.joshrose.plotsforcompose.axis.config.util.Multiplier
import com.joshrose.plotsforcompose.linegraph.model.NumberData
import com.joshrose.plotsforcompose.linegraph.model.NumberData.Companion.maxXValue
import com.joshrose.plotsforcompose.linegraph.model.NumberData.Companion.maxYValue
import com.joshrose.plotsforcompose.linegraph.model.NumberData.Companion.minXValue
import com.joshrose.plotsforcompose.linegraph.model.NumberData.Companion.minYValue
import kotlinx.coroutines.*
import kotlin.math.abs

class DataModelImpl(initialState: DataValueStates): InstanceKeeper.Instance, DataModel {
    override val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    override val dataValueStates: MutableValue<DataValueStates> = MutableValue(initialState)

    override fun updateData(data: List<NumberData>) {
        scope.launch {
            dataValueStates.update {
                DataValueStates(
                    data = data,
                    maxXValue = data.maxXValue(),
                    minXValue = data.minXValue(),
                    maxYValue = data.maxYValue(),
                    minYValue = data.minYValue(),
                    xRange = data.maxXValue().minus(data.minXValue()),
                    yRange = data.maxYValue().minus(data.minYValue())
                )
            }
        }
    }

    override fun maxValues(
        yMaxValue: Float,
        yMaxValueAdjustment: Multiplier,
        xMaxValue: Float,
        xMaxValueAdjustment: Multiplier
    ) {
        dataValueStates.update { it.copy(maxYValue = yMaxValue.plus(yMaxValue.times(yMaxValueAdjustment.factor))) }
        dataValueStates.update { it.copy(maxXValue = xMaxValue.plus(xMaxValue.times(xMaxValueAdjustment.factor))) }
    }

    override fun minValues(
        yMinValue: Float,
        yMinValueAdjustment: Multiplier,
        xMinValue: Float,
        xMinValueAdjustment: Multiplier
    ) {
        val adjustedMinYValue = yMinValue.minus(abs(yMinValue.times(yMinValueAdjustment.factor)))
        val finalYValue =
            if (yMinValue < 0 && dataValueStates.value.maxYValue!! > 0) dataValueStates.value.maxYValue?.times(-1) ?: 100f
            else adjustedMinYValue
        dataValueStates.update { it.copy(minYValue = finalYValue) }

        val adjustedMinXValue = xMinValue.minus(abs(xMinValue.times(xMinValueAdjustment.factor)))
        val finalXValue =
            if (xMinValue < 0 && dataValueStates.value.maxXValue!! > 0) dataValueStates.value.maxXValue?.times(-1) ?: 100f
            else adjustedMinXValue
        dataValueStates.update { it.copy(minXValue = finalXValue) }
    }

    override fun ranges(yRangeAdjustment: Multiplier, xRangeAdjustment: Multiplier) {
        val yRange = dataValueStates.value.maxYValue?.minus(dataValueStates.value.minYValue ?: 0f) ?: 0f
        dataValueStates.update {
            it.copy(
                yRange = when {
                    dataValueStates.value.minYValue!! <= 0f && dataValueStates.value.maxYValue!! >= 0f -> yRange
                    dataValueStates.value.minYValue!! == 0f || dataValueStates.value.maxYValue!! == 0f -> yRange
                    else -> yRange.plus(yRange.times(yRangeAdjustment.factor))
                }
            )
        }

        val xRange = dataValueStates.value.maxXValue?.minus(dataValueStates.value.minXValue ?: 0f) ?: 0f
        dataValueStates.update {
            it.copy(
                xRange = when {
                    dataValueStates.value.minXValue!! <= 0f && dataValueStates.value.maxXValue!! >= 0f -> xRange
                    dataValueStates.value.minXValue!! == 0f || dataValueStates.value.maxXValue == 0f -> xRange
                    else -> xRange.plus(xRange.times(xRangeAdjustment.factor))
                }
            )
        }
    }

    override fun calculateData(
        xConfig: LabelsConfiguration,
        yConfig: LabelsConfiguration
    ) {
        maxValues(
            yMaxValue = dataValueStates.value.data.maxYValue(),
            yMaxValueAdjustment = yConfig.maxValueAdjustment,
            xMaxValue = dataValueStates.value.data.maxXValue(),
            xMaxValueAdjustment = xConfig.maxValueAdjustment
        )

        minValues(
            yMinValue = dataValueStates.value.data.minYValue(),
            yMinValueAdjustment = yConfig.minValueAdjustment,
            xMinValue = dataValueStates.value.data.minXValue(),
            xMinValueAdjustment = xConfig.minValueAdjustment
        )

        ranges(yRangeAdjustment = yConfig.rangeAdjustment, xRangeAdjustment = xConfig.rangeAdjustment)
    }

    override fun resetData() {
        scope.launch {
            dataValueStates.update { DataValueStates() }
        }
    }

    override fun onDestroy() {
        scope.cancel()
    }
}