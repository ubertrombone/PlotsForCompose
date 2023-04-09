package com.joshrose.common.linegraph

import com.joshrose.common.util.ScreenNames

interface LineGraphComponent {
    val screenProperties: ScreenNames
    fun onBackClicked()
}