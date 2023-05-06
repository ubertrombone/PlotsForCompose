package com.joshrose.common.components.axes.loading

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.joshrose.common.components.axes.models.LoadingState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

class LoadingModelImp(initialState: LoadingState): InstanceKeeper.Instance, LoadingModel {
    override val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    override val loadingState: MutableValue<LoadingState> = MutableValue(initialState)

    override fun updateState(state: LoadingState) {
        loadingState.update { state }
    }

    override fun onDestroy() {
        scope.cancel()
    }
}