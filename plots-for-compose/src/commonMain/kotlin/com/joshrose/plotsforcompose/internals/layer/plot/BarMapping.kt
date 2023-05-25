package com.joshrose.plotsforcompose.internals.layer.plot

import com.joshrose.plotsforcompose.internals.layer.WithGroupOption
import com.joshrose.plotsforcompose.internals.layer.stat.CountStatAesthetics

class BarMapping(
    override var x: Any? = null,
    override var y: Any? = null,
    override var z: Any? = null,
    override var alpha: Any? = null,
    override var color: Any? = null,
    override var width: Any? = null,
    override var group: Any? = null,
    override var weight: Any? = null
) : BarAesthetics, CountStatAesthetics, WithGroupOption {
    override fun seal() = super<BarAesthetics>.seal() +
            super<CountStatAesthetics>.seal() +
            groupOption()
}