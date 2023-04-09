package com.joshrose.common.components.home

import com.arkivanov.decompose.value.Value
import com.joshrose.common.util.ScreenNames

interface HomeComponent {

    val children: Value<Names>
    val screenProperties: ScreenNames

    fun onChildClicked(name: ScreenNames)

    data class Names(val name: List<ScreenNames>)
}