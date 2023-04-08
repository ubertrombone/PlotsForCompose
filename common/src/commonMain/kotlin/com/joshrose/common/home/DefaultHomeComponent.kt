package com.joshrose.common.home

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.joshrose.common.home.HomeComponent.Names

class DefaultHomeComponent(
    componentContext: ComponentContext,
    private val onChildSelected: (name: String) -> Unit
): HomeComponent, ComponentContext by componentContext {

    private val _children = MutableValue(
        Names(
            name = listOf(
                "Line Graph"
            )
        )
    )

    override val children: Value<Names> = _children
    override val homeName = "PlotsForCompose"
    override val isBackEnabled = false

    override fun onChildClicked(name: String) { onChildSelected(name) }
}