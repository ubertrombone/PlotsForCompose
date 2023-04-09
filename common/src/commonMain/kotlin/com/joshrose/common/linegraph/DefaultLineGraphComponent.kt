package com.joshrose.common.linegraph

import com.arkivanov.decompose.ComponentContext
import com.joshrose.common.util.ScreenNames.LINE_GRAPH

class DefaultLineGraphComponent(
    componentContext: ComponentContext,
    private val onPrev: () -> Unit
) : LineGraphComponent, ComponentContext by componentContext {

    override val screenProperties = LINE_GRAPH

    override fun onBackClicked() {
        onPrev()
    }
}