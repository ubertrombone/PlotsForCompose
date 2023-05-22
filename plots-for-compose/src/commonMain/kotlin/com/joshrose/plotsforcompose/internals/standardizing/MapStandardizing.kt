package com.joshrose.plotsforcompose.internals.standardizing

object MapStandardizing {
    fun standardize(map: Map<*, *>): Map<String, Any> {
        return map
            .filter { it.value != null }
            .map { it.key.toString() to Standardizing.standardizeValue(it.value) as Any }.toMap()
    }
}