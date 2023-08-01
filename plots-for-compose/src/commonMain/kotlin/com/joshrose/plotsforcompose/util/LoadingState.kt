package com.joshrose.plotsforcompose.util

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

sealed interface LoadingState: Parcelable {
    @Parcelize
    data object Empty: LoadingState {
        @Suppress("unused")
        private fun readResolve(): Any = Empty
    }

    @Parcelize
    data object Error: LoadingState {
        @Suppress("unused")
        private fun readResolve(): Any = Error
    }

    @Parcelize
    data object Success: LoadingState {
        @Suppress("unused")
        private fun readResolve(): Any = Success
    }
}