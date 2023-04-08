package com.joshrose.common.home

import com.arkivanov.decompose.value.Value

interface HomeComponent {

    val children: Value<Names>
    val homeName: String
    val isBackEnabled: Boolean

    fun onChildClicked(name: String)

    data class Names(val name: List<String>)
}