package com.joshrose.plotsforcompose.axis.util

import java.text.DecimalFormat

fun Float.formatToString(pattern: String): String = DecimalFormat(pattern).format(this).toString()