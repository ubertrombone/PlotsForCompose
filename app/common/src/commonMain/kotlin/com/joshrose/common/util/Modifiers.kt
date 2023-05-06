package com.joshrose.common.util

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.*

fun Modifier.repeatingClickable(
    interactionSource: InteractionSource,
    enabled: Boolean,
    onClick: () -> Unit
): Modifier = composed {
    val currentClickListener by rememberUpdatedState(onClick)
    val isEnabled by rememberUpdatedState(enabled)

    pointerInput(interactionSource, enabled) {
        awaitEachGesture {
            val down = awaitFirstDown(requireUnconsumed = false)
            val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
            val heldButtonJob = scope.launch {
                while (isEnabled && down.pressed) {
                    currentClickListener()
                    delay(100)
                }
            }
            waitForUpOrCancellation()
            heldButtonJob.cancel()
        }
    }
}