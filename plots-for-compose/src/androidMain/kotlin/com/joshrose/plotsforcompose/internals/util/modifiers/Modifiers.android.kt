package com.joshrose.plotsforcompose.internals.util.modifiers

import androidx.compose.ui.input.pointer.PointerEvent

internal actual fun onPointerOut(event: PointerEvent, update: () -> Unit) {
    if (!event.changes.first().pressed) update()
}