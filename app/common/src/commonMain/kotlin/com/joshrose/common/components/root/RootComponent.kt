package com.joshrose.common.components.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.joshrose.common.components.axes.AxesComponent
import com.joshrose.common.components.home.HomeComponent
import com.joshrose.common.components.graph.LineGraphComponent

interface RootComponent {
    val childStack: Value<ChildStack<*, Child>>

    fun onBackPressed()

    sealed class Child {
        class HomeChild(val component: HomeComponent) : Child()
        class AxesChild(val component: AxesComponent) : Child()
        class LineGraphChild(val component: LineGraphComponent) : Child()
    }
}