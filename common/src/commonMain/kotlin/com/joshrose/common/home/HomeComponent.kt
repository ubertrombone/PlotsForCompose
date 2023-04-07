package com.joshrose.common.home

import com.arkivanov.decompose.value.Value

interface HomeComponent {

    val children: Value<Children>

    fun onChildClicked(id: Int)

    data class Children(
        val children: List<Child>,
        val selectedChildId: Int?
    )

    data class Child(
        val id: Int,
        val name: String
    )
}