package com.joshrose.common.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter

@Composable
internal expect fun createPainter(file: ImageResources): Painter