package com.joshrose.common.linegraph

interface LineGraphComponent {
    val lineGraphName: String
    val isBackEnabled: Boolean
    fun onBackClicked()
}