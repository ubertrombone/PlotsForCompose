package com.joshrose.common.components.linegraph.data

import com.arkivanov.decompose.value.MutableValue
import com.joshrose.common.components.linegraph.models.DataValues
import kotlinx.coroutines.CoroutineScope

interface DataModel {
    val scope: CoroutineScope
    val dataValues: MutableValue<DataValues>

    fun updateData()
    fun resetData()
}