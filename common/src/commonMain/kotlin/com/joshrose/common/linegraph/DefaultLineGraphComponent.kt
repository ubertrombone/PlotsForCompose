package com.joshrose.common.linegraph

import com.arkivanov.decompose.ComponentContext

class DefaultLineGraphComponent(
    componentContext: ComponentContext,
    private val onPrev: () -> Unit
): LineGraphComponent, ComponentContext by componentContext {

    override val lineGraphName = "Line Graph"
    override val isBackEnabled = true

    override fun onBackClicked() { onPrev() }
}