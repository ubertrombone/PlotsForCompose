package com.joshrose.common.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource

@Composable
internal actual fun createPainter(file: ImageResources): Painter = painterResource(file.path)