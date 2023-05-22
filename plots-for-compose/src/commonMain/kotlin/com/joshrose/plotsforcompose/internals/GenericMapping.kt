package com.joshrose.plotsforcompose.internals

import com.joshrose.plotsforcompose.internals.layer.WithGroupOption

class GenericMapping(
    var x: Any? = null,
    var y: Any? = null,
    var z: Any? = null,

    var xMin: Any? = null,
    var xMax: Any? = null,
    var yMin: Any? = null,
    var yMax: Any? = null,
    var xStart: Any? = null,
    var yStart: Any? = null,
    var xEnd: Any? = null,
    var yEnd: Any? = null,
    var xRange: Any? = null,
    var yRange: Any? = null,

    override var group: Any? = null
) : ConfigCapsule, WithGroupOption {
    override fun seal() = Configs.of(
        "x" to x,
        "y" to y,
        "z" to z,

        "xMin" to xMin,
        "xMax" to xMax,
        "yMin" to yMin,
        "yMax" to yMax,
        "xStart" to xStart,
        "yStart" to yStart,
        "xEnd" to xEnd,
        "yEnd" to yEnd,
        "xRange" to xRange,
        "yRange" to yRange

    ) + groupOption()
}