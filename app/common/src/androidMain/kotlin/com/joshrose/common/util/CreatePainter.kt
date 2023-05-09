package com.joshrose.common.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.joshrose.common.R
import com.joshrose.common.util.ImageResources.*

@Composable
internal actual fun createPainter(file: ImageResources): Painter =
    painterResource(
        when (file) {
            GUIDELINES -> R.drawable.grid_on
            LABELS -> R.drawable.label
            AXIS_LINES -> R.drawable.line_axis
            VISIBILITY -> R.drawable.visibility
            ALIGN_LEFT -> R.drawable.align_horizontal_left
            ALIGN_RIGHT -> R.drawable.align_horizontal_right
            ALIGN_CENTER_HORIZONTAL -> R.drawable.align_horizontal_center
            ALIGN_TOP -> R.drawable.align_vertical_top
            ALIGN_BOTTOM -> R.drawable.align_vertical_bottom
            ALIGN_CENTER_VERTICAL -> R.drawable.align_vertical_center
            ALIGN_AUTO -> R.drawable.auto_awesome
        }
    )