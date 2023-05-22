package com.joshrose.plotsforcompose.internals.standardizing

import java.time.Instant

internal object Standardizing {
    fun standardizeValue(value: Any?): Any? {
        return when (value) {
            null -> value
            is String -> value
            is Number -> toDouble(value)
            is Char -> value.toString()
            is Map<*, *> -> MapStandardizing.standardize(value)
            is Enum<*> -> value.name
            is Instant -> value.epochSecond
            else -> {
                if (SeriesStandardizing.isSeries(value)) {
                    val l = SeriesStandardizing.toList(value)
                    l.map { standardizeValue(it) }
                } else value
            }
        }
    }
}

private fun toDouble(n: Number): Double? {
    return when (n) {
        is Float -> if (n.isFinite()) n.toDouble() else null
        is Double -> if (n.isFinite()) n else null
        else -> n.toDouble()
    }
}