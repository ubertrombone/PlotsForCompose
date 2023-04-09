package com.joshrose.common.components.graph

import com.arkivanov.decompose.ComponentContext
import com.joshrose.common.util.ScreenNames.LINE_GRAPH

class LineGraphComponent(
    componentContext: ComponentContext
) : GraphComponent, ComponentContext by componentContext {
    override val screenProperties = LINE_GRAPH
}