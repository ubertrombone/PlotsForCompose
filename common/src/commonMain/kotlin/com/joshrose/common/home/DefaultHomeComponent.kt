package com.joshrose.common.home

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.joshrose.common.home.HomeComponent.Child
import com.joshrose.common.home.HomeComponent.Children
import kotlinx.coroutines.flow.MutableStateFlow

class DefaultHomeComponent(
    componentContext: ComponentContext,
    selectedChildId: MutableStateFlow<Int?>,
    private val onChildSelected: (id: Int) -> Unit
): HomeComponent, ComponentContext by componentContext {

    private val _children = MutableValue(
        Children(
            children = listOf(
                Child(id = 0, name = "LineGraphChild")
            ),
            selectedChildId = null
        )
    )
    override val children: Value<Children> = _children

    init {
        _children.update {
            it.copy(selectedChildId = selectedChildId.value)
        }
    }

    override fun onChildClicked(id: Int) { onChildSelected(id) }
}