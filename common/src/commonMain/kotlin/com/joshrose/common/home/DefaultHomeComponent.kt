package com.joshrose.common.home

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.joshrose.common.home.HomeComponent.Names
import com.joshrose.common.util.ScreenNames
import com.joshrose.common.util.ScreenNames.*

class DefaultHomeComponent(
    componentContext: ComponentContext,
    private val onChildSelected: (name: ScreenNames) -> Unit
) : HomeComponent, ComponentContext by componentContext {

    private val _children = MutableValue(
        Names(
            name = listOf(
                LINE_GRAPH
            )
        )
    )

    override val children: Value<Names> = _children
    override val screenProperties = HOME

    override fun onChildClicked(name: ScreenNames) {
        onChildSelected(name)
    }
}