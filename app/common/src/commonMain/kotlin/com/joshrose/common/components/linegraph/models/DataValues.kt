package com.joshrose.common.components.linegraph.models

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

@Parcelize
data class DataValues(
    val data: Map<String, List<Int>> = mapOf(
        "Independent" to List(20) { (-5..5).random() },
        "Dependent" to List(20) { (-5..5).random() }
    )
): Parcelable
