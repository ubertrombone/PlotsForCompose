package com.joshrose.common.components.axes.models

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

sealed interface LoadingState: Parcelable {
    @Parcelize
    object Empty: LoadingState {
        @Suppress("unused")
        private fun readResolve(): Any = Empty
    }

    @Parcelize
    object Loading: LoadingState {
        @Suppress("unused")
        private fun readResolve(): Any = Loading
    }

    @Parcelize
    object Complete: LoadingState {
        @Suppress("unused")
        private fun readResolve(): Any = Complete
    }
}