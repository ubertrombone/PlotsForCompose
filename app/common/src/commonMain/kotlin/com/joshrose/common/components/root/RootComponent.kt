package com.joshrose.common.components.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.joshrose.common.components.axes.DefaultAxesComponent
import com.joshrose.common.components.linegraph.DefaultLineGraphComponent
import com.joshrose.common.components.home.HomeComponent

interface RootComponent {
    val childStack: Value<ChildStack<*, Child>>

    fun onBackPressed()

    sealed class Child {
        class HomeChild(val component: HomeComponent) : Child()
        class AxesChild(val component: DefaultAxesComponent) : Child()
        class LineGraphChild(val component: DefaultLineGraphComponent) : Child()
    }
}