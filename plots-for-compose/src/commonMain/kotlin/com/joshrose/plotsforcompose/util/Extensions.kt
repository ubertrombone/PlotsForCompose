package com.joshrose.plotsforcompose.util

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.abs

fun Float.toRadians() = this * (PI / 180f).toFloat()

fun Dp.abs() = abs(this.value).dp