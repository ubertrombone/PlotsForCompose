package com.joshrose.common.components.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.*
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.joshrose.common.components.axes.AxesComponent
import com.joshrose.common.components.home.DefaultHomeComponent
import com.joshrose.common.components.home.HomeComponent
import com.joshrose.common.components.graph.LineGraphComponent
import com.joshrose.common.components.root.RootComponent.Child.*
import com.joshrose.common.util.ScreenNames.*

class DefaultRootComponent(
    componentContext: ComponentContext
) : RootComponent, ComponentContext by componentContext {

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
            is Config.Axes -> AxesChild(AxesComponent(componentContext))
            is Config.LineGraph -> LineGraphChild(LineGraphComponent(componentContext))
        }

    private fun home(componentContext: ComponentContext): HomeComponent =
        DefaultHomeComponent(
            componentContext = componentContext,
            onChildSelected = {
                when (it) {
                    HOME -> throw IllegalStateException("Home is not a child of Home")
                    AXES -> navigation.push(Config.Axes)
                    LINE_GRAPH -> navigation.push(Config.LineGraph)
                }
            }
        )

    override fun onBackPressed() { navigation.pop() }

    private sealed class Config : Parcelable {
        @Parcelize
        object Home : Config()

        @Parcelize
        object Axes: Config()

        @Parcelize
        object LineGraph : Config()
    }
}