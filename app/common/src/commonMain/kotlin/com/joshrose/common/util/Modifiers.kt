package com.joshrose.common.util

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.*

fun Modifier.repeatingClickable(
    interactionSource: InteractionSource,
    enabled: Boolean,
    onClick: () -> Unit
): Modifier = composed {
    val currentClickListener by rememberUpdatedState(onClick)
    val isEnabled by rememberUpdatedState(enabled)
    val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    pointerInput(interactionSource, enabled) {
        awaitEachGesture {
            val down = awaitFirstDown(requireUnconsumed = false)
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

expect fun Modifier.paddingBottomBar(
    paddingValues: PaddingValues,
    top: Dp = 0.dp,
    start: Dp = 0.dp,
    end: Dp = 0.dp,
    bottom: Dp = 0.dp
): Modifier