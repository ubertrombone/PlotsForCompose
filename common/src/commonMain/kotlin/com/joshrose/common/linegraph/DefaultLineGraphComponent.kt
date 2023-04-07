package com.joshrose.common.linegraph

import com.arkivanov.decompose.ComponentContext

class DefaultLineGraphComponent(
    componentContext: ComponentContext,
    private val onPrev: () -> Unit
): LineGraphComponent, ComponentContext by componentContext {
    override fun onBackClicked() {
        onPrev
    }
}