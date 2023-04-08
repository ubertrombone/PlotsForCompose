package com.joshrose.common.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.*
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.joshrose.common.home.DefaultHomeComponent
import com.joshrose.common.home.HomeComponent
import com.joshrose.common.linegraph.DefaultLineGraphComponent
import com.joshrose.common.linegraph.LineGraphComponent
import com.joshrose.common.root.RootComponent.Child.HomeChild
import com.joshrose.common.root.RootComponent.Child.LineGraphChild

class DefaultRootComponent(
    componentContext: ComponentContext
): RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    private val _childStack = childStack(
        source = navigation,
        initialConfiguration = Config.Home,
        handleBackButton = true,
        childFactory = ::createChild
    )
    override val childStack: Value<ChildStack<*, RootComponent.Child>> = _childStack

    private fun createChild(config: Config, componentContext: ComponentContext): RootComponent.Child =
        when (config) {
            is Config.Home -> HomeChild(home(componentContext))
            is Config.LineGraph -> LineGraphChild(lineGraph(componentContext))
        }

    private fun home(componentContext: ComponentContext): HomeComponent =
        DefaultHomeComponent(
            componentContext = componentContext,
            onChildSelected = {
                when (it) {
                    "Line Graph" -> navigation.push(Config.LineGraph)
                }
            }
        )

    private fun lineGraph(componentContext: ComponentContext): LineGraphComponent =
        DefaultLineGraphComponent(
            componentContext = componentContext,
            onPrev = { navigation.pop { isSuccess -> println(isSuccess) } }
        )

    override fun onBackPressed() {
        navigation.pop { isSuccess -> println(isSuccess) }
    }

    private sealed class Config: Parcelable {
        @Parcelize
        object Home: Config()

        @Parcelize
        object LineGraph: Config()
    }
}