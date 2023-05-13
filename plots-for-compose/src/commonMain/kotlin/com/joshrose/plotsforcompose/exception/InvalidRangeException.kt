package com.joshrose.plotsforcompose.exception

class InvalidRangeException(message: String? = null, cause: Throwable? = null) : Exception(message, cause) {
    constructor(cause: Throwable) : this(null, cause)
}