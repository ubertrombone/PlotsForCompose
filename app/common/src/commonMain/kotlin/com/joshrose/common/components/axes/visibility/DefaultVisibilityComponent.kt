package com.joshrose.common.components.axes.visibility

import com.arkivanov.decompose.ComponentContext

class DefaultVisibilityComponent(
    componentContext: ComponentContext
): VisibilityComponent, ComponentContext by componentContext