package com.joshrose.plotsforcompose.internals.util

data class Range<out T>(val min: T, val max: T) {
    companion object {
        fun <T: Number> Range<T>.mapToFloat() = Range(min = min.toFloat(), max = max.toFloat())
    }
}
