package com.joshrose.plotsforcompose.exception

class FigureNotFoundException(message: String? = null, cause: Throwable? = null) : Exception(message, cause) {
    constructor(cause: Throwable) : this(null, cause)
}