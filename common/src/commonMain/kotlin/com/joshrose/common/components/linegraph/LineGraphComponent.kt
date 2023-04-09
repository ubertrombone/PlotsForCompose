package com.joshrose.common.components.linegraph

import com.joshrose.common.util.ScreenNames

interface LineGraphComponent {
    val screenProperties: ScreenNames
    fun onBackClicked()
}