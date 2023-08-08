@file:Suppress("DuplicatedCode")

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
            ALIGN_BOTH_HORIZONTAL -> R.drawable.align_horizontal_both
            ALIGN_TOP -> R.drawable.align_vertical_top
            ALIGN_BOTTOM -> R.drawable.align_vertical_bottom
            ALIGN_CENTER_VERTICAL -> R.drawable.align_vertical_center
            ALIGN_BOTH_VERTICAL -> R.drawable.align_vertical_both
            ALIGN_AUTO -> R.drawable.auto_awesome
            DOUBLE_ARROW_UP -> R.drawable.keyboard_double_arrow_up
            DOUBLE_ARROW_DOWN -> R.drawable.keyboard_double_arrow_down
            LINE_CURVE -> R.drawable.line_curve
            LINE_STRAIGHT -> R.drawable.line_straight
            PUSH_PIN -> R.drawable.push_pin
            SMALL_PUSH_PIN -> R.drawable.small_push_pin
            SHOW_CHART -> R.drawable.show_chart
            LINE -> R.drawable.maximize
            LABEL_LINE -> R.drawable.height_line
            LABEL_MARKER -> R.drawable.label_marker
            FILL -> R.drawable.stroke_full
            STROKE -> R.drawable.circle
        }
    )