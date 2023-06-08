package com.joshrose.plotsforcompose.exception

class DataFrameSizeException(message: String? = null, cause: Throwable? = null) : Exception(message, cause) {
    constructor(cause: Throwable) : this(null, cause)
}