package com.joshrose.plotsforcompose.internals.exception

internal class MissingAestheticException(message: String? = null, cause: Throwable? = null) : Exception(message, cause) {
    constructor(cause: Throwable) : this(null, cause)
}