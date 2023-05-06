package com.joshrose.common.components.axes.data

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import com.joshrose.common.components.axes.models.DataValueStates
import com.joshrose.plotsforcompose.axis.config.labels.ContinuousLabelsConfig
import com.joshrose.plotsforcompose.axis.config.util.Multiplier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

interface DataModel {
    val scope: CoroutineScope
    val dataValueStates: MutableValue<DataValueStates>

    fun updateData(xList: List<Float>, yList: List<Float>) {
        scope.launch {
            dataValueStates.update { it.copy(data = listOf(xList, yList)) }
        }
    }

    fun maxValues(
        yMaxValue: Float,
        yMaxValueAdjustment: Multiplier,
        xMaxValue: Float,
        xMaxValueAdjustment: Multiplier
    )

    fun minValues(
        yMinValue: Float,
        yMinValueAdjustment: Multiplier,
        xMinValue: Float,
        xMinValueAdjustment: Multiplier,
    )

    fun ranges(yRangeAdjustment: Multiplier, xRangeAdjustment: Multiplier)

    fun calculateData(
        xConfig: ContinuousLabelsConfig,
        yConfig: ContinuousLabelsConfig
    )
}