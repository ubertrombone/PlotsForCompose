package com.joshrose.plotsforcompose.internals.util

import android.annotation.SuppressLint
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput

@SuppressLint("ModifierFactoryUnreferencedReceiver")
internal actual fun Modifier.showGraphLabels(
    interactionSource: InteractionSource,
    enabled: Boolean,
    coordinates: List<Pair<Float, Float>>,
    data: List<Pair<Any?, Number>>,
    updatePosition: (position: Offset?, values: Pair<Any?, Any?>?, index: Int?) -> Unit
): Modifier = composed {
    val updateListener by rememberUpdatedState(updatePosition)
    pointerInput(interactionSource, enabled) {
        awaitEachGesture {
            while (true) {
                val event = awaitPointerEvent()
                val position = event.changes.first().position
                // TODO: Android: if event.changes.first().pressed = false -- return null; Desktop: if event.nativeEvent contains "MOUSE_EXITED" -- return null
                val xPointRanges =
                    coordinates.map { it.first }.map {
                        val factor = size.width.div(coordinates.size).div(3f)
                        it.minus(factor) to it.plus(factor)
                    }
                val areaRange = xPointRanges.lastOrNull { it.first < position.x }

                areaRange?.let {
                    if (position.x <= areaRange.second && (0f..size.height.toFloat().minus(.01f)).contains(position.y)) {
                        val pointIndex = xPointRanges.indexOf(areaRange)
                        val pointValue = data.elementAt(pointIndex)
                        updateListener(position, pointValue, pointIndex)
                    } else updateListener(null, null, null)
                }

                if (!event.changes.first().pressed)
                    updateListener(null, null, null)
            }
        }
    }
}