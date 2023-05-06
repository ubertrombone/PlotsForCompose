package com.joshrose.common.components.axes.loading

import com.arkivanov.decompose.value.MutableValue
import com.joshrose.common.components.axes.models.LoadingState
import kotlinx.coroutines.CoroutineScope

interface LoadingModel {
    val scope: CoroutineScope
    val loadingState: MutableValue<LoadingState>

    fun updateState(state: LoadingState)
}