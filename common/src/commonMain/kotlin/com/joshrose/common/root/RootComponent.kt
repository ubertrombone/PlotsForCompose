package com.joshrose.common.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.joshrose.common.home.HomeComponent
import com.joshrose.common.linegraph.LineGraphComponent

interface RootComponent {
    val childStack: Value<ChildStack<*, Child>>

    fun onBackPressed()

    sealed class Child {
        class HomeChild(val component: HomeComponent) : Child()
        class LineGraphChild(val component: LineGraphComponent) : Child()
    }
}