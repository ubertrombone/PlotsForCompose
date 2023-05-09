package com.joshrose.common.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.joshrose.common.R

@Composable
internal actual fun createPainter(file: ImageResources): Painter =
    painterResource(
        when (file) {
            ImageResources.GUIDELINES -> R.drawable.grid_on
            ImageResources.LABELS -> R.drawable.label
            ImageResources.AXIS_LINES -> R.drawable.line_axis
            ImageResources.VISIBILITY -> R.drawable.visibility
        }
    )