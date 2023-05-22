package com.joshrose.plotsforcompose.internals

import com.joshrose.plotsforcompose.internals.standardizing.MapStandardizing

class Configs(map: Map<String, Any?>) {
    companion object {
        fun of(pair: Pair<String, Any?>) = Configs(mapOf(pair))
        fun of(vararg pairs: Pair<String, Any?>) = Configs(mapOf(*pairs))
        fun empty() = Configs(emptyMap())
    }

    val map: Map<String, Any> = MapStandardizing.standardize(map)

    operator fun plus(other: Configs) = Configs(this.map + other.map)

    fun has(key: String): Boolean = map.containsKey(key)
    fun get(key: String): Any = map[key] ?: throw IllegalArgumentException("No such option: '$key'")
    fun isEmpty() = map.isEmpty()
}