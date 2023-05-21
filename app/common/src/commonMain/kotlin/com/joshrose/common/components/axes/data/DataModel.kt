package com.joshrose.common.components.axes.data

import com.arkivanov.decompose.value.MutableValue
import com.joshrose.common.components.axes.models.DataValueStates
import com.joshrose.plotsforcompose.axis.config.labels.LabelsConfiguration
import com.joshrose.plotsforcompose.axis.config.util.Multiplier
import com.joshrose.plotsforcompose.linegraph.model.NumberData
import kotlinx.coroutines.CoroutineScope

interface DataModel {
    val scope: CoroutineScope
    val dataValueStates: MutableValue<DataValueStates>

    fun updateData(data: List<NumberData>)

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
        xConfig: LabelsConfiguration,
        yConfig: LabelsConfiguration
    )

    fun resetData()
}